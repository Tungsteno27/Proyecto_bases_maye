/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.BOs;

import java.util.ArrayList;
import java.util.List;
import negocio.DTOs.ProductoDTO;
import negocio.Exception.NegocioException;
import persistencia.DAOs.IProductoDAO;
import persistencia.Dominio.Producto;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author julian izaguirre
 */
public class ProductoBO implements IProductoBO{
    private final IProductoDAO productoDAO;

    public ProductoBO(IProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    @Override
    public List<ProductoDTO> obtenerProductosDisponibles() throws NegocioException {
        try {
            // le pedimos los productos al DAO
            List<Producto> listaDominio = productoDAO.obtenerProductosDisponibles();
            List<ProductoDTO> listaDTO = new ArrayList<>();
            
            // convertimos cada Producto de la BD a un ProductoDTO para la pantalla
            for (Producto prod : listaDominio) {
                ProductoDTO dto = new ProductoDTO();
                dto.setIdProducto(prod.getIdProducto());
                dto.setNombre(prod.getNombre());
                dto.setPrecio(prod.getPrecio());
                dto.setDescripcion(prod.getDescripcion());
                
                // Convertimos el Enum a String usando .name()
                if (prod.getEstado() != null) {
                    dto.setEstado(prod.getEstado().name());
                }
                if (prod.getTamanio() != null) {
                    dto.setTamanio(prod.getTamanio().name());
                }
                listaDTO.add(dto);
            }
            
            return listaDTO;
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al obtener los productos disponibles: " + ex.getMessage());
        }
    }

    @Override
    public ProductoDTO obtenerProductoPorId(int id) throws NegocioException {
        try {
            // buscamos el producto en la base de datos
            Producto producto = productoDAO.obtenerProductoPorId(id);
            
            if (producto != null) {
                // mapeo de Dominio a DTO
                ProductoDTO dto = new ProductoDTO();
                dto.setIdProducto(producto.getIdProducto());
                dto.setNombre(producto.getNombre());
                dto.setPrecio(producto.getPrecio());
                dto.setDescripcion(producto.getDescripcion());
                
                if (producto.getEstado() != null) {
                    dto.setEstado(producto.getEstado().name()); 
                }
                if (producto.getTamanio() != null) {
                    dto.setTamanio(producto.getTamanio().name());
                }
                return dto;
            } else {
                throw new NegocioException("No se encontro ningun producto con el ID: " + id);
            }
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar el producto: " + ex.getMessage());
        }
    }
    
    @Override
    public ProductoDTO agregarProducto(ProductoDTO productoDTO) throws NegocioException {
        try {
            Producto producto = new Producto();
            producto.setNombre(productoDTO.getNombre());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setDescripcion(productoDTO.getDescripcion());
            
            // Casteo de Enums manejando mayúsculas
            if (productoDTO.getEstado() != null && !productoDTO.getEstado().isEmpty()) {
                producto.setEstado(persistencia.Dominio.EstadoProducto.valueOf(productoDTO.getEstado().toUpperCase()));
            } else {
                producto.setEstado(persistencia.Dominio.EstadoProducto.DISPONIBLE);
            }

            if (productoDTO.getTamanio() != null && !productoDTO.getTamanio().isEmpty()) {
                producto.setTamanio(persistencia.Dominio.TamanioProducto.valueOf(productoDTO.getTamanio().toUpperCase()));
            }

            // Guardamos en BD
            int idGenerado = productoDAO.insertarProducto(producto);
            productoDTO.setIdProducto(idGenerado);
            
            return productoDTO;
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al registrar el producto: " + ex.getMessage());
        }
    }
    
    /**
     * 
     * @param productoDTO
     * @throws NegocioException 
     */
    @Override
    public void actualizarProducto(ProductoDTO productoDTO) throws NegocioException {
        try {
            // Mapeo de DTO a Dominio
            Producto producto = new Producto();
            producto.setIdProducto(productoDTO.getIdProducto());
            producto.setNombre(productoDTO.getNombre());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setDescripcion(productoDTO.getDescripcion());
            
            if (productoDTO.getEstado() != null && !productoDTO.getEstado().isEmpty()) {
                producto.setEstado(persistencia.Dominio.EstadoProducto.valueOf(productoDTO.getEstado().toUpperCase()));
            }

            if (productoDTO.getTamanio() != null && !productoDTO.getTamanio().isEmpty()) {
                producto.setTamanio(persistencia.Dominio.TamanioProducto.valueOf(productoDTO.getTamanio().toUpperCase()));
            }

            // Mandamos a actualizar al DAO
            productoDAO.actualizarProducto(producto);

        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al actualizar el producto: " + ex.getMessage());
        }
    }
    
    /**
     * 
     * @param idProducto
     * @throws NegocioException 
     */
    @Override
    public void eliminarProducto(int idProducto) throws NegocioException {
        try {
            productoDAO.eliminarProducto(idProducto);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al eliminar el producto: " + ex.getMessage());
        }
    }
    
}
