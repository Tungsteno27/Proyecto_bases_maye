/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.BOs;

import negocio.DTOs.PedidoDTO;
import negocio.Exception.NegocioException;
import persistencia.DAOs.IPedidoDAO;
import persistencia.Dominio.EstadoPedido;
import persistencia.Dominio.Pedido;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author julian izaguirre
 */
public class PedidoBO implements IPedidoBO{
    private final IPedidoDAO pedidoDAO;
    
    /**
     * 
     * @param pedidoDAO 
     */
    public PedidoBO(IPedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }
    
    /**
     * 
     * @param pedidoDTO
     * @return
     * @throws NegocioException 
     */
    @Override
    public PedidoDTO registrarPedido(PedidoDTO pedidoDTO) throws NegocioException {
        try {
            // Mapeo de DTO a Dominio
            Pedido pedido = new Pedido();
            pedido.setIdCliente(pedidoDTO.getIdCliente()); 
            pedido.setTotalPagar(pedidoDTO.getTotalPagar());
            if (pedidoDTO.getEstado() != null && !pedidoDTO.getEstado().trim().isEmpty()) { 
                try {
                    pedido.setEstado(EstadoPedido.valueOf(pedidoDTO.getEstado().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    pedido.setEstado(EstadoPedido.PENDIENTE); 
                }
            } else {
                pedido.setEstado(EstadoPedido.PENDIENTE); 
            }

            // aquie lo que hago es que guardo el pedido en la BD 
            // usando el metodo del DAO que tiene el tolano
            int idGenerado = pedidoDAO.insertarPedido(pedido);
            return pedidoDTO;
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error en base de datos al registrar el pedido: " + ex.getMessage());
        }
    }
    
}
