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
    
    public int insertarDireccion(DireccionCliente direccion) throws PersistenciaException;
    
}
