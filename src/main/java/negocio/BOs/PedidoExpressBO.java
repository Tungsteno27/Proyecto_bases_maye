/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.BOs;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import negocio.DTOs.PedidoExpressDTO;
import negocio.DTOs.ProductoCarritoDTO;
import negocio.Exception.NegocioException;
import org.mindrot.jbcrypt.BCrypt;
import persistencia.DAOs.IPedidoDAO;
import persistencia.DAOs.IPedidoExpressDAO;
import persistencia.DAOs.IPedidoProductoDAO;
import persistencia.Dominio.EstadoPedido;
import persistencia.Dominio.Pedido;
import persistencia.Dominio.PedidoExpress;
import persistencia.Dominio.PedidoProducto;
import persistencia.Dominio.Producto;
import persistencia.Exception.PersistenciaException;

/**
 *
 * @author julian izaguirre
 */
public class PedidoExpressBO implements IPedidoExpressBO {
    private IPedidoExpressDAO pedidoExpressDAO;
    private IPedidoDAO pedidoDAO;
    private IPedidoProductoDAO pedidoProductoDAO;
    
    private static final Logger LOG = Logger.getLogger(PedidoProgramadoBO.class.getName());

    
    public PedidoExpressBO(IPedidoExpressDAO pedidoExpressDAO, IPedidoDAO pedidoDAO, IPedidoProductoDAO pedidoProductoDAO) {
        this.pedidoExpressDAO = pedidoExpressDAO;
        this.pedidoDAO = pedidoDAO;
        this.pedidoProductoDAO = pedidoProductoDAO;
    }
    /**
     * Métodl que inserta un pedido express en la BD
     * @param carrito una lista de todos los prodcutos contenidos en el pedido Express
     * @return un DTO de un pedidoExpress
     * @throws NegocioException si ocurre un error al intentar crear un pedido express
     */
    @Override
    public PedidoExpressDTO crearPedidoExpress(List<ProductoCarritoDTO> carrito) throws NegocioException {
        try {
            //El folio se genera al azar con el Random, al igual que el pin
            int folio = generarFolio();
            String pinPlano = generarPin();
            //El pin se encripta con BCrypt, antes usado para encriptar la contraseña
            String pinHash = BCrypt.hashpw(pinPlano, BCrypt.gensalt());
           
            //Crea el pedido base con estado PENDIENTE
            Pedido pedido = new Pedido();
            pedido.setEstado(EstadoPedido.LISTO);
            pedido.setIdCliente(null);
            pedido.setTotalPagar(0.0);
            int idPedido = pedidoDAO.insertarPedido(pedido);

            //Crea el registro express
            PedidoExpress pedidoExpress = new PedidoExpress();
            pedidoExpress.setIdPedidoExpress(idPedido);
            pedidoExpress.setFolio(folio);
            pedidoExpress.setPinSeguridad(pinHash);
            pedidoExpressDAO.insertarPedidoExpress(pedidoExpress);

            //Inserta cada producto del carrito
            for (ProductoCarritoDTO item : carrito) {
                Producto producto = new Producto();
                producto.setIdProducto(item.getProducto().getIdProducto());
                producto.setPrecio(item.getProducto().getPrecio());

                PedidoProducto pp = new PedidoProducto();
                pp.setIdPedido(idPedido);
                pp.setProducto(producto);
                pp.setCantidad(item.getCantidad());
                pp.setPrecioUnitario(item.getProducto().getPrecio());
                pp.setSubTotal(item.getProducto().getPrecio() * item.getCantidad());
                pp.setNotasAdicionales(item.getNotas());
                pedidoProductoDAO.insertarPedidoProducto(pp);
            }
            LOG.info("Actualizando totalPagar del pedido: " + idPedido);
            pedidoDAO.actualizarTotalPagar(idPedido);
            LOG.info("total Actualizado");

            //Regresa el DTO con folio y PIN en texto plano
            PedidoExpressDTO dto = new PedidoExpressDTO();
            dto.setIdPedidoExpress(idPedido);
            dto.setFolio(folio);
            dto.setPin(pinPlano);
            dto.setCarrito(carrito);
            return dto;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al intentar crear el pedido express: " + e.getMessage());
        }
    }
    //Estos métodos no forman parte de la interfaz, solo pueden ser usados dentro de esta clase
    private int generarFolio() {
        Random random = new Random();
        return 10000000 + random.nextInt(90000000);
    }
    
    private String generarPin() {
        Random random = new Random();
        int pin = 10000000 + random.nextInt(90000000);
        return String.valueOf(pin);
    }
}
