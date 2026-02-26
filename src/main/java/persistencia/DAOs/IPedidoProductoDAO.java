/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia.DAOs;

import persistencia.Dominio.PedidoProducto;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author Tungs
 */
public interface IPedidoProductoDAO  {
    
    public void insertarPedidoProducto(PedidoProducto pp) throws PersistenciaException;
}
