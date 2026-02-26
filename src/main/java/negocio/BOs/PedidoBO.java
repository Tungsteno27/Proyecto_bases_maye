package negocio.BOs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import negocio.DTOs.PedidoDTO;
import negocio.Exception.NegocioException;
import persistencia.DAOs.IPedidoDAO;
import persistencia.DAOs.IPedidoExpressDAO;
import persistencia.Dominio.EstadoPedido;
import persistencia.Dominio.Pedido;
import persistencia.Dominio.PedidoExpress;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author julian izaguirre
 */
public class PedidoBO implements IPedidoBO {

    private static final int LIMITE_MINUTOS_EXPRESS = 20;

    private final IPedidoDAO pedidoDAO;
    private final IPedidoExpressDAO pedidoExpressDAO;

    public PedidoBO(IPedidoDAO pedidoDAO, IPedidoExpressDAO pedidoExpressDAO) {
        this.pedidoDAO = pedidoDAO;
        this.pedidoExpressDAO = pedidoExpressDAO;
    }

    // =========================================================================
    // MÉTODOS EXISTENTES - NO MODIFICAR
    // =========================================================================
    @Override
    public PedidoDTO registrarPedido(PedidoDTO pedidoDTO) throws NegocioException {
        try {
            Pedido pedido = new Pedido();
            pedido.setIdCliente(pedidoDTO.getIdCliente());
            pedido.setTotalPagar(pedidoDTO.getTotalPagar());
            if (pedidoDTO.getEstado() != null && !pedidoDTO.getEstado().trim().isEmpty()) {
                try {
                    pedido.setEstado(EstadoPedido.valueOf(pedidoDTO.getEstado().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    pedido.setEstado(EstadoPedido.PENDIENTE);
                }
            } else {
                pedido.setEstado(EstadoPedido.PENDIENTE);
            }
            int idGenerado = pedidoDAO.insertarPedido(pedido);
            pedidoDTO.setIdPedido(idGenerado);
            return pedidoDTO;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error en base de datos al registrar el pedido: " + ex.getMessage());
        }
    }

    // =========================================================================
    // MÉTODO AUXILIAR DE MAPEO
    // =========================================================================
    private PedidoDTO mapearADTO(Pedido p) {
        PedidoDTO dto = new PedidoDTO();
        dto.setIdPedido(p.getIdPedido());
        dto.setIdCliente(p.getIdCliente());
        dto.setTotalPagar(p.getTotalPagar());
        dto.setEstado(p.getEstado().name());
        dto.setFechaCreacion(p.getFechaCreacion());
        return dto;
    }

    // =========================================================================
    // MÉTODOS NUEVOS
    // =========================================================================
    @Override
    public List<PedidoDTO> obtenerPedidosActivos() throws NegocioException {
        try {
            List<Pedido> pedidos = pedidoDAO.obtenerPedidosActivos();
            List<PedidoDTO> resultado = new ArrayList<>();

            for (Pedido p : pedidos) {
                PedidoDTO dto = mapearADTO(p);
                if (p.getIdCliente() == null) {
                    dto.setTipo("Express");
                    try {
                        List<PedidoExpress> expressListos = pedidoExpressDAO.obtenerExpressListos();
                        for (PedidoExpress pe : expressListos) {
                            if (pe.getIdPedidoExpress() == p.getIdPedido()) {
                                dto.setFolio(pe.getFolio());
                                break;
                            }
                        }
                    } catch (PersistenciaException ex) {
                        // Si no podemos obtener el folio, dejamos Express sin folio
                    }
                } else {
                    dto.setTipo("Programado");
                }
                resultado.add(dto);
            }
            return resultado;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al obtener pedidos activos: " + ex.getMessage());
        }
    }

    @Override
    public void marcarComoListo(int idPedido) throws NegocioException {
        try {
            Pedido pedido = pedidoDAO.obtenerPedidoPorId(idPedido);
            if (pedido == null) {
                throw new NegocioException("No se encontró ningún pedido con ID: " + idPedido);
            }
            if (pedido.getEstado() != EstadoPedido.PENDIENTE
                    && pedido.getEstado() != EstadoPedido.EN_PREPARACION) {
                throw new NegocioException("Solo se pueden marcar como Listo los pedidos en estado "
                        + "Pendiente o En Preparación. Estado actual: " + pedido.getEstado().name());
            }
            pedidoDAO.cambiarEstado(idPedido, EstadoPedido.LISTO);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al marcar pedido como Listo: " + ex.getMessage());
        }
    }

    @Override
    public void marcarComoEntregado(int idPedido, Integer folioIngresado, String pinIngresado)
            throws NegocioException {
        try {
            Pedido pedido = pedidoDAO.obtenerPedidoPorId(idPedido);
            if (pedido == null) {
                throw new NegocioException("No se encontró ningún pedido con ID: " + idPedido);
            }
            if (pedido.getEstado() != EstadoPedido.LISTO) {
                throw new NegocioException("Solo se pueden entregar pedidos en estado Listo. "
                        + "Estado actual: " + pedido.getEstado().name());
            }

            if (pedido.getIdCliente() == null) {
                if (folioIngresado == null || pinIngresado == null || pinIngresado.trim().isEmpty()) {
                    throw new NegocioException("Para pedidos express debe proporcionar el folio y el PIN");
                }
                boolean pinValido = pedidoExpressDAO.validarPin(folioIngresado, pinIngresado.trim());
                if (!pinValido) {
                    throw new NegocioException("El folio o PIN de seguridad son incorrectos");
                }
                LocalDateTime fechaHoraListo = pedidoDAO.obtenerFechaHoraListo(idPedido);
                if (fechaHoraListo == null) {
                    throw new NegocioException("No se pudo verificar el tiempo del pedido express");
                }
                long minutos = java.time.Duration.between(fechaHoraListo, LocalDateTime.now()).toMinutes();
                if (minutos > LIMITE_MINUTOS_EXPRESS) {
                    pedidoDAO.cambiarEstado(idPedido, EstadoPedido.NO_RECLAMADO);
                    throw new NegocioException("El tiempo límite de " + LIMITE_MINUTOS_EXPRESS
                            + " minutos ha sido superado. El pedido ha sido marcado como No Reclamado.");
                }
            }

            pedidoDAO.cambiarEstado(idPedido, EstadoPedido.ENTREGADO);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al marcar pedido como Entregado: " + ex.getMessage());
        }
    }

    @Override
    public void cancelarPedido(int idPedido) throws NegocioException {
        try {
            Pedido pedido = pedidoDAO.obtenerPedidoPorId(idPedido);
            if (pedido == null) {
                throw new NegocioException("No se encontró ningún pedido con ID: " + idPedido);
            }
            if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
                throw new NegocioException("Solo se pueden cancelar pedidos en estado Pendiente. "
                        + "Estado actual: " + pedido.getEstado().name());
            }
            pedidoDAO.cambiarEstado(idPedido, EstadoPedido.CANCELADO);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al cancelar el pedido: " + ex.getMessage());
        }
    }

    @Override
    public List<PedidoDTO> buscarPorTelefono(String telefono) throws NegocioException {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new NegocioException("Debe ingresar un número de teléfono para buscar");
        }
        try {
            List<Pedido> pedidos = pedidoDAO.buscarPorTelefono(telefono.trim());
            List<PedidoDTO> resultado = new ArrayList<>();
            for (Pedido p : pedidos) {
                PedidoDTO dto = mapearADTO(p);
                dto.setTipo("Programado");
                resultado.add(dto);
            }
            return resultado;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar por teléfono: " + ex.getMessage());
        }
    }

