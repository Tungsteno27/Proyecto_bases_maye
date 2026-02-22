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

    private final IClienteDAO clienteDAO;

    public ClienteBO(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public ClienteDTO registrarCliente(ClienteDTO clienteDTO) throws NegocioException {
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

            // Mapeo de la lista de telefonos
            if (clienteDTO.getTelefonos() != null && !clienteDTO.getTelefonos().isEmpty()) {
                List<TelefonoCliente> listaTelefonos = new ArrayList<>();
                
                for (TelefonoDTO telDTO : clienteDTO.getTelefonos()) {
                    TelefonoCliente telefono = new TelefonoCliente();
                    telefono.setNumero(telDTO.getNumero());
                    telefono.setEtiqueta(telDTO.getEtiqueta()); 
                    
                    listaTelefonos.add(telefono);
                }
                cliente.setTelefonos(listaTelefonos);
            }
            
            int idGenerado = clienteDAO.insertarCliente(cliente);
            clienteDTO.setIdCliente(idGenerado);
            return clienteDTO;
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al registrar el cliente: " + ex.getMessage());
        }
    }

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


