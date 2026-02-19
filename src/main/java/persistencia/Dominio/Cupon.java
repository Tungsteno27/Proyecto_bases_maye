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
public class Cupon {
    private int idCupon;
    private int usosTotales;
    private int usosActuales;
    private String nombre;
    private Double porcentaje;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public Cupon(int idCupon, int usosTotales, int usosActuales, String nombre, Double porcentaje, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.idCupon = idCupon;
        this.usosTotales = usosTotales;
        this.usosActuales = usosActuales;
        this.nombre = nombre;
        this.porcentaje = porcentaje;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Cupon() {
    }

    public int getIdCupon() {
        return idCupon;
    }

    public void setIdCupon(int idCupon) {
        this.idCupon = idCupon;
    }

    public int getUsosTotales() {
        return usosTotales;
    }

    public void setUsosTotales(int usosTotales) {
        this.usosTotales = usosTotales;
    }

    public int getUsosActuales() {
        return usosActuales;
    }

    public void setUsosActuales(int usosActuales) {
        this.usosActuales = usosActuales;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Cupon{" + "idCupon=" + idCupon + ", usosTotales=" + usosTotales + ", usosActuales=" + usosActuales + ", nombre=" + nombre + ", porcentaje=" + porcentaje + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + '}';
    }
    
    
    
}
