/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import persistencia.Dominio.EstadoPedido;
import persistencia.Dominio.Pedido;
import persistencia.Exception.PersistenciaException;
import persistencia.conexion.IConexionBD;

/**
 *
 * @author Tungs
 */
public class PedidoDAO implements IPedidoDAO {

    private final IConexionBD conexionBD;
    private static final Logger LOG = Logger.getLogger(ProductoDAO.class.getName());

    public PedidoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    // =========================================================================
    // MÉTODOS EXISTENTES - NO MODIFICAR
    // =========================================================================

    @Override
    public int insertarPedido(Pedido pedido) throws PersistenciaException {
        String comandoSQL = "INSERT INTO Pedidos (estado, idCliente, totalPagar) VALUES (?, ?, ?)";
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(comandoSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pedido.getEstado().toString());
            if (pedido.getIdCliente() == null) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, pedido.getIdCliente());
            }
            ps.setDouble(3, pedido.getTotalPagar());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al insertar pedido: " + e.getMessage());
        }
        throw new PersistenciaException("No se pudo obtener el ID del pedido a insertar");
    }

    @Override
    public Pedido obtenerPedidoPorId(int idPedido) throws PersistenciaException {
        Pedido pedido = null;
        String sql = "SELECT idPedido, fechaCreacion, estado, idCliente, totalPagar "
                + "FROM Pedidos WHERE idPedido = ?";
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pedido = mapearPedido(rs);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener pedido: " + e.getMessage());
        }
        return pedido;
    }

    @Override
    public void cambiarEstado(int idPedido, EstadoPedido estado) throws PersistenciaException {
        String sql = "UPDATE Pedidos SET estado = ? WHERE idPedido = ?";
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mapearEstadoABD(estado));
            ps.setInt(2, idPedido);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al cambiar estado del pedido: " + e.getMessage());
        }
    }

    @Override
    public int contarPedidosActivos(int idCliente) throws PersistenciaException {
        String sql = "SELECT COUNT(*) FROM Pedidos "
                + "WHERE idCliente = ? "
                + "AND estado IN ('Pendiente', 'En Preparación', 'Listo')";
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al contar pedidos activos: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public void actualizarTotalPagar(int idPedido) throws PersistenciaException {
        String sql = "UPDATE Pedidos SET totalPagar = calcularTotalPedido(?) WHERE idPedido = ?";
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            ps.setInt(2, idPedido);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizar total del pedido: " + e.getMessage());
        }
    }

    // =========================================================================
    // MÉTODOS DE MAPEO
    // =========================================================================

    /**
     * Convierte el String que viene de la BD al enum de Java.
     * Necesario porque la BD usa 'Pendiente', 'En Preparación', 'No Reclamado'
     * con espacios y acentos que no son compatibles directamente con valueOf().
     */
    private EstadoPedido mapearEstadoDeBD(String estadoDB) throws PersistenciaException {
        if (estadoDB == null) {
            throw new PersistenciaException("El estado del pedido en la BD es null");
        }
        switch (estadoDB) {
            case "Pendiente":       return EstadoPedido.PENDIENTE;
            case "En Preparación":  return EstadoPedido.EN_PREPARACION;
            case "Listo":           return EstadoPedido.LISTO;
            case "Entregado":       return EstadoPedido.ENTREGADO;
            case "Cancelado":       return EstadoPedido.CANCELADO;
            case "No Reclamado":    return EstadoPedido.NO_RECLAMADO;
            default:
                throw new PersistenciaException("Estado desconocido en BD: " + estadoDB);
        }
    }

    /**
     * Convierte el enum de Java al String exacto que espera la BD.
     */
    private String mapearEstadoABD(EstadoPedido estado) throws PersistenciaException {
        if (estado == null) {
            throw new PersistenciaException("El estado del pedido no puede ser null");
        }
        switch (estado) {
            case PENDIENTE:       return "Pendiente";
            case EN_PREPARACION:  return "En Preparación";
            case LISTO:           return "Listo";
            case ENTREGADO:       return "Entregado";
            case CANCELADO:       return "Cancelado";
            case NO_RECLAMADO:    return "No Reclamado";
            default:
                throw new PersistenciaException("Estado no soportado: " + estado.name());
        }
    }

    /**
     * Construye un Pedido desde un ResultSet.
     * El ResultSet debe incluir: idPedido, fechaCreacion, estado, idCliente, totalPagar.
     */
    private Pedido mapearPedido(ResultSet rs) throws SQLException, PersistenciaException {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(rs.getInt("idPedido"));
        pedido.setFechaCreacion(rs.getTimestamp("fechaCreacion").toLocalDateTime());
        pedido.setEstado(mapearEstadoDeBD(rs.getString("estado")));
        pedido.setTotalPagar(rs.getDouble("totalPagar"));
        if (rs.getObject("idCliente") != null) {
            pedido.setIdCliente(rs.getInt("idCliente"));
        } else {
            pedido.setIdCliente(null);
        }
        return pedido;
    }

    // =========================================================================
    // MÉTODOS NUEVOS - GESTIÓN DE ENTREGAS
    // =========================================================================

    @Override
    public List<Pedido> obtenerPedidosActivos() throws PersistenciaException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT idPedido, fechaCreacion, estado, idCliente, totalPagar "
                + "FROM Pedidos "
                + "WHERE estado IN ('Pendiente', 'En Preparación', 'Listo') "
                + "ORDER BY fechaCreacion ASC";
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pedidos.add(mapearPedido(rs));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener pedidos activos: " + e.getMessage());
        }
        return pedidos;
    }

    @Override
    public List<Pedido> buscarPorTelefono(String telefono) throws PersistenciaException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT p.idPedido, p.fechaCreacion, p.estado, p.idCliente, p.totalPagar "
                + "FROM Pedidos p "
                + "INNER JOIN PedidoProgramados pp ON p.idPedido = pp.idPedidoProgramado "
                + "INNER JOIN TelefonosClientes tc ON p.idCliente = tc.idCliente "
                + "WHERE tc.numero = ? "
                + "ORDER BY p.fechaCreacion DESC";
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, telefono);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(mapearPedido(rs));
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al buscar pedidos por teléfono: " + e.getMessage());
        }
        return pedidos;
    }

    @Override
    public List<Pedido> buscarPorRangoDeFechas(LocalDateTime inicio, LocalDateTime fin)
            throws PersistenciaException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT idPedido, fechaCreacion, estado, idCliente, totalPagar "
                + "FROM Pedidos "
                + "WHERE fechaCreacion BETWEEN ? AND ? "
                + "ORDER BY fechaCreacion DESC";
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(inicio));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(mapearPedido(rs));
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al buscar pedidos por rango de fechas: " + e.getMessage());
        }
        return pedidos;
    }

    @Override
    public LocalDateTime obtenerFechaHoraListo(int idPedido) throws PersistenciaException {
        String sql = "SELECT fechaHoraCambio "
                + "FROM HistorialEstados "
                + "WHERE idPedido = ? AND estadoNuevo = 'Listo' "
                + "ORDER BY fechaHoraCambio DESC "
                + "LIMIT 1";
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getTimestamp("fechaHoraCambio").toLocalDateTime();
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener fecha de estado Listo: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Pedido> obtenerPedidosPorCliente(int idCliente) throws PersistenciaException {
        List<Pedido> pedidos = new ArrayList<>();
        // Solo trae pedidos programados (los que tienen idCliente no nulo y
        // están registrados en la tabla PedidoProgramados)
        String sql = "SELECT p.idPedido, p.fechaCreacion, p.estado, p.idCliente, p.totalPagar "
                + "FROM Pedidos p "
                + "INNER JOIN PedidoProgramados pp ON p.idPedido = pp.idPedidoProgramado "
                + "WHERE p.idCliente = ? "
                + "ORDER BY p.fechaCreacion DESC";
        try (Connection conn = conexionBD.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(mapearPedido(rs));
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener pedidos del cliente: " + e.getMessage());
        }
        return pedidos;
    }
}