/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.BOs;

import java.util.List;
import negocio.DTOs.ProductoDTO;
import negocio.Exception.NegocioException;

/**
 *
 * @author julian izaguirre
 */
public interface IProductoBO {
    /**
     * Obtiene la lista de todos los productos con estado DISPONIBLE
     */
    List<ProductoDTO> obtenerProductosDisponibles() throws NegocioException;
    
    /**
     * Busca un producto especifico por su ID
     */
    ProductoDTO obtenerProductoPorId(int id) throws NegocioException;
    
    /**
     * 
     * @param producto
     * @return
     * @throws NegocioException 
     */
    ProductoDTO agregarProducto(ProductoDTO producto) throws NegocioException;
    
    /**
     * 
     * @param productoDTO
     * @throws NegocioException 
     */
    public void actualizarProducto(ProductoDTO productoDTO) throws NegocioException;
    
    /**
     * 
     * @param idProducto
     * @throws NegocioException 
     */
    public void eliminarProducto(int idProducto) throws NegocioException;
    
}
