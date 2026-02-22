/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import persistencia.Dominio.DireccionCliente;
import persistencia.Exception.PersistenciaException;
import persistencia.conexion.IConexionBD;

/**
 *
 * @author Tungs
 */
public class DireccionDAO implements IDireccionDAO {
    
     
    private final IConexionBD conexionBD;
    
    public DireccionDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    public int insertarDireccion(DireccionCliente direccion) throws PersistenciaException {
        String sql = "INSERT INTO DireccionesClientes (calle, numero, colonia) VALUES (?, ?, ?)";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, direccion.getCalle());
            ps.setInt(2, direccion.getNumero());
            ps.setString(3, direccion.getColonia());
            ps.executeUpdate();

            try (PreparedStatement ps2 = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                 ResultSet rs = ps2.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al insertar dirección: " + e.getMessage());
        }

        throw new PersistenciaException("No se pudo obtener el ID de la dirección insertada");
    }
    
}
