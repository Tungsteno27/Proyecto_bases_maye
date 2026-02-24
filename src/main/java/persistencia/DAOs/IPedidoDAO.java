/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs;

import java.util.List;
import persistencia.Dominio.Pedido;
import persistencia.Exception.PersistenciaException;

/**
 * Define las operaciones de acceso a datos relacionadas con los pedidos.
 */
public interface IPedidoDAO {

    /**
     * Inserta un nuevo pedido en la base de datos.
     */
    public int insertarPedido(Pedido pedido) throws PersistenciaException;

    /**
     * Consulta los pedidos programados asociados a un cliente.
     */
    public List<Pedido> consultarPedidosProgramadosPorCliente(Integer idCliente)
            throws PersistenciaException;
}
