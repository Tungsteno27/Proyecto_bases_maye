/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Logger;
import persistencia.Dominio.Cupon;
import persistencia.Dominio.PedidoProgramado;
import persistencia.Exception.PersistenciaException;
import persistencia.conexion.IConexionBD;

/**
 *
 * @author Tungs
 */
public class PedidoProgramadoDAO implements IPedidoProgramadoDAO {
    
    private final IConexionBD conexionBD;
    
    private static final Logger LOG = Logger.getLogger(ProductoDAO.class.getName());
    
    public PedidoProgramadoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    /**
     * Método que inserta un pedido programado a la base de datos, este método deberá ser llamado única y exclusivamente por la BO.
     * @param pedidoProg el pedido programado a insertar en la BD
     * @throws PersistenciaException si ocurre un error de SQL
     */
    @Override
    public void insertarPedidoProgramado(PedidoProgramado pedidoProg) throws PersistenciaException {
        String comandoSQL = "INSERT INTO PedidoProgramados (idPedidoProgramado, idCupon) VALUES (?, ?)";
        try(Connection conn=conexionBD.crearConexion();
                PreparedStatement ps = conn.prepareStatement(comandoSQL)){
            ps.setInt(1, pedidoProg.getIdPedidoProgramado());
            if(pedidoProg.getCupon()==null){
                ps.setNull(2, Types.INTEGER);
            }else{
                ps.setInt(2, pedidoProg.getCupon().getIdCupon());
            }
            ps.executeUpdate();
            
        }catch(SQLException ex){
            throw new PersistenciaException("No se pudo insertar el pedido programado");
        } 
    }
    
    /**
     * Método que aplica un cupón a un pedido, que por la regla de negocio tiene que ser Programado
     * @param idPedido el id del pedido a aplicar el descuento
     * @param idCupon el cupón a aplicar
     * @throws PersistenciaException si ocurrió un error al intentar aplicar el cupón 
     */
    @Override
    public void aplicarCupon(int idPedido, int idCupon) throws PersistenciaException {
        //Aquí se usa el método también sacado de la DB profe maye
        String sql = "{CALL aplicarDescuento(?, ?)}";

        try (Connection conn = conexionBD.crearConexion();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idPedido);
            cs.setInt(2, idCupon);
            cs.execute();
            LOG.info("Cupón " + idCupon + " aplicado al pedido: " + idPedido);

        } catch (SQLException e) {
            LOG.severe("Error al aplicar cupón: " + e.getMessage());
            throw new PersistenciaException("Error al aplicar cupón: " + e.getMessage());
        }
    }
    /**
     * Método que obtiene un pedidoProgramado por su id
     * @param idPedido el id del pedido a buscar
     * @return el pedidoProgramado si lo encontró
     * @throws PersistenciaException si ocurre un error al intentar obtener el pedido programado
     */
    @Override
    public PedidoProgramado obtenerPorId(int idPedido) throws PersistenciaException {
        PedidoProgramado pedidoProgramado = null;
        String sql = "SELECT pp.idPedidoProgramado, c.idCupon, c.nombre, "
                   + "c.porcentaje, c.usosTotales, c.usosActuales, "
                   + "c.fechaInicio, c.fechaFin "
                   + "FROM PedidoProgramados pp "
                   + "LEFT JOIN Cupones c ON pp.idCupon = c.idCupon "
                   + "WHERE pp.idPedidoProgramado = ?";

        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPedido);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pedidoProgramado = new PedidoProgramado();
                    pedidoProgramado.setIdPedidoProgramado(rs.getInt("idPedidoProgramado"));

                    if (rs.getObject("idCupon") != null) {
                        Cupon cupon = new Cupon();
                        cupon.setIdCupon(rs.getInt("idCupon"));
                        cupon.setNombre(rs.getString("nombre"));
                        cupon.setPorcentaje(rs.getDouble("porcentaje"));
                        cupon.setUsosTotales(rs.getInt("usosTotales"));
                        cupon.setUsosActuales(rs.getInt("usosActuales"));
                        cupon.setFechaInicio(rs.getTimestamp("fechaInicio").toLocalDateTime());

                        if (rs.getTimestamp("fechaFin") != null) {
                            cupon.setFechaFin(rs.getTimestamp("fechaFin").toLocalDateTime());
                        }

                        pedidoProgramado.setCupon(cupon);
                    } else {
                        pedidoProgramado.setCupon(null);
                    }
                }
            }

        } catch (SQLException e) {
            LOG.severe("Error al intentar obtener  el pedido programado: " + e.getMessage());
            throw new PersistenciaException("Error al obtener pedido programado: " + e.getMessage());
        }

        return pedidoProgramado;
    }
    
    
}