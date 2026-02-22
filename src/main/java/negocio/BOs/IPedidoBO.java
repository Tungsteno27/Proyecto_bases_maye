/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.BOs;

import negocio.DTOs.PedidoDTO;
import negocio.Exception.NegocioException;

/**
 *
 * @author julian izaguirre
 */
public interface IPedidoBO {
    /**
     * registra un nuevo pedido en la base de datos y retorna el DTO con su ID generado
     */
    PedidoDTO registrarPedido(PedidoDTO pedido) throws NegocioException;
}

