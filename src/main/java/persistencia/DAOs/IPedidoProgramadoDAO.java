/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs;

import persistencia.Dominio.PedidoProgramado;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author Tungs
 */
public interface IPedidoProgramadoDAO {
    
    public void insertarPedidoProgramado(PedidoProgramado pedidoProg) throws PersistenciaException;
}
