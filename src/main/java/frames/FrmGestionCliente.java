/*
 * click nbfsnbhostsystemfilesystemtemplateslicenseslicense-defaulttxt to change this license
 * click nbfsnbhostsystemfilesystemtemplatesclassesclassjava to edit this template
 */
package frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import negocio.BOs.ClienteBO;
import negocio.BOs.IClienteBO;
import negocio.BOs.ITelefonoBO;
import negocio.BOs.TelefonoBO;
import negocio.DTOs.UsuarioDTO;
import negocio.Exception.NegocioException;
import persistencia.DAOs.ClienteDAO;
import persistencia.DAOs.TelefonoDAO;
import persistencia.conexion.ConexionBD;

/**
 * pantalla para el crud de clientes
 * permite a los empleados y administradores visualizar a los clientes registrados,
 * consultar sus datos de contacto y cambiar su estatus operativo en el sistema
 *
 * @author julian izaguirre
 */
public class FrmGestionCliente extends JFrame {
    
    private JPanel PnlPrincipal;
    private JLabel LblTitulo;
    
    // formulario
    private JLabel LblNombres, LblApellidos, LblTelefono, LblEstatus;
    private JTextField TxtNombres, TxtApellidoPaterno, TxtApellidoMaterno, TxtTelefono;
    private JComboBox<String> CmbEstatus;
    
    private JButton BtnActualizar, BtnEliminar, BtnLimpiar, BtnRegresar;
    
    private JTable TblClientes;
    private DefaultTableModel modeloTabla;
    private JScrollPane ScrollTabla;

    private UsuarioDTO sesionActual;
    private int idClienteSeleccionado = -1;
    
    // bo de clientes
    private IClienteBO clienteBO;

    /**
     * constructor principal de la ventana de gestion de clientes
     * inicializa la capa de negocio los componentes graficos y carga la tabla
     * * @param sesion el usuario actual que ha iniciado sesion en el sistema
     */
    public FrmGestionCliente(UsuarioDTO sesion) {
        this.sesionActual = sesion;

        setTitle("Gestion de Clientes - Mayes Pizzas");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarNegocio(); 

        inicializarComponentes();
        aplicarEstilos();
        agregarEventos();
        
        cargarTabla(); 

        setVisible(true);
    }
    
    /**
     * inicializa las instancias de la capa de negocio y acceso a datos
     * prepara la conexion a la base de datos para realizar las consultas de clientes
     */
    private void inicializarNegocio() {
        ConexionBD conexion = new ConexionBD();
        ITelefonoBO telefonoBO = new TelefonoBO(new TelefonoDAO(conexion));
        clienteBO = new ClienteBO(
            new ClienteDAO(conexion), 
            null, 
            null, 
            telefonoBO
        );
    }

    /**
     * construye y posiciona todos los elementos visuales de la interfaz grafica
     * define los campos de texto etiquetas botones y la tabla de registros
     */
    private void inicializarComponentes() {
        PnlPrincipal = new JPanel();
        PnlPrincipal.setLayout(null);
        add(PnlPrincipal);

        LblTitulo = new JLabel("GESTION DE CLIENTES");
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        LblTitulo.setBounds(250, 20, 400, 40);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        PnlPrincipal.add(LblTitulo);

        LblNombres = new JLabel("Nombre(s) *");
        LblNombres.setBounds(30, 80, 200, 25);
        PnlPrincipal.add(LblNombres);
        TxtNombres = new JTextField();
        TxtNombres.setBounds(30, 105, 200, 30);
        TxtNombres.setEditable(false);
        PnlPrincipal.add(TxtNombres);

        LblApellidos = new JLabel("Apellidos (Paterno y Materno) *");
        LblApellidos.setBounds(30, 145, 250, 25);
        PnlPrincipal.add(LblApellidos);
        TxtApellidoPaterno = new JTextField();
        TxtApellidoPaterno.setBounds(30, 170, 100, 30);
        TxtApellidoPaterno.setEditable(false); // no se edita desde aqui
        PnlPrincipal.add(TxtApellidoPaterno);
        TxtApellidoMaterno = new JTextField();
        TxtApellidoMaterno.setBounds(140, 170, 100, 30);
        TxtApellidoMaterno.setEditable(false); // no se edita desde aqui
        PnlPrincipal.add(TxtApellidoMaterno);

        LblTelefono = new JLabel("Telefono Principal");
        LblTelefono.setBounds(30, 210, 200, 25);
        PnlPrincipal.add(LblTelefono);
        TxtTelefono = new JTextField();
        TxtTelefono.setBounds(30, 235, 200, 30);
        TxtTelefono.setEditable(false); // no se edita desde aqui
        PnlPrincipal.add(TxtTelefono);

        LblEstatus = new JLabel("Estatus en el Sistema");
        LblEstatus.setBounds(30, 275, 200, 25);
        PnlPrincipal.add(LblEstatus);
        CmbEstatus = new JComboBox<>(new String[]{"ACTIVO", "INACTIVO"});
        CmbEstatus.setBounds(30, 300, 200, 30);
        PnlPrincipal.add(CmbEstatus);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre Completo", "Telefono", "Estatus"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } 
        };
        TblClientes = new JTable(modeloTabla);
        ScrollTabla = new JScrollPane(TblClientes);
        ScrollTabla.setBounds(270, 105, 580, 250);
        PnlPrincipal.add(ScrollTabla);

        BtnActualizar = new JButton("Actualizar Datos");
        BtnActualizar.setBounds(30, 415, 140, 35);
        BtnActualizar.setEnabled(false); 
        PnlPrincipal.add(BtnActualizar);

