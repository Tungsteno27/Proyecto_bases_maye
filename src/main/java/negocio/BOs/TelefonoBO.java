/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.BOs;

import java.util.List;
import negocio.DTOs.TelefonoDTO;
import negocio.Exception.NegocioException;
import persistencia.DAOs.ITelefonoDAO;
import persistencia.DAOs.TelefonoDAO;
import persistencia.Dominio.TelefonoCliente;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author Tungs
 */
public class TelefonoBO implements ITelefonoBO {
    
    private final ITelefonoDAO telefonoDAO;
    
    public TelefonoBO(ITelefonoDAO telefonoDAO){
        this.telefonoDAO=telefonoDAO;
    }
    
    public void agregarTelefono(TelefonoDTO dto) throws NegocioException {
        try {
            if (dto.getNumero() == null || dto.getNumero().isBlank()) {
                throw new NegocioException("El número de teléfono es obligatorio");
            }

            if (!dto.getNumero().matches("\\d{10}")) {
                throw new NegocioException("El número debe tener exactamente 10 dígitos");
            }

            TelefonoCliente telefono = new TelefonoCliente();
            telefono.setIdCliente(dto.getIdCliente());
            telefono.setNumero(dto.getNumero());
            telefono.setEtiqueta(dto.getEtiqueta());

            telefonoDAO.insertarTelefono(telefono);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al agregar teléfono: " + e.getMessage());
        }
    }
    
    @Override
    public List<TelefonoDTO> obtenerTelefonosPorIdCliente(int idCliente) throws NegocioException {
        try {
            List<TelefonoCliente> listaDominio = telefonoDAO.buscarPorIdCliente(idCliente);
            List<TelefonoDTO> listaDTO = new java.util.ArrayList<>();
            
            if(listaDominio != null) {
                for(TelefonoCliente tel : listaDominio) {
                    TelefonoDTO dto = new TelefonoDTO();
                    dto.setIdTelefono(tel.getIdTelefono());
                    dto.setNumero(tel.getNumero());
                    dto.setEtiqueta(tel.getEtiqueta());
                    dto.setIdCliente(tel.getIdCliente());
                    listaDTO.add(dto);
                }
            }
            return listaDTO;
            
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener los teléfonos: " + e.getMessage());
        }
    }

    @Override
    public void actualizarTelefono(TelefonoDTO dto) throws NegocioException {
        try {
            if (dto.getNumero() == null || dto.getNumero().isBlank()) {
                throw new NegocioException("El número de teléfono es obligatorio");
            }

            if (!dto.getNumero().matches("\\d{10}")) {
                throw new NegocioException("El número debe tener exactamente 10 dígitos");
            }

            TelefonoCliente telefono = new TelefonoCliente();
            telefono.setIdTelefono(dto.getIdTelefono());
            telefono.setIdCliente(dto.getIdCliente());
            telefono.setNumero(dto.getNumero());
            telefono.setEtiqueta(dto.getEtiqueta());

            telefonoDAO.actualizarTelefono(telefono);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar teléfono: " + e.getMessage());
        }
    }
    
}
