/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.BOs;

import negocio.DTOs.ClienteDTO;
import negocio.Exception.NegocioException;

/**
 * interfaz para la logica de negocio de los clientes
 * @author julian izaguirre 
 */
public interface IClienteBO {
    
    /**
     * 
     * @param cliente
     * @return
     * @throws NegocioException 
     */
    ClienteDTO registrarCliente(ClienteDTO cliente) throws NegocioException;
        
    /**
     * 
     * @param cliente
     * @return
     * @throws NegocioException 
     */
    ClienteDTO actualizarInformacion(ClienteDTO cliente) throws NegocioException;
    
}