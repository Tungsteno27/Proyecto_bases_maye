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
    
    
}
