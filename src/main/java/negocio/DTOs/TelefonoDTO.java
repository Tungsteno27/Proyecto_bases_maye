/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.DTOs;

/**
 *
 * @author julian izaguirre
 */
public class TelefonoDTO {
    private int idCliente;
    private String numero;
    private String etiqueta;
    // etiqueta de casa, work celular
    private int idTelefono;

    public TelefonoDTO() {
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    
    public TelefonoDTO(String numero, String etiqueta) {
        this.numero = numero;
        this.etiqueta = etiqueta;
    }
     
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public int getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(int idTelefono) {
        this.idTelefono = idTelefono;
    }

    @Override
    public String toString() {
        return "TelefonoDTO{" + "numero=" + numero + ", etiqueta=" + etiqueta + ", idTelefono=" + idTelefono + '}';
    }  
}
