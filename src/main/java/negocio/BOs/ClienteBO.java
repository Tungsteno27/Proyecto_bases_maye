/*
 * click nbfsnbhostsystemfilesystemtemplateslicenseslicense-defaulttxt to change this license
 * click nbfsnbhostsystemfilesystemtemplatesclassesclassjava to edit this template
 */
package negocio.BOs;

import java.util.ArrayList;
import java.util.List;
import negocio.DTOs.ClienteDTO;
import negocio.DTOs.TelefonoDTO;
import negocio.Exception.NegocioException;
import persistencia.DAOs.IClienteDAO;
import persistencia.DAOs.IDireccionDAO;
import persistencia.Dominio.Cliente;
import persistencia.Dominio.DireccionCliente;
import persistencia.Dominio.EstatusCliente;
import persistencia.Dominio.TelefonoCliente;
import persistencia.Exception.PersistenciaException;

/**
 * clase que implementa la logica de negocio para los clientes
 * se encarga de procesar las reglas y validaciones antes de enviar
 * o solicitar informacion a la capa de persistencia mediante los daos
 * actua como intermediario entre la vista y la base de datos
 *
 * @author julian izaguirre
 */
public class ClienteBO implements IClienteBO{
 
    private IClienteDAO clienteDAO;
    private IUsuarioBO usuarioBO;
    private ITelefonoBO telefonoBO;
    private IDireccionDAO direccionDAO;

    /**
     * constructor de la clase clientebo
     * inicializa los componentes necesarios para interactuar con las tablas
     * relacionadas al cliente como direccion telefono y usuario
     * * @param clienteDAO objeto para acceder a datos de clientes
     * @param direccionDAO objeto para acceder a datos de direcciones
     * @param usuarioBO objeto para logica de usuarios
     * @param telefonoBO objeto para logica de telefonos
     */
    public ClienteBO(IClienteDAO clienteDAO, IDireccionDAO direccionDAO, IUsuarioBO usuarioBO, ITelefonoBO telefonoBO) {
        this.clienteDAO = clienteDAO;
        this.direccionDAO = direccionDAO;
        this.usuarioBO = usuarioBO;
        this.telefonoBO = telefonoBO;
    }
    
    /**
     * registra un nuevo cliente en el sistema coordinando la creacion
     * de su usuario direccion datos personales y telefonos en orden
     * * @param dto el objeto con los datos capturados en pantalla
     * @return el mismo dto actualizado con el id generado
     * @throws NegocioException si ocurre un error en la capa de persistencia
     */
    @Override
    public ClienteDTO registrarCliente(ClienteDTO dto) throws NegocioException {
        try {
                // registra el usuario primero para obtener el id principal
                int idUsuario = usuarioBO.registrarUsuario(dto.getUsuario());

                // verifica si existen datos de direccion y los guarda
                DireccionCliente direccion = null;
                if (dto.getCalle() != null && !dto.getCalle().isBlank()) {
                    direccion = new DireccionCliente();
                    direccion.setCalle(dto.getCalle());
                    direccion.setNumero(Integer.parseInt(dto.getNumero()));
                    direccion.setColonia(dto.getColonia());
                    int idDireccion = direccionDAO.insertarDireccion(direccion);
                    direccion.setIdDireccion(idDireccion);
                }

                // instancia un objeto de dominio cliente con las llaves relacionadas
                Cliente cliente = new Cliente();
                cliente.setIdCliente(idUsuario);
                cliente.setNombres(dto.getNombres());
                cliente.setApellidoPaterno(dto.getApellidoPaterno());
                cliente.setApellidoMaterno(dto.getApellidoMaterno());
                cliente.setFechaNacimiento(dto.getFechaNacimiento());
                cliente.setEstatus(EstatusCliente.ACTIVO); // por defecto todo registro nuevo es activo
                cliente.setDireccion(direccion);
                clienteDAO.insertarCliente(cliente);

                // vincula cada telefono de la lista con el id del cliente recien creado
                for (TelefonoDTO telDTO : dto.getTelefonos()) {
                    telDTO.setIdCliente(idUsuario);
                    telefonoBO.agregarTelefono(telDTO);
                }

                // actualiza el dto original con el id final para retornarlo
                dto.setIdCliente(idUsuario);
                return dto;

        } catch (PersistenciaException e) {
            throw new NegocioException("error al registrar cliente: " + e.getMessage());
        }
    }
    
