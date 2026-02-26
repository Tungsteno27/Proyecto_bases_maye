/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.BOs;

import java.time.LocalDateTime;
import java.util.List;
import negocio.DTOs.PedidoDTO;
import negocio.Exception.NegocioException;

/**
 * Define las operaciones de negocio relacionadas con pedidos.
 */
public interface IPedidoBO {

    /**
     * Registra un nuevo pedido en la base de datos.
     */
    PedidoDTO registrarPedido(PedidoDTO pedido) throws NegocioException;

    // Metodos nuevos
    /**
     * Obtiene todos los pedidos activos (Pendiente, En Preparación, Listo)
     * ordenados del más antiguo al más reciente. Usado por el panel del
     * empleado.
     */
    List<PedidoDTO> obtenerPedidosActivos() throws NegocioException;

    /**
     * Cambia el estado de un pedido a Listo. Solo se permite si el pedido está
     * en estado Pendiente o En Preparación.
     */
    void marcarComoListo(int idPedido) throws NegocioException;

    /**
     * Marca un pedido como Entregado. Para programados: solo requiere que esté
     * en Listo. Para express: valida folio, PIN y límite de 20 minutos.
     */
    void marcarComoEntregado(int idPedido, Integer folioIngresado, String pinIngresado)
            throws NegocioException;

    /**
     * Cancela un pedido. Solo se permite si está en estado Pendiente.
     */
    void cancelarPedido(int idPedido) throws NegocioException;

    /**
     * Busca pedidos programados por número de teléfono del cliente.
     */
    List<PedidoDTO> buscarPorTelefono(String telefono) throws NegocioException;

    /**
     * Busca pedidos dentro de un rango de fechas.
     */
    List<PedidoDTO> buscarPorRangoDeFechas(LocalDateTime inicio, LocalDateTime fin)
            throws NegocioException;

    /**
     * Consulta el historial de pedidos programados de un cliente. Usado en
     * FrmMisPedidos para mostrar el historial al usuario.
     */
    List<PedidoDTO> consultarPedidosProgramadosPorCliente(Integer idCliente)
            throws NegocioException;
}
