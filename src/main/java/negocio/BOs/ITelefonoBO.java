/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.BOs;

import java.util.List;
import negocio.DTOs.TelefonoDTO;
import negocio.Exception.NegocioException;

/**
 *
 * @author Tungs
 */
public interface ITelefonoBO {
    /**
     * 
     * @param dto
     * @throws NegocioException 
     */
    public void agregarTelefono(TelefonoDTO dto) throws NegocioException;

    /**
     * Obtiene la lista de teléfonos de un client
     * @param idCliente
     * @return
     * @throws NegocioException 
     */
    public List<TelefonoDTO> obtenerTelefonosPorIdCliente(int idCliente) throws NegocioException;
    
    /**
     * Actualiza un teléfono existente
     * @param dto
     * @throws NegocioException 
     */
    public void actualizarTelefono(TelefonoDTO dto) throws NegocioException;
    
    
}
