/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.DTOs;

import persistencia.Dominio.RolUsuario;

/**
 *
 * @author Tungs
 */
public class UsuarioDTO {
    private String correoElectronico;
    private int idUsuario;
    private RolUsuario rol;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public UsuarioDTO(int idUsuario, String correoElectronico, RolUsuario rol, String password) {
        this.idUsuario = idUsuario;
        this.rol = rol;
        this.correoElectronico=correoElectronico;
        this.password= password; 
    }

    public UsuarioDTO() {
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    
    
}
