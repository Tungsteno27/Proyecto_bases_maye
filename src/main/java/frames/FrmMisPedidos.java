/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import negocio.BOs.IPedidoBO;
import negocio.BOs.PedidoBO;
import negocio.DTOs.PedidoDTO;
import negocio.DTOs.UsuarioDTO;
import negocio.Exception.NegocioException;
import persistencia.DAOs.PedidoDAO;
import persistencia.DAOs.PedidoExpressDAO;
import persistencia.conexion.ConexionBD;

/**
 * Representa la ventana de consulta de pedidos del usuario dentro del sistema.
 * <p>
 * Esta clase permite visualizar el historial completo de pedidos realizados por
 * el usuario, con filtro por tipo (Programados o Express).
 * </p>
 * Forma parte de la capa de presentación.
 *
 * @author Noelia E.N
 */
public class FrmMisPedidos extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;
    private JComboBox<String> CmbFiltro;
    private JButton BtnFiltrar;
    private JTextArea TxtAreaPedidos;
    private JScrollPane ScrollPedidos;
    private JButton BtnRegresar;

    private UsuarioDTO sesionActual;
    private IPedidoBO pedidoBO;

    private static final DateTimeFormatter FORMATO_FECHA
            = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public FrmMisPedidos(UsuarioDTO sesion) {
        this.sesionActual = sesion;
        setTitle("Mis pedidos - Maye´s Pizzas");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        inicializarNegocio();
        inicializarComponentes();
        aplicarEstilos();
        agregarEventos();
        setVisible(true);
    }

    private void inicializarNegocio() {
        ConexionBD conexion = new ConexionBD();
        pedidoBO = new PedidoBO(
                new PedidoDAO(conexion),
                new PedidoExpressDAO(conexion)
        );
    }

    private void inicializarComponentes() {
        PnlPrincipal = new JPanel();
        PnlPrincipal.setLayout(null);
        add(PnlPrincipal);

        LblTitulo = new JLabel("MIS PEDIDOS");
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        LblTitulo.setBounds(150, 30, 300, 40);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        PnlPrincipal.add(LblTitulo);

        CmbFiltro = new JComboBox<>();
        CmbFiltro.addItem("Programados");
        CmbFiltro.addItem("Express");
        CmbFiltro.setBounds(150, 100, 200, 30);
        PnlPrincipal.add(CmbFiltro);

        BtnFiltrar = new JButton("Filtrar");
        BtnFiltrar.setBounds(370, 100, 100, 30);
        PnlPrincipal.add(BtnFiltrar);

        TxtAreaPedidos = new JTextArea();
        TxtAreaPedidos.setEditable(false);
        TxtAreaPedidos.setFont(new Font("Monospaced", Font.PLAIN, 13));
        ScrollPedidos = new JScrollPane(TxtAreaPedidos);
        ScrollPedidos.setBounds(80, 160, 440, 300);
        PnlPrincipal.add(ScrollPedidos);

        BtnRegresar = new JButton("Regresar");
        BtnRegresar.setBounds(230, 500, 140, 35);
        PnlPrincipal.add(BtnRegresar);
    }

    private void aplicarEstilos() {
        PnlPrincipal.setBackground(new Color(255, 248, 220));
        BtnFiltrar.setBackground(new Color(255, 140, 0));
        BtnFiltrar.setForeground(Color.WHITE);
        BtnRegresar.setBackground(new Color(200, 0, 0));
        BtnRegresar.setForeground(Color.WHITE);
    }

    private void agregarEventos() {

        BtnRegresar.addActionListener(e -> {
            new FrmMenuUsuario(sesionActual);
            dispose();
        });

        BtnFiltrar.addActionListener(e -> {
            String filtro = (String) CmbFiltro.getSelectedItem();
            TxtAreaPedidos.setText("");
            if ("Programados".equals(filtro)) {
                mostrarPedidosProgramados();
            } else {
                TxtAreaPedidos.append("Los pedidos Express no están vinculados a tu cuenta.\n\n");
                TxtAreaPedidos.append("Para consultar un pedido Express usa el folio y PIN\n");
                TxtAreaPedidos.append("que recibiste al momento de la compra.");
            }
        });
    }

    // =========================================================================
    // MÉTODOS AUXILIARES
    // =========================================================================
    /**
     * Consulta el historial completo de pedidos programados del cliente
     * (activos, entregados y cancelados) y los muestra en el área de texto.
     */
    private void mostrarPedidosProgramados() {
        try {
            List<PedidoDTO> pedidos = pedidoBO.consultarPedidosProgramadosPorCliente(
                    sesionActual.getIdUsuario()
            );

            if (pedidos == null || pedidos.isEmpty()) {
                TxtAreaPedidos.append("No tienes pedidos programados registrados.");
                return;
            }

            for (PedidoDTO p : pedidos) {
                String fecha = p.getFechaCreacion() != null
                        ? p.getFechaCreacion().format(FORMATO_FECHA)
                        : "Sin fecha";

                TxtAreaPedidos.append("─────────────────────────────────────\n");
                TxtAreaPedidos.append("Pedido #" + p.getIdPedido() + "\n");
                TxtAreaPedidos.append("Fecha:  " + fecha + "\n");
                TxtAreaPedidos.append("Estado: " + formatearEstado(p.getEstado()) + "\n");
                TxtAreaPedidos.append("Total:  $" + String.format("%.2f", p.getTotalPagar()) + "\n");
            }
            TxtAreaPedidos.append("─────────────────────────────────────\n");
            TxtAreaPedidos.append("Total de pedidos: " + pedidos.size() + "\n");

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar tus pedidos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Convierte el nombre del enum a un texto legible para el usuario.
     */
    private String formatearEstado(String estado) {
        if (estado == null) {
            return "-";
        }
        switch (estado) {
            case "PENDIENTE":
                return "Pendiente";
            case "EN_PREPARACION":
                return "En Preparación";
            case "LISTO":
                return "Listo para recoger";
            case "ENTREGADO":
                return "Entregado ✓";
            case "CANCELADO":
                return "Cancelado";
            case "NO_RECLAMADO":
                return "No Reclamado";
            default:
                return estado;
        }
    }
}