    /**
     * actualiza la informacion personal de un cliente existente
     * maneja la limpieza de cadenas numericas para la direccion y delega
     * la actualizacion de contrasenas o telefonos a sus respectivos bo
     * * @param clienteDTO los nuevos datos que reemplazaran a los actuales
     * @return el dto modificado si la operacion fue exitosa
     * @throws NegocioException si el cliente no existe o hay error de guardado
     */
    @Override
    public ClienteDTO actualizarInformacion(ClienteDTO clienteDTO) throws NegocioException {
        try {
            Cliente clienteOriginal = clienteDAO.buscarPorId(clienteDTO.getIdCliente());
            if (clienteOriginal == null) {
                throw new NegocioException("no se encontro el cliente a actualizar");
            }

            // conversion de datos del dto hacia la entidad de dominio
            Cliente cliente = new Cliente();
            cliente.setIdCliente(clienteDTO.getIdCliente());
            cliente.setNombres(clienteDTO.getNombres());
            cliente.setApellidoPaterno(clienteDTO.getApellidoPaterno());
            cliente.setApellidoMaterno(clienteDTO.getApellidoMaterno());
            cliente.setFechaNacimiento(clienteDTO.getFechaNacimiento());
            cliente.setEstatus(EstatusCliente.ACTIVO); 
            DireccionCliente direccion = new DireccionCliente();
            direccion.setCalle(clienteDTO.getCalle());
            direccion.setColonia(clienteDTO.getColonia());
            
            // limpieza preventiva para asegurar que el numero de casa solo contenga digitos
            if (clienteDTO.getNumero() != null && !clienteDTO.getNumero().isEmpty()) {
                String numeroLimpio = clienteDTO.getNumero().replaceAll("\\D+", "");
                if (!numeroLimpio.isEmpty()) {
                    try {
                        direccion.setNumero(Integer.parseInt(numeroLimpio));
                    } catch (NumberFormatException e) {
                        direccion.setNumero(0); 
                    }
                } else {
                    direccion.setNumero(0); 
                }
            }
            
            // verifica si es actualizacion de direccion existente o creacion de una nueva
            if (clienteOriginal.getDireccion() != null) {
                direccion.setIdDireccion(clienteOriginal.getDireccion().getIdDireccion());
                direccionDAO.actualizarDireccion(direccion); 
            } else {
                int nuevoIdDir = direccionDAO.insertarDireccion(direccion);
                direccion.setIdDireccion(nuevoIdDir);
            }
            cliente.setDireccion(direccion);

            boolean actualizado = clienteDAO.actualizarCliente(cliente);
            
            // gestiona la edicion o agregacion de multiples telefonos
            if (clienteDTO.getTelefonos() != null) {
                for (TelefonoDTO telDTO : clienteDTO.getTelefonos()) {
                    if (telDTO.getIdTelefono() != 0) {
                        telefonoBO.actualizarTelefono(telDTO);
                    } else {
                        telDTO.setIdCliente(cliente.getIdCliente());
                        telefonoBO.agregarTelefono(telDTO); 
                    }
                }
            }
            
            // delega el cambio de seguridad si se envio una nueva contrasena
            if (clienteDTO.getUsuario() != null && clienteDTO.getUsuario().getPassword() != null && !clienteDTO.getUsuario().getPassword().isEmpty()) {
                usuarioBO.actualizarPassword(clienteDTO.getUsuario().getIdUsuario(), clienteDTO.getUsuario().getPassword());
            }
            
            if (actualizado) {
                return clienteDTO;
            } else {
                throw new NegocioException("no se encontro el cliente a actualizar en la base de datos");
            }
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("error al actualizar la informacion del cliente: " + ex.getMessage());
        }
    }
    
