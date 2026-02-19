/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.Dominio;

/**
 *
 * @author Tungs
 */
public class PedidoProgramado {
    private int idPedidoProgramado; 
    private Cupon cupon;

    public PedidoProgramado(int idPedidoProgramado, Cupon cupon) {
        this.idPedidoProgramado = idPedidoProgramado;
        this.cupon = cupon;
    }

    public PedidoProgramado() {
    }

    public int getIdPedidoProgramado() {
        return idPedidoProgramado;
    }

    public void setIdPedidoProgramado(int idPedidoProgramado) {
        this.idPedidoProgramado = idPedidoProgramado;
    }

    public Cupon getCupon() {
        return cupon;
    }

    public void setCupon(Cupon cupon) {
        this.cupon = cupon;
    }

    @Override
    public String toString() {
        return "PedidoProgramado{" + "idPedidoProgramado=" + idPedidoProgramado + ", cupon=" + cupon + '}';
    }
    
    
}
