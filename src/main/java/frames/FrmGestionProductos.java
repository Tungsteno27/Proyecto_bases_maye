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
import negocio.Exception.NegocioException;
import persistencia.DAOs.IProductoDAO;
import persistencia.DAOs.ProductoDAO;
import persistencia.conexion.ConexionBD;

/**
 * Pantalla para el CRUD de Productos 
 */
public class FrmGestionProductos extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;
    
    // Formulario
    private JLabel LblNombre, LblDescripcion, LblPrecio, LblTamanio, LblEstado;
    private JTextField TxtNombre, TxtDescripcion, TxtPrecio;
    private JComboBox<String> CmbTamanio, CmbEstado;
    
    // Botones
    private JButton BtnGuardar, BtnLimpiar, BtnRegresar;
    
    // Tabla
    private JTable TblProductos;
    private DefaultTableModel modeloTabla;
    private JScrollPane ScrollTabla;

    // Conexión a la capa de negocio
    private IProductoBO productoBO;

    public FrmGestionProductos() {
        setTitle("Gestión de Productos - Maye´s Pizzas");
        setSize(850, 500); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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

        // lado izquierdo formulario
        
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
        CmbEstado = new JComboBox<>(new String[]{"DISPONIBLE", "AGOTADO"});
        CmbEstado.setBounds(30, 365, 200, 30);
        PnlPrincipal.add(CmbEstado);

        // lado derecho tablilla
        
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Tamaño", "Estado"}, 0);
        TblProductos = new JTable(modeloTabla);
        ScrollTabla = new JScrollPane(TblProductos);
        ScrollTabla.setBounds(260, 105, 540, 290);
        PnlPrincipal.add(ScrollTabla);

        // votones
        
        BtnGuardar = new JButton("Guardar Producto");
        BtnGuardar.setBounds(30, 415, 150, 35);
        PnlPrincipal.add(BtnGuardar);

        BtnLimpiar = new JButton("Limpiar Campos");
        BtnLimpiar.setBounds(260, 415, 140, 35);
        PnlPrincipal.add(BtnLimpiar);

        BtnRegresar = new JButton("Regresar");
        BtnRegresar.setBounds(660, 415, 140, 35);
        PnlPrincipal.add(BtnRegresar);
    }

    private void aplicarEstilos() {
        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnGuardar.setBackground(new Color(255, 140, 0));
        BtnGuardar.setForeground(Color.WHITE);
        
        BtnLimpiar.setBackground(new Color(100, 100, 100));
        BtnLimpiar.setForeground(Color.WHITE);

        BtnRegresar.setBackground(new Color(200, 0, 0));
        BtnRegresar.setForeground(Color.WHITE);
    }

    private void agregarEventos() {
        BtnRegresar.addActionListener(e -> {
            // menú de admin
            // new FrmMenuAdministrador(); 
            dispose();
        });

        BtnLimpiar.addActionListener(e -> limpiarFormulario());

        BtnGuardar.addActionListener(e -> {
            BtnGuardar.setEnabled(false); // Evitar doble clic

            String nombre = TxtNombre.getText().trim();
            String descripcion = TxtDescripcion.getText().trim();
            String precioStr = TxtPrecio.getText().trim();
            String tamanio = CmbTamanio.getSelectedItem().toString();
            String estado = CmbEstado.getSelectedItem().toString();

            // Validaciones básicas
            if (nombre.isEmpty() || descripcion.isEmpty() || precioStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos marcados con *", "Error", JOptionPane.ERROR_MESSAGE);
                BtnGuardar.setEnabled(true);
                return;
            }

            double precio;
            try {
                precio = Double.parseDouble(precioStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El precio debe ser un número válido (ej. 150.50)", "Error", JOptionPane.ERROR_MESSAGE);
                BtnGuardar.setEnabled(true);
                return;
            }

            // Armamos el DTO
            ProductoDTO nuevoProducto = new ProductoDTO();
            nuevoProducto.setNombre(nombre);
            nuevoProducto.setDescripcion(descripcion);
            nuevoProducto.setPrecio(precio);
            nuevoProducto.setTamanio(tamanio);
            nuevoProducto.setEstado(estado);

            try {
                // Lo mandamos a la base de datos
                productoBO.agregarProducto(nuevoProducto);

                JOptionPane.showMessageDialog(this, "Producto registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                limpiarFormulario();
                cargarTabla(); // Refrescamos la tabla para que aparezca la nueva pizza

            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al guardar", JOptionPane.ERROR_MESSAGE);
            } finally {
                BtnGuardar.setEnabled(true); // Siempre volvemos a prender el botón al terminar
            }
        });
    }

    // Método para llenar la tabla con la información de la base de datos
    private void cargarTabla() {
        modeloTabla.setRowCount(0); // Limpiamos la tabla antes de cargar
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
        TxtNombre.setText("");
        TxtDescripcion.setText("");
        TxtPrecio.setText("");
        CmbTamanio.setSelectedIndex(0);
        CmbEstado.setSelectedIndex(0);
    }
}
