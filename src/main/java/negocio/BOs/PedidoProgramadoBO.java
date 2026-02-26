/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.BOs;

import java.util.List;
import java.util.logging.Logger;
import negocio.DTOs.ProductoCarritoDTO;
import negocio.Exception.NegocioException;
import persistencia.DAOs.ICuponDAO;
import persistencia.DAOs.IPedidoDAO;
import persistencia.DAOs.IPedidoProductoDAO;
import persistencia.DAOs.IPedidoProgramadoDAO;
import persistencia.Dominio.Cupon;
import persistencia.Dominio.EstadoPedido;
import persistencia.Dominio.Pedido;
import persistencia.Dominio.PedidoProducto;
import persistencia.Dominio.PedidoProgramado;
import persistencia.Dominio.Producto;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author julian izaguirre
 */
public class PedidoProgramadoBO implements IPedidoProgramadoBO {
    
    private static final Logger LOG = Logger.getLogger(PedidoProgramadoBO.class.getName());

    private IPedidoProgramadoDAO pedidoProgramadoDAO;
    private IPedidoDAO pedidoDAO;
    private IPedidoProductoDAO pedidoProductoDAO;
    private ICuponDAO cuponDAO;

    public PedidoProgramadoBO(IPedidoProgramadoDAO pedidoProgramadoDAO,IPedidoDAO pedidoDAO, IPedidoProductoDAO pedidoProductoDAO, ICuponDAO cuponDAO) {
        this.pedidoProgramadoDAO = pedidoProgramadoDAO;
        this.pedidoDAO = pedidoDAO;
        this.pedidoProductoDAO = pedidoProductoDAO;
        this.cuponDAO = cuponDAO;
    }
    /**
     * El método más gran de del proyecto, inserta un PedidoProgramado en la BD y aplica reglas de negocio
     * @param carrito la lista de productos contenidos en el carrito
     * @param idCliente el id del cliente que hace el pedido
     * @param codigoCupon el codigo del cupón a canjear
     * @throws NegocioException si ocurre un error al intentar crear un pedido Programado
     */
    @Override
    public void crearPedidoProgramado(List<ProductoCarritoDTO> carrito,int idCliente, String codigoCupon) throws NegocioException {
        LOG.info("Iniciando creación de pedido programado para cliente: " + idCliente);

        try {
            //Antes de hacer cualquier cosa valida que no esté vacío
            if (carrito == null || carrito.isEmpty()) {
                LOG.warning("El carrito está vacío para cliente: " + idCliente);
                throw new NegocioException("El carrito no puede estar vacío");
            }

            // Esto es lo de los 3 pedidos activos que Noelia manejará
            int pedidosActivos = pedidoDAO.contarPedidosActivos(idCliente);
            LOG.info("Pedidos activos del cliente " + idCliente + ": " + pedidosActivos);

            if (pedidosActivos >= 3) {
                LOG.warning("Cliente " + idCliente + " alcanzó el límite de pedidos activos");
                throw new NegocioException("Ha alcanzado el límite de 3 pedidos activos. "
                        + "Espere a que alguno sea entregado o cancelado.");
            }

            //Se crea un Pedido y se inserta en la BD
            LOG.info("Insertando pedido base en tabla Pedidos");
            Pedido pedido = new Pedido();
            pedido.setEstado(EstadoPedido.PENDIENTE);
            pedido.setIdCliente(idCliente);
            pedido.setTotalPagar(0.0);
            int idPedido = pedidoDAO.insertarPedido(pedido);
            LOG.info("Pedido base creado con ID: " + idPedido);

            //Se crea un pedido programado y se inserta
            LOG.info("Insertando registro en PedidoProgramados");
            PedidoProgramado pp = new PedidoProgramado();
            pp.setIdPedidoProgramado(idPedido);
            pp.setCupon(null);
            pedidoProgramadoDAO.insertarPedidoProgramado(pp);

            //se inserta un pedidoProducto en la BD
            LOG.info("Insertando " + carrito.size() + " productos en PedidoProductos");
            for (ProductoCarritoDTO item : carrito) {
                LOG.info("Insertando producto: " + item.getProducto().getNombre()
                        + " cantidad: " + item.getCantidad());

                Producto producto = new Producto();
                producto.setIdProducto(item.getProducto().getIdProducto());
                producto.setPrecio(item.getProducto().getPrecio());

                PedidoProducto pedidoProducto = new PedidoProducto();
                pedidoProducto.setIdPedido(idPedido);
                pedidoProducto.setProducto(producto);
                pedidoProducto.setCantidad(item.getCantidad());
                pedidoProducto.setPrecioUnitario(item.getProducto().getPrecio());
                pedidoProducto.setSubTotal(item.getProducto().getPrecio() * item.getCantidad());

                if (item.getNotas() == null || item.getNotas().isEmpty()) {
                    pedidoProducto.setNotasAdicionales(null);
                } else {
                    pedidoProducto.setNotasAdicionales(item.getNotas());
                }

                pedidoProductoDAO.insertarPedidoProducto(pedidoProducto);
            }

            // 6. Actualiza el totalPagar sumando los subtotales
            LOG.info("Actualizando totalPagar del pedido: " + idPedido);
            pedidoDAO.actualizarTotalPagar(idPedido);
            LOG.info("total Actualizado");

            // 7. Aplica cupón si el cliente ingresó uno
            if (codigoCupon != null && !codigoCupon.isBlank()) {
                LOG.info("Validando cupón: " + codigoCupon);

                Cupon cupon = cuponDAO.obtenerCuponNombre(codigoCupon);

                if (cupon == null) {
                    LOG.warning("Cupón no encontrado: " + codigoCupon);
                    throw new NegocioException("El cupón no existe");
                }

                if (!cuponDAO.validarCupon(cupon.getIdCupon())) {
                    LOG.warning("Cupón inválido o expirado: " + codigoCupon);
                    throw new NegocioException("El cupón es inválido o está expirado");
                }
                LOG.info("Aplicando cupón: " + codigoCupon + " al pedido: " + idPedido);
                pedidoProgramadoDAO.aplicarCupon(idPedido, cupon.getIdCupon());
                LOG.info("Cupón aplicado correctamente");
            }

            LOG.info("Pedido programado creado exitosamente con ID: " + idPedido);

        } catch (PersistenciaException e) {
            LOG.severe("Error de persistencia al crear pedido programado: " + e.getMessage());
            throw new NegocioException("Error al crear el pedido: " + e.getMessage());
        }
    }
}
