/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.BOs;

import java.util.List;
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
    
    /**
     * Busca la información completa de un cliente usando el ID de su usuario
     * @param idUsuario
     * @return
     * @throws NegocioException 
     */
    ClienteDTO obtenerClientePorIdUsuario(int idUsuario) throws NegocioException;
    
    /**
     * 
     * @return
     * @throws NegocioException 
     */
    public List<ClienteDTO> obtenerTodosLosClientes() throws NegocioException;
    
    /**
     * 
     * @param idCliente
     * @throws NegocioException 
     */
    public void darDeBajaCliente(int idCliente) throws NegocioException;
    
    /**
     * 
     * @param idCliente
     * @param estatus
     * @throws NegocioException 
     */
    public void cambiarEstatus(int idCliente, String estatus) throws NegocioException;
}
