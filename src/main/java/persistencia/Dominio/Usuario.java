/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.Dominio;

/**
 *
 * @author Tungs
 */
public class Usuario {
    private int idUsuario;
    private String correoElectronico;
    private String hashContraseña;
    private RolUsuario rol;

    public Usuario(int idUsuario, String correoElectronico, String hashContraseña, RolUsuario rol) {
        this.idUsuario = idUsuario;
        this.correoElectronico = correoElectronico;
        this.hashContraseña = hashContraseña;
        this.rol = rol;
    }

    public Usuario() {
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getHashContraseña() {
        return hashContraseña;
    }

    public void setHashContraseña(String hashContraseña) {
        this.hashContraseña = hashContraseña;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", correoElectronico=" + correoElectronico + ", hashContrase\u00f1a=" + hashContraseña + ", rol=" + rol + '}';
    }
    
    
}
