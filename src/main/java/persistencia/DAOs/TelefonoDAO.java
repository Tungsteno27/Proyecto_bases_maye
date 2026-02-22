/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import persistencia.Dominio.TelefonoCliente;
import persistencia.Exception.PersistenciaException;
import persistencia.conexion.IConexionBD;

/**
 *
 * @author Tungs
 */
public class TelefonoDAO implements ITelefonoDAO{
    
    private final IConexionBD conexionBD;
    
    public TelefonoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    public void insertarTelefono(TelefonoCliente telefono) throws PersistenciaException {
    String sql = "INSERT INTO TelefonosClientes (idCliente, numero, etiqueta) VALUES (?, ?, ?)";

    try (Connection conn = conexionBD.crearConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, telefono.getIdCliente());
        ps.setString(2, telefono.getNumero());

        if (telefono.getEtiqueta() == null) {
            ps.setNull(3, Types.VARCHAR);
        } else {
            ps.setString(3, telefono.getEtiqueta());
        }

        ps.executeUpdate();

    } catch (SQLException e) {
        throw new PersistenciaException("Error al insertar teléfono: " + e.getMessage());
    }
}
}
