/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia.DAOs;

import java.util.List;
import persistencia.Dominio.PedidoExpress;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author julian izaguirre
 */
public interface IPedidoExpressDAO {
    public void insertarPedidoExpress(PedidoExpress pedidoExpress) throws PersistenciaException;
    public PedidoExpress obtenerPorFolio(int folio) throws PersistenciaException;
    public boolean validarPin(int folio, String pinIngresado) throws PersistenciaException;
    public List<PedidoExpress> obtenerExpressListos() throws PersistenciaException;
    
}