        BtnEliminar = new JButton("Dar de Baja");
        BtnEliminar.setBounds(190, 415, 140, 35);
        BtnEliminar.setEnabled(false); 
        PnlPrincipal.add(BtnEliminar);

        BtnLimpiar = new JButton("Limpiar");
        BtnLimpiar.setBounds(350, 415, 130, 35);
        PnlPrincipal.add(BtnLimpiar);

        BtnRegresar = new JButton("Regresar al Panel");
        BtnRegresar.setBounds(700, 415, 150, 35);
        PnlPrincipal.add(BtnRegresar);
    }

    /**
     * aplica los colores y estilos de fuente a los componentes de la ventana
     */
    private void aplicarEstilos() {
        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnActualizar.setBackground(new Color(0, 128, 0)); 
        BtnActualizar.setForeground(Color.WHITE);
        
        BtnEliminar.setBackground(new Color(200, 0, 0)); 
        BtnEliminar.setForeground(Color.WHITE);

        BtnLimpiar.setBackground(new Color(100, 100, 100));
        BtnLimpiar.setForeground(Color.WHITE);

        BtnRegresar.setBackground(new Color(50, 50, 50)); 
        BtnRegresar.setForeground(Color.WHITE);
    }

    /**
     * registra los oyentes de eventos para los botones y la tabla
     * define la logica a ejecutar cuando el usuario interactua con la interfaz
     */
    private void agregarEventos() {
        
        BtnRegresar.addActionListener(e -> {
            new FrmPanelPersonal(sesionActual); 
            dispose();
        });

        BtnLimpiar.addActionListener(e -> limpiarFormulario());
        
        // al hacer clic en la tabla
        TblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = TblClientes.getSelectedRow();
                if (fila != -1) {
                    idClienteSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
                    
                    try {
                        negocio.DTOs.ClienteDTO cliente = clienteBO.obtenerClientePorIdUsuario(idClienteSeleccionado);
                        TxtNombres.setText(cliente.getNombres());
                        TxtApellidoPaterno.setText(cliente.getApellidoPaterno());
                        TxtApellidoMaterno.setText(cliente.getApellidoMaterno() != null ? cliente.getApellidoMaterno() : "");
                        
                        if (cliente.getTelefonos() != null && !cliente.getTelefonos().isEmpty()) {
                            TxtTelefono.setText(cliente.getTelefonos().get(0).getNumero());
                        } else {
                            TxtTelefono.setText("");
                        }
                    } catch (NegocioException ex) {
                         JOptionPane.showMessageDialog(FrmGestionCliente.this, "error al cargar los detalles del cliente", "error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    String estatus = modeloTabla.getValueAt(fila, 3).toString();
                    CmbEstatus.setSelectedItem(estatus);

                    BtnActualizar.setEnabled(true);
                    BtnEliminar.setEnabled(true);
                }
            }
        });

        BtnActualizar.addActionListener(e -> {
            String nuevoEstatus = CmbEstatus.getSelectedItem().toString();
            
            try {
                // mandamos a guardar el nuevo estatus a la base de datos
                clienteBO.cambiarEstatus(idClienteSeleccionado, nuevoEstatus);
                
                JOptionPane.showMessageDialog(this, "el estatus del cliente se actualizo correctamente a: " + nuevoEstatus, "actualizacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                
                limpiarFormulario();
                cargarTabla(); // refrescamos la tabla para ver el cambio
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "error al actualizar: " + ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(this, "para actualizar la informacion personal el cliente debe hacerlo desde su perfil", "informacion", JOptionPane.INFORMATION_MESSAGE);
        });

        BtnEliminar.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(this, "estas seguro de que deseas dar de baja desactivar a este cliente?", "confirmar baja", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    clienteBO.darDeBajaCliente(idClienteSeleccionado);
                    JOptionPane.showMessageDialog(this, "cliente dado de baja exitosamente");
                    limpiarFormulario();
                    cargarTabla(); 
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "error al dar de baja: " + ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * consulta la base de datos para obtener todos los clientes registrados
     * y llena el modelo de la tabla visual con sus datos y estatus
     */
    private void cargarTabla() {
        modeloTabla.setRowCount(0); 
        try {
            java.util.List<negocio.DTOs.ClienteDTO> lista = clienteBO.obtenerTodosLosClientes();
            for (negocio.DTOs.ClienteDTO c : lista) {
                
                String nombreCompleto = c.getNombres() + " " + c.getApellidoPaterno() + 
                        (c.getApellidoMaterno() != null ? " " + c.getApellidoMaterno() : "");
                        
                String telefono = (c.getTelefonos() != null && !c.getTelefonos().isEmpty()) 
                                    ? c.getTelefonos().get(0).getNumero() : "sin registro";
                                    
                modeloTabla.addRow(new Object[]{
                    c.getIdCliente(),
                    nombreCompleto,
                    telefono,
                    c.getEstatus()
                });
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "error al cargar la tabla de clientes: " + ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * limpia todos los campos de texto resetea el estatus a su valor por defecto
     * y deshabilita los botones de accion que requieren una seleccion en la tabla
     */
    private void limpiarFormulario() {
        idClienteSeleccionado = -1;
        TxtNombres.setText("");
        TxtApellidoPaterno.setText("");
        TxtApellidoMaterno.setText("");
        TxtTelefono.setText("");
        CmbEstatus.setSelectedIndex(0);
        
        BtnActualizar.setEnabled(false);
        BtnEliminar.setEnabled(false);
        TblClientes.clearSelection();
    }
}
