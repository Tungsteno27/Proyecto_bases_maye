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

    @Override
    public int insertarCliente(Cliente cliente) throws PersistenciaException {
        String comandoSQL = "INSERT INTO Clientes (nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, estatus, idDireccion) VALUES\n" +
            "(?, ?, ?, ?, ?)";
        //El return generated keys es para que el método regrese el id del pedido insertado
        try (Connection conn = conexionBD.crearConexion();
                PreparedStatement ps = conn.prepareStatement(comandoSQL, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, cliente.getNombres());
            ps.setString(2, cliente.getApellidoPaterno());
            ps.setString(3, cliente.getApellidoMaterno());
            ps.setDate(4, Date.valueOf(cliente.getFechaNacimiento()));
            ps.setString(5, cliente.getEstatus().name());
            ps.setInt(6, cliente.getDireccion().getIdDireccion());
            
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            
        } catch (SQLException ex) {
            throw new PersistenciaException("Error al insertar cliente: " + ex.getMessage());
        }
        throw new PersistenciaException("so se pudo obtener el ID del cliente");
    }
    
    /**
     * 
     * @param cliente
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public boolean actualizarCliente(Cliente cliente) throws PersistenciaException {
        String comandoSQL = "UPDATE Clientes SET nombres = ?, apellidoPaterno = ?, apellidoMaterno = ?, fechaNacimiento = ?, estatus = ?, idDireccion = ? WHERE idCliente = ?";
        
        try (Connection conn = conexionBD.crearConexion();
                PreparedStatement ps = conn.prepareStatement(comandoSQL)){
            
            ps.setString(1, cliente.getNombres());
            ps.setString(2, cliente.getApellidoPaterno());
            ps.setString(3, cliente.getApellidoMaterno());
            ps.setDate(4, Date.valueOf(cliente.getFechaNacimiento()));
            ps.setString(5, cliente.getEstatus().name());
            ps.setInt(6, cliente.getDireccion().getIdDireccion());
            
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
                    cliente.setEstatus(EstatusCliente.valueOf(rs.getString("estatus")));
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
    
    
    
    
}
