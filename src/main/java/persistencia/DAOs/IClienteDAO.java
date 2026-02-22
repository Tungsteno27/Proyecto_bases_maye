/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia.DAOs;

import persistencia.Dominio.Cliente;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author julian izaguirre
 */
public interface IClienteDAO {
    
    /**
     * 
     * @param cliente
     * @return
     * @throws PersistenciaException 
     */
    public void insertarCliente(Cliente cliente) throws PersistenciaException;
    
    /**
     * 
     * @param cliente
     * @return
     * @throws PersistenciaException 
     */
    public boolean actualizarCliente(Cliente cliente) throws PersistenciaException;
    
    /**
     * 
     * @param idCliente
     * @return
     * @throws PersistenciaException 
     */
     public Cliente buscarPorId(int idCliente) throws PersistenciaException;
}
