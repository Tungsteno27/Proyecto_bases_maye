/*
 * click nbfsnbhostsystemfilesystemtemplateslicenseslicense-defaulttxt to change this license
 * click nbfsnbhostsystemfilesystemtemplatesclassesclassjava to edit this template
 */
package frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
 * panel del empleado para gestionar el ciclo de vida de los pedidos
 * permite visualizar pedidos activos buscar por telefono folio o rango de fechas
 * marcar pedidos como listo y registrar entregas
 *
 * @author Noelia E.N.
 */
public class FrmGestionEntregas extends JFrame {

    // --- busqueda ---
    private JTextField TxtBuscarTelefono;
    private JTextField TxtBuscarFolio;
    private JTextField TxtFechaInicio;
    private JTextField TxtFechaFin;
    private JButton BtnBuscarTelefono;
    private JButton BtnBuscarFolio;
    private JButton BtnBuscarFechas;
    private JButton BtnMostrarTodos;

    // --- tabla ---
    private JTable TblPedidos;
    private DefaultTableModel modeloTabla;
    private JScrollPane ScrollTabla;

    // --- detalle ---
    private JLabel LblIdPedido;
    private JLabel LblTipoPedido;
    private JLabel LblEstadoPedido;
    private JLabel LblFolioPedido;
    private JLabel LblFechaPedido;
    private JLabel LblTotalPedido;

    // --- acciones ---
    private JButton BtnMarcarListo;
    private JButton BtnRegistrarEntrega;
    private JButton BtnCancelar;
    private JButton BtnRegresar;

