/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Logger;
import persistencia.Dominio.PedidoProducto;
import persistencia.Exception.PersistenciaException;
import persistencia.conexion.IConexionBD;

/**
 *
 * @author Tungs
 */
public class PedidoProductoDAO implements IPedidoProductoDAO{
    
    private final IConexionBD conexionBD;
    
    private static final Logger LOG = Logger.getLogger(ProductoDAO.class.getName());
    
    public PedidoProductoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    public void insertarPedidoProducto(PedidoProducto pedidoProd) throws PersistenciaException {
    String comandoSQL = "INSERT INTO PedidoProductos (idPedido, idProducto, cantidad, precio_unitario, subTotal, notas_adicionales) " +
                 "VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = conexionBD.crearConexion();
         PreparedStatement ps = conn.prepareStatement(comandoSQL)) {

        ps.setInt(1, pedidoProd.getIdPedido());
        ps.setInt(2, pedidoProd.getProducto().getIdProducto());
        ps.setInt(3, pedidoProd.getCantidad());
        ps.setDouble(4, pedidoProd.getPrecioUnitario());
        ps.setDouble(5, pedidoProd.getSubTotal());

        if (pedidoProd.getNotasAdicionales() == null) {
            ps.setNull(6, Types.VARCHAR);
        } else {
            ps.setString(6, pedidoProd.getNotasAdicionales());
        }

        ps.executeUpdate();

    } catch (SQLException e) {
        throw new PersistenciaException("Error al insertar un pedidoProducto" + e.getMessage());
    }
}
}
