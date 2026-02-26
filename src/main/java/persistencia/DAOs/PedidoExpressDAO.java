/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import persistencia.Dominio.PedidoExpress;
import persistencia.Exception.PersistenciaException;
import persistencia.conexion.IConexionBD;

/**
 *
 * @author julian izaguirre
 */
public class PedidoExpressDAO implements IPedidoExpressDAO {

    private IConexionBD conexion;

    public PedidoExpressDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public void insertarPedidoExpress(PedidoExpress pedidoExpress) throws PersistenciaException {
        String sql = "INSERT INTO PedidoExpress (idPedidoExpress, folio, pinSeguridad) VALUES (?, ?, ?)";

        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pedidoExpress.getIdPedidoExpress());
            ps.setInt(2, pedidoExpress.getFolio());
            ps.setString(3, pedidoExpress.getPinSeguridad());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException("Error al insertar pedido express: " + e.getMessage());
        }
    }

    @Override
    public PedidoExpress obtenerPorFolio(int folio) throws PersistenciaException {
        PedidoExpress pedidoExpress = null;
        String sql = "SELECT pe.idPedidoExpress, pe.folio, pe.pinSeguridad FROM PedidoExpress pe INNER JOIN Pedidos p ON pe.idPedidoExpress = p.idPedido WHERE pe.folio = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, folio);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pedidoExpress = new PedidoExpress();
                    pedidoExpress.setIdPedidoExpress(rs.getInt("idPedidoExpress"));
                    pedidoExpress.setFolio(rs.getInt("folio"));
                    pedidoExpress.setPinSeguridad(rs.getString("pinSeguridad"));
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener pedido express: " + e.getMessage());
        }

        return pedidoExpress;
    }

    @Override
    public boolean validarPin(int folio, String pinIngresado) throws PersistenciaException {
        PedidoExpress pe = obtenerPorFolio(folio);
        
        if (pe == null) {
            throw new PersistenciaException("No existe un pedido express con ese folio");
        }
        //El mismo que se usó para encriptar el hash de las contraseñas, el confiable BCrypt
        return BCrypt.checkpw(pinIngresado, pe.getPinSeguridad());
    }

    @Override
    public List<PedidoExpress> obtenerExpressListos() throws PersistenciaException {
        List<PedidoExpress> lista = new ArrayList<>();
        String sql = "SELECT pe.idPedidoExpress, pe.folio, pe.pinSeguridad FROM PedidoExpress pe INNER JOIN Pedidos p ON pe.idPedidoExpress = p.idPedido WHERE p.estado = 'Listo'";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PedidoExpress pe = new PedidoExpress();
                pe.setIdPedidoExpress(rs.getInt("idPedidoExpress"));
                pe.setFolio(rs.getInt("folio"));
                pe.setPinSeguridad(rs.getString("pinSeguridad"));
                lista.add(pe);
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener pedidos express listos: " + e.getMessage());
        }

        return lista;
    }
    
}