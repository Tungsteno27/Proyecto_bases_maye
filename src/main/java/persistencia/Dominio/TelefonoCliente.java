/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.Dominio;

/**
 *
 * @author Tungs
 */
public class TelefonoCliente {
    private int idTelefono;
    private int idCliente;  
    private String numero;
    private String etiqueta;

    public TelefonoCliente(int idTelefono, int idCliente, String numero, String etiqueta) {
        this.idTelefono = idTelefono;
        this.idCliente = idCliente;
        this.numero = numero;
        this.etiqueta = etiqueta;
    }

    public TelefonoCliente() {
    }

    public int getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(int idTelefono) {
        this.idTelefono = idTelefono;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    @Override
    public String toString() {
        return "TelefonoCliente{" + "idTelefono=" + idTelefono + ", idCliente=" + idCliente + ", numero=" + numero + ", etiqueta=" + etiqueta + '}';
    }
    
    
}

