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
    
    public ProductoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public List<Producto> obtenerProductosDisponibles() throws PersistenciaException {
        List<Producto> productos = new ArrayList<>();
        String comandoSQL = "SELECT idProducto, nombre, estado, precio, descripcion, tamanio FROM Productos";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(comandoSQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                
                String estadoBD = rs.getString("estado");
                p.setEstado(estadoBD != null && estadoBD.equalsIgnoreCase("Disponible") ? EstadoProducto.DISPONIBLE : EstadoProducto.NO_DISPONIBLE);
                
                String tamanioBD = rs.getString("tamanio");
                if (tamanioBD != null) {
                    if (tamanioBD.equalsIgnoreCase("Chica")) p.setTamanio(TamanioProducto.CHICA);
                    else if (tamanioBD.equalsIgnoreCase("Mediana")) p.setTamanio(TamanioProducto.MEDIANA);
                    else p.setTamanio(TamanioProducto.GRANDE);
                }
                
                productos.add(p);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener los productos: " + e.getMessage());
        }
        return productos;
    }

    @Override
    public Producto obtenerProductoPorId(int id) throws PersistenciaException {
        Producto producto = null; 
        String comandoSQL = "SELECT idProducto, nombre, estado, precio, descripcion, tamanio FROM Productos WHERE idProducto=?";
        try(Connection conn = conexionBD.crearConexion();
            PreparedStatement ps = conn.prepareStatement(comandoSQL)) {
            
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    producto = new Producto();
                    producto.setIdProducto(rs.getInt("idProducto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setPrecio(rs.getDouble("precio"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    
                    String estadoBD = rs.getString("estado");
                    producto.setEstado(estadoBD != null && estadoBD.equalsIgnoreCase("Disponible") ? EstadoProducto.DISPONIBLE : EstadoProducto.NO_DISPONIBLE);
                    
                    String tamanioBD = rs.getString("tamanio");
                    if (tamanioBD != null) {
                        if (tamanioBD.equalsIgnoreCase("Chica")) producto.setTamanio(TamanioProducto.CHICA);
                        else if (tamanioBD.equalsIgnoreCase("Mediana")) producto.setTamanio(TamanioProducto.MEDIANA);
                        else producto.setTamanio(TamanioProducto.GRANDE);
                    }
                }
            }
            
        } catch(SQLException e) {
            throw new PersistenciaException("Error al obtener el producto por id: " + e.getMessage());
        }
        return producto;
    }
    
    @Override
    public int insertarProducto(Producto producto) throws PersistenciaException {
        String sql = "INSERT INTO Productos (nombre, estado, precio, descripcion, tamanio) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, producto.getNombre());
            
            ps.setString(2, producto.getEstado() == EstadoProducto.DISPONIBLE ? "Disponible" : "No Disponible"); 
            
            ps.setDouble(3, producto.getPrecio());
            ps.setString(4, producto.getDescripcion());
            
            String tamanioStr = "Chica";
            if (producto.getTamanio() == TamanioProducto.MEDIANA) tamanioStr = "Mediana";
            else if (producto.getTamanio() == TamanioProducto.GRANDE) tamanioStr = "Grande";
            ps.setString(5, tamanioStr);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al insertar el producto: " + e.getMessage());
        }
        throw new PersistenciaException("No se pudo obtener el ID del producto insertado");
    }

    @Override
    public void actualizarProducto(Producto producto) throws PersistenciaException {
        String sql = "UPDATE Productos SET nombre = ?, estado = ?, precio = ?, descripcion = ?, tamanio = ? WHERE idProducto = ?";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            
            ps.setString(2, producto.getEstado() == EstadoProducto.DISPONIBLE ? "Disponible" : "No Disponible"); 
            
            ps.setDouble(3, producto.getPrecio());
            ps.setString(4, producto.getDescripcion());
            
            String tamanioStr = "Chica";
            if (producto.getTamanio() == TamanioProducto.MEDIANA) tamanioStr = "Mediana";
            else if (producto.getTamanio() == TamanioProducto.GRANDE) tamanioStr = "Grande";
            ps.setString(5, tamanioStr);
            
            ps.setInt(6, producto.getIdProducto());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizar el producto: " + e.getMessage());
        }
    }

    @Override
    public void eliminarProducto(int idProducto) throws PersistenciaException {
        String sql = "DELETE FROM Productos WHERE idProducto = ?";
        
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException("Error al eliminar el producto: " + e.getMessage());
        }
    }
}
