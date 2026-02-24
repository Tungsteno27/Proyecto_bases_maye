/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia.DAOs;

import persistencia.Dominio.DireccionCliente;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author Tungs
 */
public interface IDireccionDAO {
    
    /**
     * 
     * @param direccion
     * @return
     * @throws PersistenciaException 
     */
    public int insertarDireccion(DireccionCliente direccion) throws PersistenciaException;
    
    /**
     * Actualiza una dirección existente en la base de datos
     * @param direccion
     * @throws PersistenciaException 
     */
    public void actualizarDireccion(DireccionCliente direccion) throws PersistenciaException;
}