    private PedidoDTO pedidoSeleccionado;
    private UsuarioDTO sesionActual;
    private IPedidoBO pedidoBO;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * constructor principal de la ventana de gestion de entregas
     * configura la interfaz inicializa la conexion y carga los pedidos iniciales
     * * @param sesion el usuario empleado que esta utilizando el sistema
     */
    public FrmGestionEntregas(UsuarioDTO sesion) {
        this.sesionActual = sesion;
        setTitle("Gestion de Entregas - Mayes Pizzas");
        setSize(950, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inicializarNegocio();
        inicializarComponentes();
        aplicarEstilos();
        agregarEventos();
        cargarPedidosActivos();
        setVisible(true);
    }

    /**
     * inicializa la capa de negocio y los objetos dao necesarios
     * prepara la conexion a la base de datos para realizar busquedas y actualizaciones
     */
    private void inicializarNegocio() {
        ConexionBD conexion = new ConexionBD();
        pedidoBO = new PedidoBO(
                new PedidoDAO(conexion),
                new PedidoExpressDAO(conexion)
        );
    }
    
    /**
     * construye y posiciona todos los elementos visuales de la interfaz
     * crea los paneles de busqueda la tabla principal y el area de detalles
     */
    private void inicializarComponentes() {
        JPanel PnlPrincipal = new JPanel();
        PnlPrincipal.setLayout(null);
        add(PnlPrincipal);

        JLabel LblTitulo = new JLabel("GESTION DE ENTREGAS");
        LblTitulo.setBounds(330, 15, 300, 35);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        PnlPrincipal.add(LblTitulo);

        // --- panel busqueda ---
        JPanel PnlBusqueda = new JPanel(null);
        PnlBusqueda.setBounds(10, 60, 600, 110);
        PnlBusqueda.setBorder(BorderFactory.createTitledBorder("buscar pedidos"));
        PnlPrincipal.add(PnlBusqueda);

        JLabel LblTel = new JLabel("Telefono:");
        LblTel.setBounds(10, 25, 70, 25);
        PnlBusqueda.add(LblTel);
        TxtBuscarTelefono = new JTextField();
        TxtBuscarTelefono.setBounds(80, 25, 150, 28);
        PnlBusqueda.add(TxtBuscarTelefono);
        BtnBuscarTelefono = new JButton("Buscar");
        BtnBuscarTelefono.setBounds(240, 25, 80, 28);
        PnlBusqueda.add(BtnBuscarTelefono);

        JLabel LblFolioB = new JLabel("Folio:");
        LblFolioB.setBounds(340, 25, 40, 25);
        PnlBusqueda.add(LblFolioB);
        TxtBuscarFolio = new JTextField();
        TxtBuscarFolio.setBounds(385, 25, 100, 28);
        PnlBusqueda.add(TxtBuscarFolio);
        BtnBuscarFolio = new JButton("Buscar");
        BtnBuscarFolio.setBounds(495, 25, 80, 28);
        PnlBusqueda.add(BtnBuscarFolio);

        JLabel LblDesde = new JLabel("Desde:");
        LblDesde.setBounds(10, 68, 50, 25);
        PnlBusqueda.add(LblDesde);
        TxtFechaInicio = new JTextField("dd/MM/yyyy");
        TxtFechaInicio.setBounds(65, 68, 110, 28);
        PnlBusqueda.add(TxtFechaInicio);

        JLabel LblHasta = new JLabel("Hasta:");
        LblHasta.setBounds(185, 68, 50, 25);
        PnlBusqueda.add(LblHasta);
        TxtFechaFin = new JTextField("dd/MM/yyyy");
        TxtFechaFin.setBounds(240, 68, 110, 28);
        PnlBusqueda.add(TxtFechaFin);

        BtnBuscarFechas = new JButton("Buscar");
        BtnBuscarFechas.setBounds(360, 68, 80, 28);
        PnlBusqueda.add(BtnBuscarFechas);

        BtnMostrarTodos = new JButton("Ver activos");
        BtnMostrarTodos.setBounds(455, 68, 110, 28);
        PnlBusqueda.add(BtnMostrarTodos);

        String[] columnas = {"ID", "Tipo", "Folio", "Fecha", "Estado", "Total"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TblPedidos = new JTable(modeloTabla);
        TblPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TblPedidos.getColumnModel().getColumn(0).setPreferredWidth(40);
        TblPedidos.getColumnModel().getColumn(1).setPreferredWidth(80);
        TblPedidos.getColumnModel().getColumn(2).setPreferredWidth(60);
        TblPedidos.getColumnModel().getColumn(3).setPreferredWidth(140);
        TblPedidos.getColumnModel().getColumn(4).setPreferredWidth(100);
        TblPedidos.getColumnModel().getColumn(5).setPreferredWidth(80);
        ScrollTabla = new JScrollPane(TblPedidos);
        ScrollTabla.setBounds(10, 180, 600, 320);
        PnlPrincipal.add(ScrollTabla);

        // --- panel detalle ---
        JPanel PnlDetalle = new JPanel(null);
        PnlDetalle.setBounds(625, 60, 300, 310);
        PnlDetalle.setBorder(BorderFactory.createTitledBorder("detalle del pedido"));
        PnlPrincipal.add(PnlDetalle);

        JLabel LblIdLabel = new JLabel("ID Pedido:");
        LblIdLabel.setBounds(15, 30, 90, 22);
        PnlDetalle.add(LblIdLabel);
        LblIdPedido = new JLabel("-");
        LblIdPedido.setBounds(110, 30, 170, 22);
        LblIdPedido.setFont(new Font("Arial", Font.BOLD, 13));
        PnlDetalle.add(LblIdPedido);

        JLabel LblTipoLabel = new JLabel("Tipo:");
        LblTipoLabel.setBounds(15, 65, 90, 22);
        PnlDetalle.add(LblTipoLabel);
        LblTipoPedido = new JLabel("-");
        LblTipoPedido.setBounds(110, 65, 170, 22);
        PnlDetalle.add(LblTipoPedido);

        JLabel LblEstadoLabel = new JLabel("Estado:");
        LblEstadoLabel.setBounds(15, 100, 90, 22);
        PnlDetalle.add(LblEstadoLabel);
        LblEstadoPedido = new JLabel("-");
        LblEstadoPedido.setBounds(110, 100, 170, 22);
        LblEstadoPedido.setFont(new Font("Arial", Font.BOLD, 13));
        PnlDetalle.add(LblEstadoPedido);

        JLabel LblFolioLabel = new JLabel("Folio:");
        LblFolioLabel.setBounds(15, 135, 90, 22);
        PnlDetalle.add(LblFolioLabel);
        LblFolioPedido = new JLabel("-");
        LblFolioPedido.setBounds(110, 135, 170, 22);
        PnlDetalle.add(LblFolioPedido);

        JLabel LblFechaLabel = new JLabel("Fecha:");
        LblFechaLabel.setBounds(15, 170, 90, 22);
        PnlDetalle.add(LblFechaLabel);
        LblFechaPedido = new JLabel("-");
        LblFechaPedido.setBounds(110, 170, 170, 22);
        PnlDetalle.add(LblFechaPedido);

        JLabel LblTotalLabel = new JLabel("Total:");
        LblTotalLabel.setBounds(15, 205, 90, 22);
        PnlDetalle.add(LblTotalLabel);
        LblTotalPedido = new JLabel("-");
        LblTotalPedido.setBounds(110, 205, 170, 22);
        LblTotalPedido.setFont(new Font("Arial", Font.BOLD, 13));
        PnlDetalle.add(LblTotalPedido);

        // --- botones de accion ---
        BtnMarcarListo = new JButton("Marcar como Listo");
        BtnMarcarListo.setBounds(625, 385, 300, 40);
        BtnMarcarListo.setEnabled(false);
        PnlPrincipal.add(BtnMarcarListo);

        BtnRegistrarEntrega = new JButton("Registrar Entrega");
        BtnRegistrarEntrega.setBounds(625, 435, 300, 40);
        BtnRegistrarEntrega.setEnabled(false);
        PnlPrincipal.add(BtnRegistrarEntrega);

        BtnCancelar = new JButton("Cancelar Pedido");
        BtnCancelar.setBounds(625, 485, 300, 40);
        BtnCancelar.setEnabled(false);
        PnlPrincipal.add(BtnCancelar);

        BtnRegresar = new JButton("Regresar al Panel");
        BtnRegresar.setBounds(10, 520, 180, 35);
        PnlPrincipal.add(BtnRegresar);

        JLabel LblAyuda = new JLabel("* los pedidos se muestran del mas antiguo al mas reciente");
        LblAyuda.setBounds(200, 525, 400, 20);
        LblAyuda.setFont(new Font("Arial", Font.ITALIC, 11));
        LblAyuda.setForeground(Color.GRAY);
        PnlPrincipal.add(LblAyuda);

        PnlPrincipal.setBackground(new Color(255, 248, 220));
    }
    
    /**
     * aplica colores tipografias y estilos visuales a los componentes de la ventana
     */
    private void aplicarEstilos() {
        BtnBuscarTelefono.setBackground(new Color(255, 140, 0));
        BtnBuscarTelefono.setForeground(Color.WHITE);
        BtnBuscarFolio.setBackground(new Color(255, 140, 0));
        BtnBuscarFolio.setForeground(Color.WHITE);
        BtnBuscarFechas.setBackground(new Color(255, 140, 0));
        BtnBuscarFechas.setForeground(Color.WHITE);
        BtnMostrarTodos.setBackground(new Color(100, 100, 100));
        BtnMostrarTodos.setForeground(Color.WHITE);
        BtnMarcarListo.setBackground(new Color(0, 128, 0));
        BtnMarcarListo.setForeground(Color.WHITE);
        BtnMarcarListo.setFont(new Font("Arial", Font.BOLD, 13));
        BtnRegistrarEntrega.setBackground(new Color(183, 28, 28));
        BtnRegistrarEntrega.setForeground(Color.WHITE);
        BtnRegistrarEntrega.setFont(new Font("Arial", Font.BOLD, 13));
        BtnCancelar.setBackground(new Color(100, 100, 100));
        BtnCancelar.setForeground(Color.WHITE);
        BtnRegresar.setBackground(new Color(50, 50, 50));
        BtnRegresar.setForeground(Color.WHITE);
    }
    
    /**
     * define los manejadores de eventos para todos los botones y para la tabla
     * se encarga de las busquedas la seleccion de elementos y los cambios de estado
     */
    private void agregarEventos() {

        BtnRegresar.addActionListener(e -> {
            new FrmPanelPersonal(sesionActual);
            dispose();
        });

        BtnMostrarTodos.addActionListener(e -> cargarPedidosActivos());

        BtnBuscarTelefono.addActionListener(e -> {
            String tel = TxtBuscarTelefono.getText().trim();
            if (tel.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ingrese un numero de telefono", "aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                List<PedidoDTO> resultados = pedidoBO.buscarPorTelefono(tel);
                cargarTabla(resultados);
                if (resultados.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "no se encontraron pedidos para ese telefono", "sin resultados", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
            }
        });

        BtnBuscarFolio.addActionListener(e -> {
            String folioStr = TxtBuscarFolio.getText().trim();
            if (folioStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ingrese un numero de folio", "aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int folio = Integer.parseInt(folioStr);
                List<PedidoDTO> activos = pedidoBO.obtenerPedidosActivos();
                PedidoDTO resultado = activos.stream()
                        .filter(p -> p.getFolio() != null && p.getFolio() == folio)
                        .findFirst()
                        .orElse(null);
                modeloTabla.setRowCount(0);
                if (resultado != null) {
                    agregarFilaTabla(resultado);
                    mostrarDetalle(resultado);
                } else {
                    JOptionPane.showMessageDialog(this, "no se encontro ningun pedido express con el folio: " + folio, "sin resultados", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "el folio debe ser un numero entero", "error", JOptionPane.ERROR_MESSAGE);
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
            }
        });

        BtnBuscarFechas.addActionListener(e -> {
            try {
                LocalDate inicio = LocalDate.parse(TxtFechaInicio.getText().trim(), FORMATO_FECHA);
                LocalDate fin = LocalDate.parse(TxtFechaFin.getText().trim(), FORMATO_FECHA);
                List<PedidoDTO> resultados = pedidoBO.buscarPorRangoDeFechas(
                        inicio.atStartOfDay(), fin.atTime(LocalTime.MAX));
                cargarTabla(resultados);
                if (resultados.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "no se encontraron pedidos en ese rango", "sin resultados", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "formato de fecha incorrecto use dd/MM/yyyy", "error", JOptionPane.ERROR_MESSAGE);
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
            }
        });

        TblPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = TblPedidos.getSelectedRow();
                if (fila != -1) {
                    int idPedido = (int) modeloTabla.getValueAt(fila, 0);
                    try {
                        List<PedidoDTO> activos = pedidoBO.obtenerPedidosActivos();
                        pedidoSeleccionado = activos.stream()
                                .filter(p -> p.getIdPedido() == idPedido)
                                .findFirst().orElse(null);
                        if (pedidoSeleccionado != null) {
                            mostrarDetalle(pedidoSeleccionado);
                            actualizarBotones();
                        }
                    } catch (NegocioException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        BtnMarcarListo.addActionListener(e -> {
            if (pedidoSeleccionado == null) {
                return;
            }
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "marcar el pedido " + pedidoSeleccionado.getIdPedido() + " como listo?",
                    "confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }
            try {
                pedidoBO.marcarComoListo(pedidoSeleccionado.getIdPedido());
                JOptionPane.showMessageDialog(this, "pedido marcado como listo correctamente", "exito", JOptionPane.INFORMATION_MESSAGE);
                limpiarSeleccion();
                cargarPedidosActivos();
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
            }
        });

        BtnRegistrarEntrega.addActionListener(e -> {
            if (pedidoSeleccionado == null) {
                return;
            }
            Integer folioIngresado = null;
            String pinIngresado = null;

            if ("Express".equals(pedidoSeleccionado.getTipo())) {
                String folioStr = JOptionPane.showInputDialog(this,
                        "ingrese el folio del pedido express:", "validacion express", JOptionPane.PLAIN_MESSAGE);
                if (folioStr == null || folioStr.trim().isEmpty()) {
                    return;
                }
                try {
                    folioIngresado = Integer.parseInt(folioStr.trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "el folio debe ser un numero", "error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                pinIngresado = JOptionPane.showInputDialog(this,
                        "ingrese el pin de 8 digitos:", "validacion express", JOptionPane.PLAIN_MESSAGE);
                if (pinIngresado == null || pinIngresado.trim().isEmpty()) {
                    return;
                }
            }

            try {
                pedidoBO.marcarComoEntregado(pedidoSeleccionado.getIdPedido(), folioIngresado, pinIngresado);
                JOptionPane.showMessageDialog(this, "pedido entregado y pago registrado correctamente", "exito", JOptionPane.INFORMATION_MESSAGE);
                limpiarSeleccion();
                cargarPedidosActivos();
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
                cargarPedidosActivos();
            }
        });

        BtnCancelar.addActionListener(e -> {
            if (pedidoSeleccionado == null) {
                return;
            }
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "cancelar el pedido " + pedidoSeleccionado.getIdPedido() + "\nesta accion no se puede deshacer",
                    "confirmar cancelacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }
            try {
                pedidoBO.cancelarPedido(pedidoSeleccionado.getIdPedido());
                JOptionPane.showMessageDialog(this, "pedido cancelado correctamente", "exito", JOptionPane.INFORMATION_MESSAGE);
                limpiarSeleccion();
                cargarPedidosActivos();
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * consulta la base de datos y carga en la tabla todos los pedidos que aun no se han entregado
     */
    private void cargarPedidosActivos() {
        try {
            cargarTabla(pedidoBO.obtenerPedidosActivos());
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "error al cargar pedidos: " + ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * recibe una lista de pedidos limpia el modelo de la tabla y los agrega uno por uno
     * * @param pedidos la lista de objetos dtos a mostrar
     */
    private void cargarTabla(List<PedidoDTO> pedidos) {
        modeloTabla.setRowCount(0);
        for (PedidoDTO p : pedidos) {
            agregarFilaTabla(p);
        }
        limpiarSeleccion();
    }

    /**
     * agrega un pedido individual como una fila al modelo visual de la tabla
     * * @param p el objeto con la informacion del pedido a agregar
     */
    private void agregarFilaTabla(PedidoDTO p) {
        String tipo = p.getTipo() != null ? p.getTipo() : "-";
        String folio = p.getFolio() != null ? String.valueOf(p.getFolio()) : "-";
        String fecha = p.getFechaCreacion() != null
                ? p.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "-";
        modeloTabla.addRow(new Object[]{
            p.getIdPedido(), tipo, folio, fecha,
            p.getEstado(), "$" + String.format("%.2f", p.getTotalPagar())
        });
    }

    /**
     * actualiza el panel de detalles lateral con la informacion del pedido seleccionado
     * * @param p el pedido del cual se extraeran los detalles
     */
    private void mostrarDetalle(PedidoDTO p) {
        LblIdPedido.setText(String.valueOf(p.getIdPedido()));
        LblTipoPedido.setText(p.getTipo() != null ? p.getTipo() : "-");
        LblEstadoPedido.setText(p.getEstado());
        LblFolioPedido.setText(p.getFolio() != null ? String.valueOf(p.getFolio()) : "n/a");
        LblFechaPedido.setText(p.getFechaCreacion() != null
                ? p.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "-");
        LblTotalPedido.setText("$" + String.format("%.2f", p.getTotalPagar()));
        switch (p.getEstado()) {
            case "PENDIENTE":
                LblEstadoPedido.setForeground(new Color(200, 100, 0));
                break;
            case "EN_PREPARACION":
                LblEstadoPedido.setForeground(new Color(0, 100, 200));
                break;
            case "LISTO":
                LblEstadoPedido.setForeground(new Color(0, 128, 0));
                break;
            default:
                LblEstadoPedido.setForeground(Color.BLACK);
        }
    }

    /**
     * habilita o deshabilita los botones de accion segun el estado del pedido actual
     * evita que se entregue un pedido que apenas esta pendiente o se cancele uno ya listo
     */
    private void actualizarBotones() {
        if (pedidoSeleccionado == null) {
            BtnMarcarListo.setEnabled(false);
            BtnRegistrarEntrega.setEnabled(false);
            BtnCancelar.setEnabled(false);
            return;
        }
        String estado = pedidoSeleccionado.getEstado();
        BtnMarcarListo.setEnabled("PENDIENTE".equals(estado) || "EN_PREPARACION".equals(estado));
        BtnRegistrarEntrega.setEnabled("LISTO".equals(estado));
        BtnCancelar.setEnabled("PENDIENTE".equals(estado));
    }

    /**
     * deselecciona la fila actual limpia los textos del panel de detalles
     * y deshabilita todos los botones de operacion
     */
    private void limpiarSeleccion() {
        pedidoSeleccionado = null;
        TblPedidos.clearSelection();
        LblIdPedido.setText("-");
        LblTipoPedido.setText("-");
        LblEstadoPedido.setText("-");
        LblEstadoPedido.setForeground(Color.BLACK);
        LblFolioPedido.setText("-");
        LblFechaPedido.setText("-");
        LblTotalPedido.setText("-");
        actualizarBotones();
    }
}

