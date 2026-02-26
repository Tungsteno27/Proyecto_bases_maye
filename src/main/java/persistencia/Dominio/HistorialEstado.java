/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.Dominio;

import java.time.LocalDateTime;

/**
 *
 * @author Tungs
 */
public class HistorialEstado {
    private int idHistorial;
    private int idPedido;
    private EstadoPedido estadoAnterior;
    private EstadoPedido estadoNuevo;
    private LocalDateTime fechaHoraCambio;

    public HistorialEstado(int idHistorial, int idPedido, EstadoPedido estadoAnterior, EstadoPedido estadoNuevo, LocalDateTime fechaHoraCambio) {
        this.idHistorial = idHistorial;
        this.idPedido = idPedido;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.fechaHoraCambio = fechaHoraCambio;
    }

    public HistorialEstado() {
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public EstadoPedido getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(EstadoPedido estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public EstadoPedido getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(EstadoPedido estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public LocalDateTime getFechaHoraCambio() {
        return fechaHoraCambio;
    }

    public void setFechaHoraCambio(LocalDateTime fechaHoraCambio) {
        this.fechaHoraCambio = fechaHoraCambio;
    }

    @Override
    public String toString() {
        return "HistorialEstado{" + "idHistorial=" + idHistorial + ", idPedido=" + idPedido + ", estadoAnterior=" + estadoAnterior + ", estadoNuevo=" + estadoNuevo + ", fechaHoraCambio=" + fechaHoraCambio + '}';
    }
    
    
}
