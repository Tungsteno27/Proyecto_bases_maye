/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.BOs;

import negocio.DTOs.UsuarioDTO;
import negocio.Exception.NegocioException;
import org.mindrot.jbcrypt.BCrypt;
import persistencia.DAOs.IUsuarioDAO;
import persistencia.Dominio.RolUsuario;
import persistencia.Dominio.Usuario;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author Tungs
 */
public class UsuarioBO implements IUsuarioBO {
    
    private final IUsuarioDAO usuarioDAO;
    
    public UsuarioBO( IUsuarioDAO usuarioDAO){
        this.usuarioDAO=usuarioDAO;
        
    }

   public int registrarUsuario(UsuarioDTO dto) throws NegocioException {
        try {
            if (usuarioDAO.existeCorreo(dto.getCorreoElectronico())) {
                throw new NegocioException("El correo electrónico ya está registrado");
            }

            String hash = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());

            Usuario usuario = new Usuario();
            usuario.setCorreoElectronico(dto.getCorreoElectronico());
            usuario.setHashContraseña(hash);
            usuario.setRol(RolUsuario.CLIENTE);

            return usuarioDAO.insertarUsuario(usuario);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar usuario: " + e.getMessage());
        }
    }

    public UsuarioDTO iniciarSesion(String correo, String password) throws NegocioException {
        try {
 
            Usuario usuario = usuarioDAO.obtenerPorCorreo(correo);

            if (usuario == null) {
                throw new NegocioException("El correo electrónico no está registrado");
            }

            if (!BCrypt.checkpw(password, usuario.getHashContraseña())) {
                throw new NegocioException("Contraseña incorrecta");
            }

            UsuarioDTO dto = new UsuarioDTO();
            dto.setIdUsuario(usuario.getIdUsuario());
            dto.setRol(usuario.getRol());
            return dto;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al iniciar sesión: " + e.getMessage());
        }
    }
    
    @Override
    public void actualizarPassword(int idUsuario, String nuevaPassword) throws NegocioException {
        try {
            // Encriptamos la nueva contraseña igual que cuando se registro,
            // tremendo el tolano aquui
            String nuevoHash = BCrypt.hashpw(nuevaPassword, BCrypt.gensalt());
            // la mandamos al DAO para que haga el UPDATE
            usuarioDAO.actualizarPassword(idUsuario, nuevoHash);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar la contraseña: " + e.getMessage());
        }
    }
    
}