    @Override
    public List<PedidoDTO> buscarPorRangoDeFechas(LocalDateTime inicio, LocalDateTime fin)
            throws NegocioException {
        if (inicio == null || fin == null) {
            throw new NegocioException("Debe ingresar ambas fechas para buscar por rango");
        }
        if (inicio.isAfter(fin)) {
            throw new NegocioException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        try {
            List<Pedido> pedidos = pedidoDAO.buscarPorRangoDeFechas(inicio, fin);
            List<PedidoDTO> resultado = new ArrayList<>();
            for (Pedido p : pedidos) {
                PedidoDTO dto = mapearADTO(p);
                dto.setTipo(p.getIdCliente() == null ? "Express" : "Programado");
                resultado.add(dto);
            }
            return resultado;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar por rango de fechas: " + ex.getMessage());
        }
    }

    @Override
    public List<PedidoDTO> consultarPedidosProgramadosPorCliente(Integer idCliente)
            throws NegocioException {
        try {
            // Ahora usa el método correcto del DAO que trae el historial completo
            // incluyendo pedidos entregados, cancelados y activos
            List<Pedido> pedidos = pedidoDAO.obtenerPedidosPorCliente(idCliente);
            List<PedidoDTO> resultado = new ArrayList<>();
            for (Pedido p : pedidos) {
                PedidoDTO dto = mapearADTO(p);
                dto.setTipo("Programado");
                resultado.add(dto);
            }
            return resultado;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al consultar pedidos del cliente: " + ex.getMessage());
        }
    }
}
