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
import java.util.logging.Logger;
import persistencia.Dominio.EstadoProducto;
import persistencia.Dominio.Producto;
import persistencia.Dominio.TamanioProducto;
import persistencia.Exception.PersistenciaException;
import persistencia.conexion.IConexionBD;

/**
 *
 * @author Tungs
 */
public class ProductoDAO implements IProductoDAO {
    private final IConexionBD conexionBD;
    
    private static final Logger LOG = Logger.getLogger(ProductoDAO.class.getName());
    
    public ProductoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    /**
     * Método que regresa todos los pedidos disponibles
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public List<Producto> obtenerProductosDisponibles() throws PersistenciaException {
        List<Producto> productos = new ArrayList<>();
        String comandoSQL = "SELECT idProducto, nombre, estado, precio, descripcion, tamanio FROM Productos WHERE estado = 'Disponible'";
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(comandoSQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("nombre"));
                p.setEstado(EstadoProducto.valueOf(rs.getString("estado").toUpperCase()));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setTamanio(TamanioProducto.valueOf(rs.getString("tamanio").toUpperCase()));
                productos.add(p);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener los productos disponibles: " + e.getMessage());
        }
        return productos;
    }

    @Override
    public Producto obtenerProductoPorId(int id) throws PersistenciaException {
        Producto producto=null;
        String comandoSQL = "SELECT idProducto, nombre, estado, precio, descripcion, tamanio FROM Productos WHERE idProducto=?";
        try(Connection conn = conexionBD.crearConexion();
                PreparedStatement ps= conn.prepareStatement(comandoSQL)
                 ){
            
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    producto.setIdProducto(rs.getInt("idProducto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setEstado(EstadoProducto.valueOf(rs.getString("estado").toUpperCase()));
                    producto.setPrecio(rs.getDouble("precio"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    producto.setTamanio(TamanioProducto.valueOf(rs.getString("tamanio").toUpperCase()));
                }
            }
            
        }catch(SQLException e) {
            throw new PersistenciaException("Error al obtener el producto por id: " + e.getMessage());
        }
        return producto;
    }
    
    
    
    
    
}