    /**
     * recupera todos los datos de un cliente basado en su identificador
     * empaqueta la entidad junto con su direccion y lista de telefonos en un dto
     * * @param idUsuario el identificador unico del cliente a buscar
     * @return un clientedto con toda la informacion cruzada
     * @throws NegocioException si ocurre un error de consulta
     */
    @Override
    public ClienteDTO obtenerClientePorIdUsuario(int idUsuario) throws NegocioException {
        try {
            Cliente cliente = clienteDAO.buscarPorId(idUsuario);
            
            if (cliente == null) {
                throw new NegocioException("no se encontro la informacion del perfil");
            }
            
            ClienteDTO dto = new ClienteDTO();
            dto.setIdCliente(cliente.getIdCliente());
            dto.setNombres(cliente.getNombres());
            dto.setApellidoPaterno(cliente.getApellidoPaterno());
            dto.setApellidoMaterno(cliente.getApellidoMaterno());
            dto.setFechaNacimiento(cliente.getFechaNacimiento());
            
            // extrae los valores de la direccion si el objeto no es nulo
            if (cliente.getDireccion() != null) {
                dto.setCalle(cliente.getDireccion().getCalle());
                dto.setNumero(String.valueOf(cliente.getDireccion().getNumero()));
                dto.setColonia(cliente.getDireccion().getColonia());
            }

            dto.setTelefonos(telefonoBO.obtenerTelefonosPorIdCliente(idUsuario));
            
            return dto;
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("error al cargar el perfil: " + ex.getMessage());
        }
    }
    
    /**
     * consulta la base de datos para recuperar todos los clientes registrados
     * convierte cada entidad de dominio a un objeto dto para la capa visual
     * extrayendo unicamente el primer telefono como dato de contacto principal
     * * @return lista de clientes empaquetados en objetos dto
     * @throws NegocioException si falla la peticion a la base de datos
     */
    @Override
    public List<ClienteDTO> obtenerTodosLosClientes() throws NegocioException {
        try {
            List<Cliente> listaDominio = clienteDAO.obtenerTodosLosClientes();
            List<ClienteDTO> listaDTO = new ArrayList<>();
            
            for (Cliente c : listaDominio) {
                ClienteDTO dto = new ClienteDTO();
                dto.setIdCliente(c.getIdCliente());
                dto.setNombres(c.getNombres());
                dto.setApellidoPaterno(c.getApellidoPaterno());
                dto.setApellidoMaterno(c.getApellidoMaterno());
                
                if (c.getEstatus() != null) {
                    dto.setEstatus(c.getEstatus().name());
                }
                
                // saca solo el telefono principal para mostrarlo en tablas resumen
                if (c.getTelefonos() != null && !c.getTelefonos().isEmpty()) {
                    List<TelefonoDTO> telefonosDTO = new ArrayList<>();
                    TelefonoDTO telDTO = new TelefonoDTO();
                    telDTO.setNumero(c.getTelefonos().get(0).getNumero());
                    telefonosDTO.add(telDTO);
                    dto.setTelefonos(telefonosDTO);
                }
                
                listaDTO.add(dto);
            }
            return listaDTO;
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("error al consultar la lista de clientes: " + ex.getMessage());
        }
    }
    
    /**
     * ejecuta una baja logica sobre un cliente en especifico
     * * @param idCliente identificador del cliente a desactivar
     * @throws NegocioException si falla la comunicacion con el dao
     */
    @Override
    public void darDeBajaCliente(int idCliente) throws NegocioException {
        try {
            clienteDAO.darDeBajaCliente(idCliente);
        } catch (PersistenciaException ex) {
            throw new NegocioException("error al dar de baja al cliente en el sistema: " + ex.getMessage());
        }
    }
    
    /**
     * modifica el estatus operativo de un cliente determinado
     * traduciendo la cadena de texto a un valor del enumerador correspondiente
     * * @param idCliente identificador del cliente a modificar
     * @param estatus texto que representa el nuevo estado activo o inactivo
     * @throws NegocioException si el valor no es valido o falla la persistencia
     */
    @Override
    public void cambiarEstatus(int idCliente, String estatus) throws NegocioException {
        try {
            clienteDAO.cambiarEstatus(idCliente, persistencia.Dominio.EstatusCliente.valueOf(estatus.toUpperCase()));
        } catch (PersistenciaException ex) {
            throw new NegocioException("error al actualizar el estatus: " + ex.getMessage());
        }
    }
}
