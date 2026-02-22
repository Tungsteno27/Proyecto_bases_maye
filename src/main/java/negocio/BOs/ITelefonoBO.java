/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.BOs;

import negocio.DTOs.TelefonoDTO;
import negocio.Exception.NegocioException;

/**
 *
 * @author Tungs
 */
public interface ITelefonoBO {
    public void agregarTelefono(TelefonoDTO dto) throws NegocioException;
}
