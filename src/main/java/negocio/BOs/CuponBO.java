/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.BOs;

import negocio.DTOs.CuponDTO;
import negocio.Exception.NegocioException;
import persistencia.DAOs.ICuponDAO;
import persistencia.Dominio.Cupon;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author julian izaguirre
 */
public class CuponBO implements ICuponBO {
    
    private final ICuponDAO cuponDAO;
    
    /**
     * 
     * @param cuponDAO 
     */
    public CuponBO(ICuponDAO cuponDAO) {
        this.cuponDAO = cuponDAO;
    }
    
    /**
     * 
     * @param nombre
     * @return
     * @throws NegocioException 
     */
    @Override
    public CuponDTO obtenerCuponPorNombre(String nombre) throws NegocioException {
        try {
            Cupon cuponDominio = cuponDAO.obtenerCuponNombre(nombre);
            
            // si el DAO nos devuelve algo lo mapeamos al DTO
            if (cuponDominio != null) {
                CuponDTO cuponDTO = new CuponDTO();
                cuponDTO.setIdCupon(cuponDominio.getIdCupon());
                cuponDTO.setNombre(cuponDominio.getNombre());
                cuponDTO.setUsosTotales(cuponDominio.getUsosTotales());
                cuponDTO.setUsosActuales(cuponDominio.getUsosActuales());
                cuponDTO.setPorcentaje(cuponDominio.getPorcentaje());
                cuponDTO.setFechaInicio(cuponDominio.getFechaInicio());
                cuponDTO.setFechaFin(cuponDominio.getFechaFin());
                
                return cuponDTO;
            } else {
                throw new NegocioException("El cupon ingresado no existe");
            }  
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar el cupon: " + ex.getMessage());
        }
    }
    
    /**
     * 
     * @param idCupon
     * @return
     * @throws NegocioException 
     */
    @Override
    public boolean validarCupon(int idCupon) throws NegocioException {
        try {
            return cuponDAO.validarCupon(idCupon);
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al validar la disponibilidad del cp: " + ex.getMessage());
        }
    }
    
}