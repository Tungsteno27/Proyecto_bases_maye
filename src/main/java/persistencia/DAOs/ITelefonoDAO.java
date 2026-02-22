/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia.DAOs;

import persistencia.Dominio.TelefonoCliente;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author Tungs
 */
public interface ITelefonoDAO {
    public void insertarTelefono(TelefonoCliente telefono) throws PersistenciaException;
    
}
