/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs;

import java.time.LocalDateTime;
import java.util.List;
import persistencia.Dominio.EstadoPedido;
import persistencia.Dominio.Pedido;
import persistencia.Exception.PersistenciaException;

/**
 * Define las operaciones de acceso a datos relacionadas con los pedidos.
 */
public interface IPedidoDAO {

    // =========================================================================
    // MÉTODOS EXISTENTES - NO MODIFICAR
    // =========================================================================
    public int insertarPedido(Pedido pedido) throws PersistenciaException;

    public Pedido obtenerPedidoPorId(int idPedido) throws PersistenciaException;

    public void cambiarEstado(int idPedido, EstadoPedido estado) throws PersistenciaException;

    public int contarPedidosActivos(int idCliente) throws PersistenciaException;

    public void actualizarTotalPagar(int idPedido) throws PersistenciaException;

    // =========================================================================
    // MÉTODOS NUEVOS - GESTIÓN DE ENTREGAS
    // =========================================================================
    /**
     * Obtiene todos los pedidos en estado Pendiente, En Preparación o Listo,
     * ordenados del más antiguo al más reciente.
     */
    public List<Pedido> obtenerPedidosActivos() throws PersistenciaException;

    /**
     * Busca pedidos programados por número de teléfono del cliente.
     */
    public List<Pedido> buscarPorTelefono(String telefono) throws PersistenciaException;

    /**
     * Busca pedidos creados dentro de un rango de fechas.
     */
    public List<Pedido> buscarPorRangoDeFechas(LocalDateTime inicio, LocalDateTime fin)
            throws PersistenciaException;

    /**
     * Obtiene la fecha y hora en que un pedido pasó al estado Listo. Se usa
     * para validar el límite de 20 minutos en pedidos express.
     */
    public LocalDateTime obtenerFechaHoraListo(int idPedido) throws PersistenciaException;

    /**
     * Obtiene el historial completo de pedidos programados de un cliente,
     * incluyendo todos los estados (activos, entregados, cancelados). Usado en
     * FrmMisPedidos.
     */
    public List<Pedido> obtenerPedidosPorCliente(int idCliente) throws PersistenciaException;
}
