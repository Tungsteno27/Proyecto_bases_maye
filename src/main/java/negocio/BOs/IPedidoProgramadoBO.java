/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.BOs;

import java.util.List;
import negocio.DTOs.ProductoCarritoDTO;
import negocio.Exception.NegocioException;

/**
 *
 * @author julian izaguirre
 */
public interface IPedidoProgramadoBO {
    /**
     * 
     * @param carrito
     * @param idCliente
     * @param codigoCupon
     * @throws NegocioException 
     */
    public void crearPedidoProgramado(List<ProductoCarritoDTO> carrito, int idCliente, String codigoCupon) throws NegocioException;
    
}
