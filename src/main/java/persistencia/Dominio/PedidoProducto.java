/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.Dominio;

/**
 *
 * @author Tungs
 */
public class PedidoProducto {
    
    private int idPedidoProducto;
    private int idPedido;         
    private Producto producto;    
    private int cantidad;
    private double precioUnitario;
    private double subTotal;
    private String notasAdicionales; 

    public PedidoProducto(int idPedidoProducto, int idPedido, Producto producto, int cantidad, double precioUnitario, double subTotal, String notasAdicionales) {
        this.idPedidoProducto = idPedidoProducto;
        this.idPedido = idPedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subTotal = subTotal;
        this.notasAdicionales = notasAdicionales;
    }

    public PedidoProducto() {
    }

    
    
    public int getIdPedidoProducto() {
        return idPedidoProducto;
    }

    public void setIdPedidoProducto(int idPedidoProducto) {
        this.idPedidoProducto = idPedidoProducto;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getNotasAdicionales() {
        return notasAdicionales;
    }

    public void setNotasAdicionales(String notasAdicionales) {
        this.notasAdicionales = notasAdicionales;
    }

    @Override
    public String toString() {
        return "PedidoProducto{" + "idPedidoProducto=" + idPedidoProducto + ", idPedido=" + idPedido + ", producto=" + producto + ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario + ", subTotal=" + subTotal + ", notasAdicionales=" + notasAdicionales + '}';
    }
    
    
}
