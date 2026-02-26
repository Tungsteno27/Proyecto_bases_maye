/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.Dominio;

/**
 *
 * @author Tungs
 */
public class DireccionCliente {
    private int idDireccion;
    private String calle;
    private int numero;
    private String colonia;

    public DireccionCliente(int idDireccion, String calle, int numero, String colonia) {
        this.idDireccion = idDireccion;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
    }

    public DireccionCliente() {
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    @Override
    public String toString() {
        return "DireccionCliente{" + "idDireccion=" + idDireccion + ", calle=" + calle + ", numero=" + numero + ", colonia=" + colonia + '}';
    }
    
    
}
