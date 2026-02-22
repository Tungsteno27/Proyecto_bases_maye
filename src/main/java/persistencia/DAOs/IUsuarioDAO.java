/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia.DAOs;

import persistencia.Dominio.Usuario;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author Tungs
 */
public interface IUsuarioDAO {
    public int insertarUsuario(Usuario usuario) throws PersistenciaException;
    public Usuario obtenerPorCorreo(String correo) throws PersistenciaException;
    public boolean existeCorreo(String correo) throws PersistenciaException;
    
}
