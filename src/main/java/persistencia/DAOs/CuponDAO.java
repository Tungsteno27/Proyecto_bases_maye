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
import persistencia.Exception.PersistenciaException;
import persistencia.conexion.IConexionBD;

/**
 * Clase que interactua directamente con la tabla "cupones" de la base de datos
 * @author Tungs
 */
public class CuponDAO implements ICuponDAO{
    
    private final IConexionBD conexionBD;
    
    private static final Logger LOG = Logger.getLogger(ProductoDAO.class.getName());
    
    public CuponDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    /**
     * Método que obtiene un cupón por su nombre
     * @param nombre el nombre del cupón a obtener
     * @return null si no hay un cupon con ese nombre, el cupon en caso contrario
     * @throws PersistenciaException si ocurre un error en el sql
     */
    @Override
    public Cupon obtenerCuponNombre(String nombre) throws PersistenciaException {
        Cupon cupon=null;
        String comandoSQL = "select idCupon, usosTotales, usosActuales, nombre, porcentaje, fechaInicio, fechaFin from cupones where nombre= ?";
        try(Connection conn = conexionBD.crearConexion();
                PreparedStatement ps= conn.prepareStatement(comandoSQL)
                 ){
            
            ps.setString(1, nombre);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    cupon= new Cupon();
                    cupon.setIdCupon(rs.getInt("idCupon"));
                    cupon.setNombre(rs.getString("nombre"));
                    cupon.setUsosTotales(rs.getInt("usosTotales"));
                    cupon.setUsosActuales(rs.getInt("usosActuales"));
                    cupon.setPorcentaje(rs.getDouble("porcentaje"));
                    cupon.setFechaInicio(rs.getTimestamp("fechaInicio").toLocalDateTime());
                    
                    if(rs.getTimestamp("fechaFin") !=null){
                        cupon.setFechaFin(rs.getTimestamp("fechaFin").toLocalDateTime());         
                    }
                }
            }          
        }catch(SQLException e) {
            throw new PersistenciaException("Error al obtener el cupon por id: " + e.getMessage());
        }
        return cupon;
    }
    
    /**
     * Método que valida que un cupón aún tenga usos disponibles y su fecha no haya expirado
     * @param idCupon el id del cupón a validar
     * @return verdadero si es válido, falso en caso contrario
     * @throws PersistenciaException si ocurre un error en el sql
     */
    public boolean validarCupon(int idCupon) throws PersistenciaException {
        String comandoSQL = "{? = CALL validarCupon(?)}";

        try (Connection conn = conexionBD.crearConexion();
             CallableStatement cs = conn.prepareCall(comandoSQL)) {

            cs.registerOutParameter(1, Types.BOOLEAN);
            cs.setInt(2, idCupon);
            cs.execute();

            return cs.getBoolean(1);

        } catch (SQLException e) {
            throw new PersistenciaException("Ocurrió un Error al validar el cupón: " + e.getMessage());
        }
    }
    
    
}
