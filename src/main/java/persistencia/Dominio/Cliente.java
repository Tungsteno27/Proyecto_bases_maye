/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.Dominio;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class Cliente {
    private int idCliente; 
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;
    private EstatusCliente estatus;
    private DireccionCliente direccion;       
    private List<TelefonoCliente> telefonos;

    public Cliente(int idCliente, String nombres, String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento, EstatusCliente estatus, DireccionCliente direccion, List<TelefonoCliente> telefonos) {
        this.idCliente = idCliente;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.estatus = estatus;
        this.direccion = direccion;
        this.telefonos = telefonos;
    }

    public Cliente() {
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public EstatusCliente getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusCliente estatus) {
        this.estatus = estatus;
    }

    public DireccionCliente getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionCliente direccion) {
        this.direccion = direccion;
    }

    public List<TelefonoCliente> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<TelefonoCliente> telefonos) {
        this.telefonos = telefonos;
    }

    @Override
    public String toString() {
        return "Cliente{" + "idCliente=" + idCliente + ", nombres=" + nombres + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", fechaNacimiento=" + fechaNacimiento + ", estatus=" + estatus + ", direccion=" + direccion + ", telefonos=" + telefonos + '}';
    }
    
    
    
}
