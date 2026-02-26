/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.BOs;

import java.util.List;
import negocio.DTOs.PedidoExpressDTO;
import negocio.DTOs.ProductoCarritoDTO;
import negocio.Exception.NegocioException;

/**
 *
 * @author julian izaguirre
 */
public interface IPedidoExpressBO {
      public PedidoExpressDTO crearPedidoExpress(List<ProductoCarritoDTO> carrito) throws NegocioException;   
}
