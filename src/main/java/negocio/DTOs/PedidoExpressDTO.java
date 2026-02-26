/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.DTOs;

import java.util.List;

/**
 *
 * @author julian izaguirre
 */
public class PedidoExpressDTO {
    private int idPedidoExpress;
    private int folio;
    private String pin;
    private List<ProductoCarritoDTO> carrito;

    public PedidoExpressDTO() {
    }

    public PedidoExpressDTO(int idPedidoExpress, int folio, String pin, List<ProductoCarritoDTO> carrito) {
        this.idPedidoExpress = idPedidoExpress;
        this.folio = folio;
        this.pin = pin;
        this.carrito = carrito;
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public List<ProductoCarritoDTO> getCarrito() {
        return carrito;
    }

    public void setCarrito(List<ProductoCarritoDTO> carrito) {
        this.carrito = carrito;
    }
    
    
}