/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia.conexion;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Tungs
 */
public interface IConexionBD {
     /**
     * Crea y retorna una conexión activa con la base de datos.
     *
     * La implementación concreta de este método será responsable de definir los
     * parámetros de conexión necesarios y establecer la comunicación con el
     * motor de base de datos correspondiente.
     *
     * @return una instancia de {@Connection} lista para ser utilizada
     * @throws SQLException si ocurre un error al intentar establecer la conexión
     */
    Connection crearConexion() throws SQLException;
}
