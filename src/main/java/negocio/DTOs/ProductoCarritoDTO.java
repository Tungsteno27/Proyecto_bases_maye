/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.DTOs;

import persistencia.Dominio.Producto;

/**
 *
 * @author Tungs
 */
public class ProductoCarritoDTO {
    private ProductoDTO producto;    
    private int cantidad;        
    private String notas;

    public ProductoCarritoDTO(ProductoDTO producto, int cantidad, String notas) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.notas = notas;
    }

    public ProductoCarritoDTO() {
    }
    
    
    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
    
    
}
