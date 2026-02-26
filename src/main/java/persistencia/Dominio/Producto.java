/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.Dominio;

import java.util.List;

/**
 *
 * @author Tungs
 */
public class Producto {
    private int idProducto;
    private String nombre;
    private EstadoProducto estado;
    private double precio;
    private String descripcion;
    private TamanioProducto tamanio;


    public Producto(int idProducto, String nombre, EstadoProducto estado, double precio, String descripcion, TamanioProducto tamanio) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.estado = estado;
        this.precio = precio;
        this.descripcion = descripcion;
        this.tamanio = tamanio;
    }

    public Producto() {
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TamanioProducto getTamanio() {
        return tamanio;
    }

    public void setTamanio(TamanioProducto tamanio) {
        this.tamanio = tamanio;
    }



    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", nombre=" + nombre + ", estado=" + estado + ", precio=" + precio + ", descripcion=" + descripcion + ", tamanio=" + tamanio + '}';
    }
    
    
}
