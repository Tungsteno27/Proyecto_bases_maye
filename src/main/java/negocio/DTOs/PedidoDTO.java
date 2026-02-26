/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.DTOs;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author julian izaguirre
 */
public class PedidoDTO {

    private int idPedido;
    private LocalDateTime fechaCreacion;
    private String estado;
    private Integer idCliente;
    private double totalPagar;
    private String tipo;
    private List<ProductoCarritoDTO> productos;
    private UsuarioDTO usuario;
    private String codigoCupon;
    // folio del pedido express, null si es programado
    private Integer folio;

    public PedidoDTO() {
    }

    public String getCodigoCupon() {
        return codigoCupon;
    }

    public void setCodigoCupon(String codigoCupon) {
        this.codigoCupon = codigoCupon;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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

    public List<ProductoCarritoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoCarritoDTO> productos) {
        this.productos = productos;
    }

    public Integer getFolio() {
        return folio;
    }

    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" + "idPedido=" + idPedido + ", fechaCreacion=" + fechaCreacion
                + ", estado=" + estado + ", idCliente=" + idCliente
                + ", totalPagar=" + totalPagar + ", tipo=" + tipo
                + ", folio=" + folio + ", productos=" + productos + '}';
    }
}
