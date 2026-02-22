/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.BOs;

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
}
