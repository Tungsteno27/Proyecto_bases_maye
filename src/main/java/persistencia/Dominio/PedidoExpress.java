/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.Dominio;

/**
 *
 * @author Tungs
 */
public class PedidoExpress {
    private int idPedidoExpress; 
    private int folio;
    private String pinSeguridad; 

    public PedidoExpress(int idPedidoExpress, int folio, String pinSeguridad) {
        this.idPedidoExpress = idPedidoExpress;
        this.folio = folio;
        this.pinSeguridad = pinSeguridad;
    }

    public PedidoExpress() {
    }

    
    public int getIdPedidoExpress() {
        return idPedidoExpress;
    }

    public void setIdPedidoExpress(int idPedidoExpress) {
        this.idPedidoExpress = idPedidoExpress;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public String getPinSeguridad() {
        return pinSeguridad;
    }

    public void setPinSeguridad(String pinSeguridad) {
        this.pinSeguridad = pinSeguridad;
    }
    
    
}
