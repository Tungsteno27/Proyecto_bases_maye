/*
 * click nbfsnbhostsystemfilesystemtemplateslicenseslicense-defaulttxt to change this license
 * click nbfsnbhostsystemfilesystemtemplatesclassesclassjava to edit this template
 */
package negocio.BOs;

import java.util.List;
import negocio.DTOs.PedidoExpressDTO;
import negocio.DTOs.ProductoCarritoDTO;
import negocio.Exception.NegocioException;

/**
 * interfaz que define el contrato de operaciones exclusivas
 * para el manejo logico de los pedidos de tipo express
 *
 * @author julian izaguirre
 */
public interface IPedidoExpressBO {
    
    /**
     * procesa y registra un nuevo pedido express en el sistema
     * genera automaticamente el pin de seguridad y el folio de recoleccion
     * a partir de los productos seleccionados en el menu rapido
     * * @param carrito la lista de productos y cantidades seleccionadas
     * @return el objeto dto con los datos del pedido y credenciales de recoleccion
     * @throws NegocioException si ocurre un error de validacion o persistencia
     */
    public PedidoExpressDTO crearPedidoExpress(List<ProductoCarritoDTO> carrito) throws NegocioException;   
}
