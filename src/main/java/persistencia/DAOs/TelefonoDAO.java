/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
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
    
    @Override
    public List<TelefonoCliente> buscarPorIdCliente(int idCliente) throws PersistenciaException {
        java.util.List<TelefonoCliente> telefonos = new java.util.ArrayList<>();
        String sql = "SELECT idTelefono, idCliente, numero, etiqueta FROM TelefonosClientes WHERE idCliente = ?";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TelefonoCliente tel = new TelefonoCliente();
                    tel.setIdTelefono(rs.getInt("idTelefono"));
                    tel.setIdCliente(rs.getInt("idCliente"));
                    tel.setNumero(rs.getString("numero"));
                    tel.setEtiqueta(rs.getString("etiqueta"));
                    
                    telefonos.add(tel);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al buscar los teléfonos del cliente: " + e.getMessage());
        }
        return telefonos;
    }

    @Override
    public void actualizarTelefono(TelefonoCliente telefono) throws PersistenciaException {
        String sql = "UPDATE TelefonosClientes SET numero = ?, etiqueta = ? WHERE idTelefono = ?";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, telefono.getNumero());

            if (telefono.getEtiqueta() == null || telefono.getEtiqueta().isEmpty()) {
                ps.setNull(2, Types.VARCHAR);
            } else {
                ps.setString(2, telefono.getEtiqueta());
            }

            ps.setInt(3, telefono.getIdTelefono());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizar el teléfono: " + e.getMessage());
        }
    }
    
}
