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
import java.util.ArrayList;
import java.util.List;
import persistencia.Dominio.EstadoPedido;

/**
 *
 * @author Tungs
 */
public class PedidoDAO implements IPedidoDAO {

    private final IConexionBD conexionBD;

    private static final Logger LOG = Logger.getLogger(ProductoDAO.class.getName());

    public PedidoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public int insertarPedido(Pedido pedido) throws PersistenciaException {
        String comandoSQL = "INSERT INTO Pedidos (estado, idCliente, totalPagar) VALUES (?, ?, ?)";

        try (Connection conexion = conexionBD.crearConexion(); //El return generated keys es para que el método regrese el id del pedido insertado
                 PreparedStatement ps = conexion.prepareStatement(comandoSQL, Statement.RETURN_GENERATED_KEYS)) {

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
                    //Se regresa el id para luego poder crear el pedido programado o express
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al insertar pedido: " + e.getMessage());
        }
        throw new PersistenciaException("No se pudo obtener el ID del pedido a insertar");
    }

    @Override
    public List<Pedido> consultarPedidosProgramadosPorCliente(Integer idCliente)
            throws PersistenciaException {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = """
                    SELECT p.idPedido, p.fechaCreacion, p.estado, p.idCliente, p.totalPagar
                    FROM Pedidos p
                    INNER JOIN PedidoProgramados pp
                    ON p.idPedido = pp.idPedidoProgramado
                    WHERE p.idCliente = ?
                    """;

        try (Connection conexion = conexionBD.crearConexion(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Pedido pedido = new Pedido();

                    pedido.setIdPedido(rs.getInt("idPedido"));
                    pedido.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
                    pedido.setEstado(
                            EstadoPedido.valueOf(
                                    rs.getString("estado").toUpperCase()
                                    //pq en el enum viene en mayuscula pero en la base viene en minuscula
                            )
                    );
                    pedido.setIdCliente(rs.getInt("idCliente"));
                    pedido.setTotalPagar(rs.getDouble("totalPagar"));

                    pedidos.add(pedido);
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar pedidos programados: " + e.getMessage());
        }

        return pedidos;
    }

}
