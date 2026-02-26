/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 *
 * @author julian izaguirre
 */
public class ClienteBO implements IClienteBO{
 
    private IClienteDAO clienteDAO;
    private IUsuarioBO usuarioBO;
    private ITelefonoBO telefonoBO;
    private IDireccionDAO direccionDAO;

    public ClienteBO(IClienteDAO clienteDAO, IDireccionDAO direccionDAO, IUsuarioBO usuarioBO, ITelefonoBO telefonoBO) {
        this.clienteDAO = clienteDAO;
        this.direccionDAO = direccionDAO;
        this.usuarioBO = usuarioBO;
        this.telefonoBO = telefonoBO;
    }
    
    @Override
    public ClienteDTO registrarCliente(ClienteDTO dto) throws NegocioException {
        try {
                //Registra el usuario primero
                int idUsuario = usuarioBO.registrarUsuario(dto.getUsuario());

                //Luego si tiene dirección la guarda
                DireccionCliente direccion = null;
                if (dto.getCalle() != null && !dto.getCalle().isBlank()) {
                    direccion = new DireccionCliente();
                    direccion.setCalle(dto.getCalle());
                    direccion.setNumero(Integer.parseInt(dto.getNumero()));
                    direccion.setColonia(dto.getColonia());
                    int idDireccion = direccionDAO.insertarDireccion(direccion);
                    direccion.setIdDireccion(idDireccion);
                }

                //Se crea un cliente ahora que ya se creo el usuario y la dirección
                Cliente cliente = new Cliente();
                cliente.setIdCliente(idUsuario);
                cliente.setNombres(dto.getNombres());
                cliente.setApellidoPaterno(dto.getApellidoPaterno());
                cliente.setApellidoMaterno(dto.getApellidoMaterno());
                cliente.setFechaNacimiento(dto.getFechaNacimiento());
                cliente.setEstatus(EstatusCliente.ACTIVO); //EL cliente empieza siendo activo
                cliente.setDireccion(direccion);
                clienteDAO.insertarCliente(cliente);

                //Este for sirve para recorrer la lista de telefonos del dto, la parte del idUsuario es para enchufar correctamente la llave foránea
                for (TelefonoDTO telDTO : dto.getTelefonos()) {
                    telDTO.setIdCliente(idUsuario);
                    telefonoBO.agregarTelefono(telDTO);
                }

                //Lo mismo que lo de arriba, todos usan el mismo ID cumpliendo con las propiedaded de la especialización y llaves foráneas
                dto.setIdCliente(idUsuario);
                //regresa el objeto DTO
                return dto;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar cliente: " + e.getMessage());
        }
    }
    
    //JULIÁN QUIZÁS QUIERAS VER ESTO TAL VEZ ESTÁ MAL AHORA
    // segun yo ya lo arregle con este cambio del down
    @Override
    public ClienteDTO actualizarInformacion(ClienteDTO clienteDTO) throws NegocioException {
        try {
            Cliente clienteOriginal = clienteDAO.buscarPorId(clienteDTO.getIdCliente());
            if (clienteOriginal == null) {
                throw new NegocioException("No se encontro el cliente a actualizar.");
            }

            // Mapeo de DTO a Dominio 
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
            
            if (clienteOriginal.getDireccion() != null) {
                direccion.setIdDireccion(clienteOriginal.getDireccion().getIdDireccion());
                direccionDAO.actualizarDireccion(direccion); 
            } else {
                int nuevoIdDir = direccionDAO.insertarDireccion(direccion);
                direccion.setIdDireccion(nuevoIdDir);
            }
            cliente.setDireccion(direccion);

            boolean actualizado = clienteDAO.actualizarCliente(cliente);
            
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
            
            if (clienteDTO.getUsuario() != null && clienteDTO.getUsuario().getPassword() != null && !clienteDTO.getUsuario().getPassword().isEmpty()) {
                usuarioBO.actualizarPassword(clienteDTO.getUsuario().getIdUsuario(), clienteDTO.getUsuario().getPassword());
            }
            
            if (actualizado) {
                return clienteDTO;
            } else {
                throw new NegocioException("No se encontro el cliente a actualizar en la base de datos");
            }
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al actualizar la información del cliente: " + ex.getMessage());
        }
    }
    
    @Override
    public ClienteDTO obtenerClientePorIdUsuario(int idUsuario) throws NegocioException {
        try {
            Cliente cliente = clienteDAO.buscarPorId(idUsuario);
            
            if (cliente == null) {
                throw new NegocioException("No se encontró la información del perfil.");
            }
            
            ClienteDTO dto = new ClienteDTO();
            dto.setIdCliente(cliente.getIdCliente());
            dto.setNombres(cliente.getNombres());
            dto.setApellidoPaterno(cliente.getApellidoPaterno());
            dto.setApellidoMaterno(cliente.getApellidoMaterno());
            dto.setFechaNacimiento(cliente.getFechaNacimiento());
            
            if (cliente.getDireccion() != null) {
                dto.setCalle(cliente.getDireccion().getCalle());
                dto.setNumero(String.valueOf(cliente.getDireccion().getNumero()));
                dto.setColonia(cliente.getDireccion().getColonia());
            }

            dto.setTelefonos(telefonoBO.obtenerTelefonosPorIdCliente(idUsuario));
            
            return dto;
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al cargar el perfil: " + ex.getMessage());
        }
    }
    
    /**
     * 
     * @return
     * @throws NegocioException 
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
            throw new NegocioException("Error al consultar la lista de clientes: " + ex.getMessage());
        }
    }
    
    /**
     * 
     * @param idCliente
     * @throws NegocioException 
     */
    @Override
    public void darDeBajaCliente(int idCliente) throws NegocioException {
        try {
            clienteDAO.darDeBajaCliente(idCliente);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al dar de baja al cliente en el sistema: " + ex.getMessage());
        }
    }
    
    /**
     * 
     * @param idCliente
     * @param estatus
     * @throws NegocioException 
     */
    @Override
    public void cambiarEstatus(int idCliente, String estatus) throws NegocioException {
        try {
            clienteDAO.cambiarEstatus(idCliente, persistencia.Dominio.EstatusCliente.valueOf(estatus.toUpperCase()));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al actualizar el estatus: " + ex.getMessage());
        }
    }
}


