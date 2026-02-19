/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Tungs
 */
public class ConexionBD implements IConexionBD {
    /**
     * Cadena de conexión utilizada para establecer comunicación con la base de
     * datos.
     */
    private final String CADENA_CONEXION = "jdbc:mysql://localhost:3306/mayepizzas";

    /**
     * Usuario de la base de datos.
     */
    private final String USUARIO = "root";

    /**
     * Contraseña asociada al usuario de la base de datos.
     */
    private final String CONTRASENIA = "ValarMorghulis27";

    @Override
    public Connection crearConexion() throws SQLException {
        return DriverManager.getConnection(CADENA_CONEXION, USUARIO, CONTRASENIA);
    }
}
