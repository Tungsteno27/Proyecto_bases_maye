/*
 * click nbfsnbhostsystemfilesystemtemplateslicenseslicense-defaulttxt to change this license
 * click nbfsnbhostsystemfilesystemtemplatesclassesclassjava to edit this template
 */
package frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import negocio.BOs.IProductoBO;
import negocio.BOs.ProductoBO;
import negocio.DTOs.ProductoDTO;
import negocio.DTOs.UsuarioDTO; // importante para la sesion
import negocio.Exception.NegocioException;
import persistencia.DAOs.IProductoDAO;
import persistencia.DAOs.ProductoDAO;
import persistencia.conexion.ConexionBD;

/**
 * pantalla para el crud de productos
 * permite a los administradores registrar editar y dar de baja
 * productos del menu asi como visualizar el catalogo completo
 *
 * @author julian izaguirre
 */
public class FrmGestionProductos extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;
    
    private JLabel LblNombre, LblDescripcion, LblPrecio, LblTamanio, LblEstado;
    private JTextField TxtNombre, TxtDescripcion, TxtPrecio;
    private JComboBox<String> CmbTamanio, CmbEstado;
    
    // buttons
    private JButton BtnGuardar, BtnActualizar, BtnEliminar, BtnLimpiar, BtnRegresar;
    
    // tabla
    private JTable TblProductos;
    private DefaultTableModel modeloTabla;
    private JScrollPane ScrollTabla;
    private IProductoBO productoBO;
    private UsuarioDTO sesionActual;
    
    private int idProductoSeleccionado = -1;

    /**
     * constructor principal de la ventana de gestion de productos
     * inicializa la conexion los componentes visuales y carga los productos de la bd
     * * @param sesion el usuario actual que ha iniciado sesion
     */
    public FrmGestionProductos(UsuarioDTO sesion) {
        this.sesionActual = sesion; 

        setTitle("Gestion de Productos - Mayes Pizzas");
        setSize(850, 520);
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
     * prepara la instanciacion de la capa de negocio
     * crea la conexion y los objetos dao y bo requeridos
     */
    private void inicializarNegocio() {
        ConexionBD conexionBD = new ConexionBD();
        IProductoDAO productoDAO = new ProductoDAO(conexionBD);
        productoBO = new ProductoBO(productoDAO);
    }

    /**
     * construye y ubica todos los elementos visuales en la ventana
     * configura las cajas de texto combos botones y la tabla
     */
    private void inicializarComponentes() {
        PnlPrincipal = new JPanel();
        PnlPrincipal.setLayout(null);
        add(PnlPrincipal);

        LblTitulo = new JLabel("GESTION DE PRODUCTOS");
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        LblTitulo.setBounds(250, 20, 350, 40);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        PnlPrincipal.add(LblTitulo);

        LblNombre = new JLabel("Nombre de la Pizza *");
        LblNombre.setBounds(30, 80, 200, 25);
        PnlPrincipal.add(LblNombre);
        TxtNombre = new JTextField();
        TxtNombre.setBounds(30, 105, 200, 30);
        PnlPrincipal.add(TxtNombre);

        LblDescripcion = new JLabel("Descripcion *");
        LblDescripcion.setBounds(30, 145, 200, 25);
        PnlPrincipal.add(LblDescripcion);
        TxtDescripcion = new JTextField();
        TxtDescripcion.setBounds(30, 170, 200, 30);
        PnlPrincipal.add(TxtDescripcion);

        LblPrecio = new JLabel("Precio ($) *");
        LblPrecio.setBounds(30, 210, 200, 25);
        PnlPrincipal.add(LblPrecio);
        TxtPrecio = new JTextField();
        TxtPrecio.setBounds(30, 235, 200, 30);
        PnlPrincipal.add(TxtPrecio);

        LblTamanio = new JLabel("Tamano *");
        LblTamanio.setBounds(30, 275, 200, 25);
        PnlPrincipal.add(LblTamanio);
        CmbTamanio = new JComboBox<>(new String[]{"CHICA", "MEDIANA", "GRANDE"});
        CmbTamanio.setBounds(30, 300, 200, 30);
        PnlPrincipal.add(CmbTamanio);

        LblEstado = new JLabel("Estado");
        LblEstado.setBounds(30, 340, 200, 25);
        PnlPrincipal.add(LblEstado);
        CmbEstado = new JComboBox<>(new String[]{"DISPONIBLE", "NO_DISPONIBLE"});
        CmbEstado.setBounds(30, 365, 200, 30);
        PnlPrincipal.add(CmbEstado);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Tamano", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // que no puedan escribir sobre la tabla
        };
        TblProductos = new JTable(modeloTabla);
        ScrollTabla = new JScrollPane(TblProductos);
        ScrollTabla.setBounds(260, 105, 540, 290);
        PnlPrincipal.add(ScrollTabla);

        BtnGuardar = new JButton("Guardar Nuevo");
        BtnGuardar.setBounds(30, 415, 130, 35);
        PnlPrincipal.add(BtnGuardar);

        BtnActualizar = new JButton("Actualizar");
        BtnActualizar.setBounds(170, 415, 130, 35);
        BtnActualizar.setEnabled(false); 
        PnlPrincipal.add(BtnActualizar);

        BtnEliminar = new JButton("Eliminar");
        BtnEliminar.setBounds(310, 415, 130, 35);
        BtnEliminar.setEnabled(false); 
        PnlPrincipal.add(BtnEliminar);

        BtnLimpiar = new JButton("Limpiar");
        BtnLimpiar.setBounds(450, 415, 130, 35);
        PnlPrincipal.add(BtnLimpiar);

        BtnRegresar = new JButton("Regresar al Panel");
        BtnRegresar.setBounds(650, 415, 150, 35);
        PnlPrincipal.add(BtnRegresar);
    }

    /**
     * define los colores de fondo de los botones y paneles
     * para mantener la estetica de la aplicacion
     */
    private void aplicarEstilos() {
        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnGuardar.setBackground(new Color(255, 140, 0));
        BtnGuardar.setForeground(Color.WHITE);
        
        BtnActualizar.setBackground(new Color(0, 128, 0)); // verde
        BtnActualizar.setForeground(Color.WHITE);
        
        BtnEliminar.setBackground(new Color(200, 0, 0)); // rojo
        BtnEliminar.setForeground(Color.WHITE);

        BtnLimpiar.setBackground(new Color(100, 100, 100));
        BtnLimpiar.setForeground(Color.WHITE);

        BtnRegresar.setBackground(new Color(50, 50, 50));
        BtnRegresar.setForeground(Color.WHITE);
    }

    /**
     * agrupa todos los listeners de los botones y de la tabla
     * se encarga de ejecutar el guardado la edicion y la eliminacion de registros
     */
    private void agregarEventos() {
        BtnRegresar.addActionListener(e -> {
            new FrmPanelPersonal(sesionActual); 
            dispose();
        });

        // limpiar el formulario
        BtnLimpiar.addActionListener(e -> limpiarFormulario());

        TblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = TblProductos.getSelectedRow();
                if (fila != -1) {
                    idProductoSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
                    TxtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                    
                    String precioStr = modeloTabla.getValueAt(fila, 2).toString().replace("$", "");
                    TxtPrecio.setText(precioStr);
                    
                    CmbTamanio.setSelectedItem(modeloTabla.getValueAt(fila, 3).toString());
                    CmbEstado.setSelectedItem(modeloTabla.getValueAt(fila, 4).toString());
                    // la descripcion no esta en la tabla asi que la consultamos de la bd
                    try {
                        ProductoDTO p = productoBO.obtenerProductoPorId(idProductoSeleccionado);
                        TxtDescripcion.setText(p.getDescripcion());
                    } catch (Exception ignored) {}

                    BtnGuardar.setEnabled(false);
                    BtnActualizar.setEnabled(true);
                    BtnEliminar.setEnabled(true);
                }
            }
        });

        BtnGuardar.addActionListener(e -> {
            if (!validarFormulario()) return;

            try {
                ProductoDTO nuevo = capturarDatosFormulario();
                productoBO.agregarProducto(nuevo);
                JOptionPane.showMessageDialog(this, "producto registrado con exito");
                limpiarFormulario();
                cargarTabla();
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "error al guardar", JOptionPane.ERROR_MESSAGE);
            }
        });

        BtnActualizar.addActionListener(e -> {
            if (!validarFormulario()) return;

            try {
                ProductoDTO modificado = capturarDatosFormulario();
                modificado.setIdProducto(idProductoSeleccionado); // le ponemos el id de la que seleccionaron
                productoBO.actualizarProducto(modificado); 
                
                JOptionPane.showMessageDialog(this, "producto actualizado con exito");
                limpiarFormulario();
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "funcion pendiente de implementar en el bo: " + ex.getMessage());
            }
        });

        // eliminar pizza
        BtnEliminar.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(this, "seguro de que deseas eliminar este producto", "confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    productoBO.eliminarProducto(idProductoSeleccionado);
                    
                    JOptionPane.showMessageDialog(this, "producto eliminado");
                    limpiarFormulario();
                    cargarTabla();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "funcion pendiente de implementar en el bo: " + ex.getMessage());
                }
            }
        });
    }

    /**
     * consulta los productos disponibles mediante el bo
     * y vacia la informacion en el modelo de la tabla grafica
     */
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        try {
            List<ProductoDTO> listaProductos = productoBO.obtenerProductosDisponibles();
            for (ProductoDTO prod : listaProductos) {
                modeloTabla.addRow(new Object[]{
                    prod.getIdProducto(),
                    prod.getNombre(),
                    "$" + prod.getPrecio(),
                    prod.getTamanio(),
                    prod.getEstado()
                });
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "error al cargar los productos: " + ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * vacia las cajas de texto devuelve los combos a su estado original
     * y reinicia los botones deshabilitando la opcion de actualizar y eliminar
     */
    private void limpiarFormulario() {
        idProductoSeleccionado = -1;
        TxtNombre.setText("");
        TxtDescripcion.setText("");
        TxtPrecio.setText("");
        CmbTamanio.setSelectedIndex(0);
        CmbEstado.setSelectedIndex(0);
        
        BtnGuardar.setEnabled(true);
        BtnActualizar.setEnabled(false);
        BtnEliminar.setEnabled(false);
        TblProductos.clearSelection();
    }
    
    /**
     * valida que todos los campos requeridos esten llenos
     * y se asegura de que el precio introducido sea un numero valido
     * * @return true si todo esta correcto false si hay un error
     */
    private boolean validarFormulario() {
        if (TxtNombre.getText().trim().isEmpty() || TxtDescripcion.getText().trim().isEmpty() || TxtPrecio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "debe completar todos los campos marcados", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(TxtPrecio.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "el precio debe ser numerico", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * empaqueta la informacion que el usuario tecleo en el formulario
     * dentro de un objeto de transferencia de datos para pasarlo al bo
     * * @return un productodto cargado con la info capturada
     */
    private ProductoDTO capturarDatosFormulario() {
        ProductoDTO p = new ProductoDTO();
        p.setNombre(TxtNombre.getText().trim());
        p.setDescripcion(TxtDescripcion.getText().trim());
        p.setPrecio(Double.parseDouble(TxtPrecio.getText().trim()));
        p.setTamanio(CmbTamanio.getSelectedItem().toString());
        p.setEstado(CmbEstado.getSelectedItem().toString());
        return p;
    }
}
