/*
 * click nbfsnbhostsystemfilesystemtemplateslicenseslicense-defaulttxt to change this license
 * click nbfsnbhostsystemfilesystemtemplatesclassesinterfacejava to edit this template
 */
package negocio.BOs;

import negocio.DTOs.CuponDTO;
import negocio.Exception.NegocioException;

/**
 * interfaz que define el contrato para la logica de negocio de los cupones
 * establece los metodos obligatorios para buscar y validar los codigos de descuento
 *
 * @author julian izaguirre
 */
public interface ICuponBO {
    
    /**
     * busca un cupon especifico en el sistema utilizando su nombre o codigo de texto
     * * @param nombre la cadena de texto con el codigo promocional a buscar
     * @return un objeto cupondto con los detalles y reglas del descuento
     * @throws NegocioException si el cupon no existe o falla la consulta
     */
    CuponDTO obtenerCuponPorNombre(String nombre) throws NegocioException;
    
    /**
     * verifica si un cupon sigue siendo valido para aplicarse en una compra
     * revisa las restricciones de fechas de vigencia y los limites de uso
     * * @param idCupon el identificador numerico del cupon a validar
     * @return true si el cupon se puede aplicar false si ya expiro o se agoto
     * @throws NegocioException si ocurre un error durante la validacion en la base de datos
     */
    boolean validarCupon(int idCupon) throws NegocioException;
    

}
