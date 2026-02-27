/*
 * click nbfsnbhostsystemfilesystemtemplateslicenseslicense-defaulttxt to change this license
 * click nbfsnbhostsystemfilesystemtemplatesclassesclassjava to edit this template
 */
package negocio.BOs;

import negocio.DTOs.CuponDTO;
import negocio.Exception.NegocioException;
import persistencia.DAOs.ICuponDAO;
import persistencia.Dominio.Cupon;
import persistencia.Exception.PersistenciaException;

/**
 * clase que implementa la logica de negocio para los cupones de descuento
 * se encarga de verificar la existencia y validez de los codigos promocionales
 * comunicandose con la capa de acceso a datos
 *
 * @author julian izaguirre
 */
public class CuponBO implements ICuponBO {
    
    private final ICuponDAO cuponDAO;
    
    /**
     * constructor de la clase cuponbo
     * recibe y asigna la interfaz del dao para aplicar la inyeccion de dependencias
     * * @param cuponDAO instancia para acceder a la tabla de cupones
     */
    public CuponBO(ICuponDAO cuponDAO) {
        this.cuponDAO = cuponDAO;
    }
    
    /**
     * busca un cupon especifico utilizando su codigo promocional de texto
     * convierte la entidad de dominio devuelta en un objeto dto para la vista
     * * @param nombre la cadena de texto con el codigo del cupon
     * @return un objeto cupondto con toda la informacion del descuento
     * @throws NegocioException si el cupon no se encuentra o falla la conexion
     */
    @Override
    public CuponDTO obtenerCuponPorNombre(String nombre) throws NegocioException {
        try {
            Cupon cuponDominio = cuponDAO.obtenerCuponNombre(nombre);
            
            // si el dao nos devuelve la entidad procedemos a mapearla al dto
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
                throw new NegocioException("el cupon ingresado no existe");
            }  
        } catch (PersistenciaException ex) {
            throw new NegocioException("error al buscar el cupon: " + ex.getMessage());
        }
    }
    
    /**
     * evalua si un cupon es aplicable para una compra
     * valida las restricciones de fechas de inicio y fin asi como el limite de usos
     * * @param idCupon el identificador numerico del cupon en la base de datos
     * @return true si el cupon es valido y se puede aplicar false si ya expiro
     * @throws NegocioException si ocurre un error al consultar la disponibilidad
     */
    @Override
    public boolean validarCupon(int idCupon) throws NegocioException {
        try {
            // delega la validacion de fechas y usos maximos a la capa de persistencia
            return cuponDAO.validarCupon(idCupon);
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("error al validar la disponibilidad del cupon: " + ex.getMessage());
        }
    }
    

}
