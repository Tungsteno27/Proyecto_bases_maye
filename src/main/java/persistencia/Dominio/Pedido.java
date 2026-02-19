/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.Dominio;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class Pedido {
    private int idPedido;
    private LocalDateTime fechaCreacion;
    private EstadoPedido estado;
    private Integer idCliente;               // solo ID, puede ser null en express
    private double totalPagar;
    private List<PedidoProducto> productos;

    public Pedido(int idPedido, LocalDateTime fechaCreacion, EstadoPedido estado, Integer idCliente, double totalPagar, List<PedidoProducto> productos) {
        this.idPedido = idPedido;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.idCliente = idCliente;
        this.totalPagar = totalPagar;
        this.productos = productos;
    }

    public Pedido() {
    }
    
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }

    public List<PedidoProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<PedidoProducto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "Pedido{" + "idPedido=" + idPedido + ", fechaCreacion=" + fechaCreacion + ", estado=" + estado + ", idCliente=" + idCliente + ", totalPagar=" + totalPagar + ", productos=" + productos + '}';
    }
    
    
}
