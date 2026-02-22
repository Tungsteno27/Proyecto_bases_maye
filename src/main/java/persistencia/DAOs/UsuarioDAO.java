/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import persistencia.Dominio.RolUsuario;
import persistencia.Dominio.Usuario;
import persistencia.Exception.PersistenciaException;
import persistencia.conexion.IConexionBD;

/**
 *
 * @author Tungs
 */
public class UsuarioDAO implements IUsuarioDAO {
    
    private final IConexionBD conexionBD;
    
    public UsuarioDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    public int insertarUsuario(Usuario usuario) throws PersistenciaException {
        String sql = "INSERT INTO Usuarios (correoElectronico, hashContraseña, rol) VALUES (?, ?, ?)";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getCorreoElectronico());
            ps.setString(2, usuario.getHashContraseña());
            ps.setString(3, usuario.getRol().name());
            ps.executeUpdate();

            // Obtiene el ID generado
            try (PreparedStatement ps2 = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                 ResultSet rs = ps2.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al insertar usuario: " + e.getMessage());
        }

        throw new PersistenciaException("No se pudo obtener el ID del usuario insertado");
    }

    public Usuario obtenerPorCorreo(String correo) throws PersistenciaException {
        Usuario usuario = null;
        String sql = "SELECT idUsuario, correoElectronico, hashContraseña, rol " +
                     "FROM Usuarios WHERE correoElectronico = ?";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("idUsuario"));
                    usuario.setCorreoElectronico(rs.getString("correoElectronico"));
                    usuario.setHashContraseña(rs.getString("hashContraseña"));
                    usuario.setRol(RolUsuario.valueOf(rs.getString("rol").toUpperCase()));
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener usuario: " + e.getMessage());
        }

        return usuario; 
    }

    public boolean existeCorreo(String correo) throws PersistenciaException {
        String sql = "SELECT COUNT(*) FROM Usuarios WHERE correoElectronico = ?";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al verificar correo: " + e.getMessage());
        }

        return false;
    }
}
