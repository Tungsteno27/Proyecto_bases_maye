/*
 * click nbfsnbhostsystemfilesystemtemplateslicenseslicense-defaulttxt to change this license
 * click nbfsnbhostsystemfilesystemtemplatesclassesinterfacejava to edit this template
 */
package negocio.BOs;

import java.util.List;
import negocio.DTOs.ClienteDTO;
import negocio.Exception.NegocioException;

/**
 * interfaz para la logica de negocio de los clientes
 * define el contrato que debe seguir la clase de implementacion
 * para gestionar el crud y las operaciones relacionadas con los clientes
 *
 * @author julian izaguirre 
 */
public interface IClienteBO {
    
    /**
     * registra un nuevo cliente validando sus datos y delegando
     * la creacion de su usuario direccion y telefonos
     * @param cliente el objeto con la informacion a registrar
     * @return el cliente registrado con su identificador generado
     * @throws NegocioException si hay un error de validacion o persistencia
     */
    ClienteDTO registrarCliente(ClienteDTO cliente) throws NegocioException;
        
    /**
     * actualiza los datos personales de un cliente ya existente
     * @param cliente el objeto con los datos modificados
     * @return el cliente actualizado despues del guardado
     * @throws NegocioException si no se encuentra o falla la conexion
     */
    ClienteDTO actualizarInformacion(ClienteDTO cliente) throws NegocioException;
    
    /**
     * busca la informacion completa de un cliente usando el id de su usuario
     * reuniendo datos de sus tablas asociadas como direccion y telefonos
     * @param idUsuario el identificador unico del usuario
     * @return un objeto dto con todos los datos cruzados del cliente
     * @throws NegocioException si no se encuentra informacion asociada
     */
    ClienteDTO obtenerClientePorIdUsuario(int idUsuario) throws NegocioException;
    
    /**
     * extrae a todos los clientes almacenados en la base de datos
     * @return una lista de objetos dto con el catalogo de clientes
     * @throws NegocioException si falla la peticion a la base de datos
     */
    public List<ClienteDTO> obtenerTodosLosClientes() throws NegocioException;
    
    /**
     * ejecuta una baja logica cambiando el estado del cliente a inactivo
     * para preservar su historial de pedidos
     * @param idCliente el identificador del cliente a desactivar
     * @throws NegocioException si ocurre un fallo al comunicar con los datos
     */
    public void darDeBajaCliente(int idCliente) throws NegocioException;
    
    /**
     * actualiza el estatus operativo del cliente con un valor de texto especifico
     * @param idCliente el identificador del cliente a modificar
     * @param estatus el nuevo estado a asignar
     * @throws NegocioException si el valor no es valido o falla la persistencia
     */
    public void cambiarEstatus(int idCliente, String estatus) throws NegocioException;
}
