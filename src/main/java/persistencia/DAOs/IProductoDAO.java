/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia.DAOs;

import java.util.List;
import persistencia.Dominio.Producto;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author Tungs
 */
public interface IProductoDAO {
    
    public List<Producto> obtenerProductosDisponibles() throws PersistenciaException;
    public Producto obtenerProductoPorId(int id) throws PersistenciaException;
    
   
    
}
