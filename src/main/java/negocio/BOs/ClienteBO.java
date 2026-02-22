/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.BOs;

import java.util.ArrayList;
import java.util.List;
import negocio.DTOs.ClienteDTO;
import negocio.DTOs.TelefonoDTO;
import negocio.Exception.NegocioException;
import persistencia.DAOs.IClienteDAO;
import persistencia.DAOs.IDireccionDAO;
import persistencia.Dominio.Cliente;
import persistencia.Dominio.DireccionCliente;
import persistencia.Dominio.EstatusCliente;
import persistencia.Dominio.TelefonoCliente;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author julian izaguirre
 */
public class ClienteBO implements IClienteBO{
 
    private IClienteDAO clienteDAO;
    private IUsuarioBO usuarioBO;
    private ITelefonoBO telefonoBO;
    private IDireccionDAO direccionDAO;

    public ClienteBO(IClienteDAO clienteDAO, IDireccionDAO direccionDAO, IUsuarioBO usuarioBO, ITelefonoBO telefonoBO) {
        this.clienteDAO = clienteDAO;
        this.direccionDAO = direccionDAO;
        this.usuarioBO = usuarioBO;
        this.telefonoBO = telefonoBO;
    }
    
    @Override
    public ClienteDTO registrarCliente(ClienteDTO dto) throws NegocioException {
        try {
                //Registra el usuario primero
                int idUsuario = usuarioBO.registrarUsuario(dto.getUsuario());

                //Luego si tiene dirección la guarda
                DireccionCliente direccion = null;
                if (dto.getCalle() != null && !dto.getCalle().isBlank()) {
                    direccion = new DireccionCliente();
                    direccion.setCalle(dto.getCalle());
                    direccion.setNumero(Integer.parseInt(dto.getNumero()));
                    direccion.setColonia(dto.getColonia());
                    int idDireccion = direccionDAO.insertarDireccion(direccion);
                    direccion.setIdDireccion(idDireccion);
                }

                //Se crea un cliente ahora que ya se creo el usuario y la dirección
                Cliente cliente = new Cliente();
                cliente.setIdCliente(idUsuario);
                cliente.setNombres(dto.getNombres());
                cliente.setApellidoPaterno(dto.getApellidoPaterno());
                cliente.setApellidoMaterno(dto.getApellidoMaterno());
                cliente.setFechaNacimiento(dto.getFechaNacimiento());
                cliente.setEstatus(EstatusCliente.ACTIVO); //EL cliente empieza siendo activo
                cliente.setDireccion(direccion);
                clienteDAO.insertarCliente(cliente);

                //Este for sirve para recorrer la lista de telefonos del dto, la parte del idUsuario es para enchufar correctamente la llave foránea
                for (TelefonoDTO telDTO : dto.getTelefonos()) {
                    telDTO.setIdCliente(idUsuario);
                    telefonoBO.agregarTelefono(telDTO);
                }

                //Lo mismo que lo de arriba, todos usan el mismo ID cumpliendo con las propiedaded de la especialización y llaves foráneas
                dto.setIdCliente(idUsuario);
                //regresa el objeto DTO
                return dto;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar cliente: " + e.getMessage());
        }
    }
    
    //JULIÁN QUIZÁS QUIERAS VER ESTO TAL VEZ ESTÁ MAL AHORA
    @Override
    public ClienteDTO actualizarInformacion(ClienteDTO clienteDTO) throws NegocioException {
        try {
            // Mapeo directo de DTO a Dominio
            Cliente cliente = new Cliente();
            cliente.setIdCliente(clienteDTO.getIdCliente());
            cliente.setNombres(clienteDTO.getNombres());
            cliente.setApellidoPaterno(clienteDTO.getApellidoPaterno());
            cliente.setApellidoMaterno(clienteDTO.getApellidoMaterno());
            cliente.setFechaNacimiento(clienteDTO.getFechaNacimiento());
            
            cliente.setEstatus(EstatusCliente.ACTIVO); 

            // Mapeo de la dirección
            DireccionCliente direccion = new DireccionCliente();
            direccion.setCalle(clienteDTO.getCalle());
            direccion.setColonia(clienteDTO.getColonia());
            // las otras validaciones despues las pongo, esta es la escencial bruh
            if (clienteDTO.getNumero() != null && !clienteDTO.getNumero().isEmpty()) {
                String numeroLimpio = clienteDTO.getNumero().replaceAll("\\D+", "");
                
                if (!numeroLimpio.isEmpty()) {
                    try {
                        direccion.setNumero(Integer.parseInt(numeroLimpio));
                    } catch (NumberFormatException e) {
                        direccion.setNumero(0); 
                        // Por si es un numero absurdamente grande que supere el limite del int
                    }
                } else {
                    direccion.setNumero(0); 
                }
            }
            cliente.setDireccion(direccion);

            // Mapeo de la lista de teléfonos <---
            if (clienteDTO.getTelefonos() != null && !clienteDTO.getTelefonos().isEmpty()) {
                List<TelefonoCliente> listaTelefonos = new ArrayList<>();
                
                for (TelefonoDTO telDTO : clienteDTO.getTelefonos()) {
                    TelefonoCliente telefono = new TelefonoCliente();
                    telefono.setIdTelefono(telDTO.getIdTelefono()); 
                    telefono.setNumero(telDTO.getNumero());
                    telefono.setEtiqueta(telDTO.getEtiqueta()); 
                    
                    listaTelefonos.add(telefono);
                }
                cliente.setTelefonos(listaTelefonos);
            }

            boolean actualizado = clienteDAO.actualizarCliente(cliente);
            
            if (actualizado) {
                return clienteDTO;
            } else {
                throw new NegocioException("No se encontró el cliente a actualizar en la base de datos.");
            }
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al actualizar la información del cliente: " + ex.getMessage());
        }
    }
}


