/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia.DAOs;

import java.util.List;
import persistencia.Dominio.TelefonoCliente;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author Tungs
 */
public interface ITelefonoDAO {
    /**
     * 
     * @param telefono
     * @throws PersistenciaException 
     */
    public void insertarTelefono(TelefonoCliente telefono) throws PersistenciaException;
    
    /**
     * Busca todos los teléfonos asociados a un cliente
     * @param idCliente
     * @return
     * @throws persistencia.Exception.PersistenciaException 
     */
    public List<TelefonoCliente> buscarPorIdCliente(int idCliente) throws PersistenciaException;

    /**
     * Actualiza la información de un teléfono existente
     * @param telefono
     * @throws PersistenciaException 
     */
    public void actualizarTelefono(TelefonoCliente telefono) throws PersistenciaException;
}
