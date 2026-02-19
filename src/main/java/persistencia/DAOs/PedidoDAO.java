/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.logging.Logger;
import persistencia.Dominio.Pedido;
import persistencia.Exception.PersistenciaException;
import persistencia.conexion.IConexionBD;

/**
 *
 * @author Tungs
 */
public class PedidoDAO implements IPedidoDAO{
    
    private final IConexionBD conexionBD;
    
    private static final Logger LOG = Logger.getLogger(ProductoDAO.class.getName());
    
    public PedidoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    public int insertarPedido(Pedido pedido) throws PersistenciaException {
        String comandoSQL = "INSERT INTO Pedidos (estado, idCliente, totalPagar) VALUES (?, ?, ?)";

        try (Connection conn = conexionBD.crearConexion();                      //El return generated keys es para que el método regrese el id del pedido insertado
             PreparedStatement ps = conn.prepareStatement(comandoSQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, pedido.getEstado().toString());
            //Esta validación es por si el pedido es express, es decir, no tiene un idCliente asociado, de ahí el setNull
            if (pedido.getIdCliente() == null) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, pedido.getIdCliente());
            }
            ps.setDouble(3, pedido.getTotalPagar());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    //Se retorna el id para luego poder crear el pedido programado o express, dependiendo del caso
                    return rs.getInt(1); 
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al insertar pedido: " + e.getMessage());
        }
        throw new PersistenciaException("No se pudo obtener el ID del pedido a insertar");
    }
    
    

}
