/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import negocio.BOs.IProductoBO;
import negocio.BOs.ProductoBO;
import negocio.DTOs.ProductoDTO;
import negocio.DTOs.UsuarioDTO; // Importante para la sesión
import negocio.Exception.NegocioException;
import persistencia.DAOs.IProductoDAO;
import persistencia.DAOs.ProductoDAO;
import persistencia.conexion.ConexionBD;

/**
 * pantalla para el CRUD de Productos
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
    
    // Tabla
    private JTable TblProductos;
    private DefaultTableModel modeloTabla;
    private JScrollPane ScrollTabla;
    private IProductoBO productoBO;
    private UsuarioDTO sesionActual;
    
    private int idProductoSeleccionado = -1;

    public FrmGestionProductos(UsuarioDTO sesion) {
        this.sesionActual = sesion; 

        setTitle("Gestión de Productos - Maye´s Pizzas");
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
    
    private void inicializarNegocio() {
        ConexionBD conexionBD = new ConexionBD();
        IProductoDAO productoDAO = new ProductoDAO(conexionBD);
        productoBO = new ProductoBO(productoDAO);
    }

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

        LblDescripcion = new JLabel("Descripción *");
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

        LblTamanio = new JLabel("Tamaño *");
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

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Tamaño", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Que no puedan escribir sobre la tabla
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

    private void aplicarEstilos() {
        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnGuardar.setBackground(new Color(255, 140, 0));
        BtnGuardar.setForeground(Color.WHITE);
        
        BtnActualizar.setBackground(new Color(0, 128, 0)); // Verde
        BtnActualizar.setForeground(Color.WHITE);
        
        BtnEliminar.setBackground(new Color(200, 0, 0)); // Rojo
        BtnEliminar.setForeground(Color.WHITE);

        BtnLimpiar.setBackground(new Color(100, 100, 100));
        BtnLimpiar.setForeground(Color.WHITE);

        BtnRegresar.setBackground(new Color(50, 50, 50));
        BtnRegresar.setForeground(Color.WHITE);
    }

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
                    // la descripcion no esta en la tabla, asi que la consultamos de la BD
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
                JOptionPane.showMessageDialog(this, "Producto registrado con exito");
                limpiarFormulario();
                cargarTabla();
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al guardar", JOptionPane.ERROR_MESSAGE);
            }
        });

        BtnActualizar.addActionListener(e -> {
            if (!validarFormulario()) return;

            try {
                ProductoDTO modificado = capturarDatosFormulario();
                modificado.setIdProducto(idProductoSeleccionado); // Le ponemos el ID de la que seleccionaron
                productoBO.actualizarProducto(modificado); 
                
                JOptionPane.showMessageDialog(this, "Producto actualizado con exiot");
                limpiarFormulario();
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Funcion pendiente de implementar en el BO: " + ex.getMessage());
            }
        });

        // eliminar pizza
        BtnEliminar.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(this, "seguro de que deseas eliminar este producto", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    productoBO.eliminarProducto(idProductoSeleccionado);
                    
                    JOptionPane.showMessageDialog(this, "Producto eliminado");
                    limpiarFormulario();
                    cargarTabla();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Función pendiente de implementar en el BO: " + ex.getMessage());
                }
            }
        });
    }

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
            JOptionPane.showMessageDialog(this, "Error al cargar los productos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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
     * ayuda para no repetir codigo
     * @return 
     */
    private boolean validarFormulario() {
        if (TxtNombre.getText().trim().isEmpty() || TxtDescripcion.getText().trim().isEmpty() || TxtPrecio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos marcados con algo", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(TxtPrecio.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser numerico bro", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * ayuda para recolectar lo que se escribio
     * @return 
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
