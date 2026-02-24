/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.Dominio.Cliente;
import persistencia.Dominio.DireccionCliente;
import persistencia.Dominio.EstatusCliente;
import persistencia.Exception.PersistenciaException;
import persistencia.conexion.IConexionBD;

/**
 *
 * @author julian izaguirre
 */
public class ClienteDAO implements IClienteDAO {
    
    private final IConexionBD conexionBD;
    
    public ClienteDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public void insertarCliente(Cliente cliente) throws PersistenciaException {
        String sql = "INSERT INTO Clientes (idCliente, nombres, apellidoPaterno, apellidoMaterno, " +
                     "fechaNacimiento, estatus, idDireccion) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cliente.getIdCliente()); 
            ps.setString(2, cliente.getNombres());
            ps.setString(3, cliente.getApellidoPaterno());

            if (cliente.getApellidoMaterno() == null) {
                ps.setNull(4, Types.VARCHAR);
            } else {
                ps.setString(4, cliente.getApellidoMaterno());
            }

            ps.setDate(5, Date.valueOf(cliente.getFechaNacimiento()));
            ps.setString(6, cliente.getEstatus().name());

            if (cliente.getDireccion() == null) {
                ps.setNull(7, Types.INTEGER);
            } else {
                ps.setInt(7, cliente.getDireccion().getIdDireccion());
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException("Error al insertar cliente: " + e.getMessage());
        }
    }

    @Override
    public boolean actualizarCliente(Cliente cliente) throws PersistenciaException {
        String comandoSQL = "UPDATE Clientes SET nombres = ?, apellidoPaterno = ?, apellidoMaterno = ?, fechaNacimiento = ?, estatus = ?, idDireccion = ? WHERE idCliente = ?";
        
        try (Connection conn = conexionBD.crearConexion();
                PreparedStatement ps = conn.prepareStatement(comandoSQL)){
            
            ps.setString(1, cliente.getNombres());
            ps.setString(2, cliente.getApellidoPaterno());
            if (cliente.getApellidoMaterno() == null || cliente.getApellidoMaterno().isEmpty()) {
                ps.setNull(3, java.sql.Types.VARCHAR);
            } else {
                ps.setString(3, cliente.getApellidoMaterno());
            }
            ps.setDate(4, Date.valueOf(cliente.getFechaNacimiento()));
            ps.setString(5, cliente.getEstatus().name());
            if (cliente.getDireccion() == null) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, cliente.getDireccion().getIdDireccion());
            }
            ps.setInt(7, cliente.getIdCliente());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            throw new PersistenciaException("Error al actualizar cliente: " + ex.getMessage());
        }
    }

    @Override
    public Cliente buscarPorId(int idCliente) throws PersistenciaException {
        // Usamos LEFT JOIN para traer la dirección de una vez (si es que tiene una)
        String comandoSQL = "SELECT c.idCliente, c.nombres, c.apellidoPaterno, c.apellidoMaterno, " +
                            "c.fechaNacimiento, c.estatus, c.idDireccion, " +
                            "d.calle, d.numero, d.colonia " +
                            "FROM Clientes c " +
                            "LEFT JOIN DireccionesClientes d ON c.idDireccion = d.idDireccion " +
                            "WHERE c.idCliente = ?";
        
        Cliente cliente = null;

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(comandoSQL)) {
             
            ps.setInt(1, idCliente);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("idCliente"));
                    cliente.setNombres(rs.getString("nombres"));
                    cliente.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    cliente.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    cliente.setEstatus(EstatusCliente.valueOf(rs.getString("estatus").toUpperCase()));
                    cliente.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate()); 

                    int idDireccion = rs.getInt("idDireccion");

                    if (!rs.wasNull()) { 
                        DireccionCliente direccion = new DireccionCliente();
                        direccion.setIdDireccion(idDireccion);
                        direccion.setCalle(rs.getString("calle"));
                        direccion.setNumero(rs.getInt("numero"));
                        direccion.setColonia(rs.getString("colonia"));

                        cliente.setDireccion(direccion);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Error al buscar el cliente con ID: " + idCliente + " detalle: " + ex.getMessage());
        }
        return cliente; 
    }
    
    @Override
    public java.util.List<Cliente> obtenerTodosLosClientes() throws PersistenciaException {
        java.util.List<Cliente> listaClientes = new java.util.ArrayList<>();
        
        String comandoSQL = "SELECT c.idCliente, c.nombres, c.apellidoPaterno, c.apellidoMaterno, c.estatus, " +
                            "(SELECT numero FROM TelefonosClientes t WHERE t.idCliente = c.idCliente LIMIT 1) AS telefono " +
                            "FROM Clientes c " +
                            "INNER JOIN Usuarios u ON c.idCliente = u.idUsuario " +
                            "WHERE u.rol = 'CLIENTE'";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(comandoSQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombres(rs.getString("nombres"));
                cliente.setApellidoPaterno(rs.getString("apellidoPaterno"));
                cliente.setApellidoMaterno(rs.getString("apellidoMaterno"));
                cliente.setEstatus(EstatusCliente.valueOf(rs.getString("estatus").toUpperCase()));
                // Mapeamos el telefono temporalmente a la lista del cliente si es que tiene uno
                String numTelefono = rs.getString("telefono");
                if (numTelefono != null) {
                    java.util.List<persistencia.Dominio.TelefonoCliente> telefonos = new java.util.ArrayList<>();
                    persistencia.Dominio.TelefonoCliente tel = new persistencia.Dominio.TelefonoCliente();
                    tel.setNumero(numTelefono);
                    telefonos.add(tel);
                    cliente.setTelefonos(telefonos);
                }

                listaClientes.add(cliente);
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Error al obtener la lista de clientes: " + ex.getMessage());
        }
        return listaClientes;
    }
    
    /**
     * 
     * @param idCliente
     * @throws PersistenciaException 
     */
    @Override
    public void darDeBajaCliente(int idCliente) throws PersistenciaException {
        String comandoSQL = "UPDATE Clientes SET estatus = 'INACTIVO' WHERE idCliente = ?";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(comandoSQL)) {
            
            ps.setInt(1, idCliente);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            throw new PersistenciaException("Error al dar de baja al cliente: " + ex.getMessage());
        }
    }
    
    /**
     * 
     * @param idCliente
     * @param estatus
     * @throws PersistenciaException 
     */
    @Override
    public void cambiarEstatus(int idCliente, persistencia.Dominio.EstatusCliente estatus) throws PersistenciaException {
        String sql = "UPDATE Clientes SET estatus = ? WHERE idCliente = ?";
        try (Connection conn = conexionBD.crearConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estatus.name());
            ps.setInt(2, idCliente);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Error al cambiar el estatus en BD: " + ex.getMessage());
        }
    }
}
