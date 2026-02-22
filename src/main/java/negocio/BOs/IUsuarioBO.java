/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.BOs;

import negocio.DTOs.UsuarioDTO;
import negocio.Exception.NegocioException;


/**
 *
 * @author Tungs
 */
public interface IUsuarioBO {
    public int registrarUsuario(UsuarioDTO dto) throws NegocioException;
     public UsuarioDTO iniciarSesion(String correo, String password) throws NegocioException;
}
