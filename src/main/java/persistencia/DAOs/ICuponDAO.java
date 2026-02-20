/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia.DAOs;

import persistencia.Dominio.Cupon;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author Tungs
 */
public interface ICuponDAO {
    
    public Cupon obtenerCuponNombre(String nombre) throws PersistenciaException;
    public boolean validarCupon(int idCupon) throws PersistenciaException;
}
