/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.BOs;

import negocio.DTOs.CuponDTO;
import negocio.Exception.NegocioException;

/**
 *
 * @author julian izaguirre
 */
public interface ICuponBO {
    
    /**
     * busca un cupon en la base de datos a travez por su name
     */
    CuponDTO obtenerCuponPorNombre(String nombre) throws NegocioException;
    
    /**
     * valida si un cupon aun sirve
     */
    boolean validarCupon(int idCupon) throws NegocioException;
    
}