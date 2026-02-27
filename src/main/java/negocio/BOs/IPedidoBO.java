/*
 * click nbfsnbhostsystemfilesystemtemplateslicenseslicense-defaulttxt to change this license
 * click nbfsnbhostsystemfilesystemtemplatesclassesinterfacejava to edit this template
 */
package negocio.BOs;

import java.time.LocalDateTime;
import java.util.List;
import negocio.DTOs.PedidoDTO;
import negocio.Exception.NegocioException;

/**
 * define las operaciones de negocio relacionadas con la gestion general
 * de los pedidos de la pizzeria estableciendo el contrato para el crud
 * y el ciclo de vida de los mismos
 * 
 * @author julian izaguirre
 */
public interface IPedidoBO {

    /**
     * registra un nuevo pedido en el sistema insertando sus datos principales
     * @param pedido el objeto de transferencia con los detalles de la venta
     * @return el pedido registrado con su identificador generado
     * @throws NegocioException si ocurre un error durante el registro
     */
    PedidoDTO registrarPedido(PedidoDTO pedido) throws NegocioException;

    /**
     * recupera todos los pedidos activos en estado pendiente preparacion
     * o listo ordenados por antiguedad para la cola de trabajo del empleado
     * @return lista de pedidos que aun no se han finalizado
     * @throws NegocioException si falla la consulta a la base de datos
     */
    List<PedidoDTO> obtenerPedidosActivos() throws NegocioException;

    /**
     * modifica el estado de un pedido a listo validando previamente
     * que se encuentre en una etapa valida como en preparacion o pendiente
     * @param idPedido identificador unico del pedido a modificar
     * @throws NegocioException si el pedido no esta en un estado valido o falla la actualizacion
     */
    void marcarComoListo(int idPedido) throws NegocioException;

    /**
     * registra la entrega de un pedido evaluando las reglas de seguridad
     * correspondientes a su tipo validando folios y pines si es express
     * @param idPedido identificador principal del pedido
     * @param folioIngresado folio de verificacion proporcionado por el cliente
     * @param pinIngresado pin de seguridad obligatorio para pedidos express
     * @throws NegocioException si los datos de seguridad son invalidos o ocurre un fallo de persistencia
     */
    void marcarComoEntregado(int idPedido, Integer folioIngresado, String pinIngresado)
            throws NegocioException;

    /**
     * detiene y cancela un pedido bloqueando la operacion si el pedido
     * ya ha sido preparado o si ya se encuentra listo para entrega
     * @param idPedido identificador del pedido a cancelar
     * @throws NegocioException si el pedido ya avanzo de estado o hay error interno
     */
    void cancelarPedido(int idPedido) throws NegocioException;

    /**
     * realiza una busqueda relacional para encontrar ordenes filtradas
     * por el numero de telefono de contacto del cliente
     * @param telefono cadena de digitos para rastrear compras vinculadas
     * @return una lista de pedidos coincidentes con el criterio
     * @throws NegocioException si hay error de sintaxis o de conexion
     */
    List<PedidoDTO> buscarPorTelefono(String telefono) throws NegocioException;

    /**
     * extrae un bloque de pedidos generados especificamente dentro de un
     * marco de tiempo para la generacion de reportes administrativos
     * @param inicio limite de fecha inferior para el reporte
     * @param fin limite de fecha superior para el reporte
     * @return listado de ordenes que entran en la ventana de tiempo
     * @throws NegocioException si las fechas son invalidas o falla el acceso a datos
     */
    List<PedidoDTO> buscarPorRangoDeFechas(LocalDateTime inicio, LocalDateTime fin)
            throws NegocioException;

    /**
     * devuelve el historial completo de compras programadas y finalizadas
     * realizadas historicamente por un cliente especifico
     * @param idCliente identificador del cliente logueado
     * @return la lista de pedidos de su historial personal
     * @throws NegocioException si no se puede cargar la informacion de la cuenta
     */
    List<PedidoDTO> consultarPedidosProgramadosPorCliente(Integer idCliente)
            throws NegocioException;
}
