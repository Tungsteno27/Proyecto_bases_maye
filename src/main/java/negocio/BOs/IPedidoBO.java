/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.BOs;

import negocio.DTOs.PedidoDTO;
import negocio.Exception.NegocioException;
import java.util.List;

/**
 * Define las operaciones de negocio relacionadas con pedidos.
 */
public interface IPedidoBO {

    /**
     * Registra un nuevo pedido en la base de datos.
     */
    PedidoDTO registrarPedido(PedidoDTO pedido) throws NegocioException;

    /**
     * Consulta los pedidos programados asociados a un cliente.
     */
    List<PedidoDTO> consultarPedidosProgramadosPorCliente(Integer idCliente)
            throws NegocioException;
}
