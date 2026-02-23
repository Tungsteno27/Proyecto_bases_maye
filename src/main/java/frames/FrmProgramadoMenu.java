/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package frames;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import negocio.BOs.IProductoBO;
import negocio.BOs.ProductoBO;
import negocio.DTOs.PedidoDTO;
import negocio.DTOs.ProductoDTO;
import negocio.Exception.NegocioException;
import persistencia.DAOs.ProductoDAO;
import persistencia.conexion.ConexionBD;

/**
 *
 * @author Noelia E.N.
 */

public class FrmProgramadoMenu extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;
    private JList<String> ListaMenu;
    private JScrollPane ScrollMenu;
    private DefaultListModel<String> modeloMenu;
    private JButton BtnSeleccionar, BtnVerCarrito, BtnRegresar;
    // lo nuevo
    private PedidoDTO pedidoActual;
    private List<ProductoDTO> listaProductosBD; 
    // la list para guardar lo que traiga la base de datos
    private IProductoBO productoBO;

    // Constructor para crear un carrito nuevo asi como tolanito
    public FrmProgramadoMenu() {
        this.pedidoActual = new PedidoDTO();
        this.pedidoActual.setProductos(new ArrayList<>());
        inicializarVentana();
    }

    // Constructor cuando regresas del Carrito bruh
    public FrmProgramadoMenu(PedidoDTO pedidoExistente) {
        this.pedidoActual = pedidoExistente;
        inicializarVentana();
    }
    
    /**
     * logica noelia encinas
     */
    private void inicializarVentana() {
        setTitle("Pedido Programado - Menú");
        setSize(650, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ConexionBD conexion = new ConexionBD();
        productoBO = new ProductoBO(new ProductoDAO(conexion));

        inicializarComponentes();
        aplicarEstilos();
        cargarMenuDesdeBD(); 
        agregarEventos();

        setVisible(true);
    }

    private void inicializarComponentes() {
        PnlPrincipal = new JPanel();
        PnlPrincipal.setLayout(null);
        add(PnlPrincipal);

        LblTitulo = new JLabel("MENÚ - PEDIDO PROGRAMADO");
        LblTitulo.setBounds(120, 30, 400, 40);
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        PnlPrincipal.add(LblTitulo);

        modeloMenu = new DefaultListModel<>();
        ListaMenu = new JList<>(modeloMenu);
        ScrollMenu = new JScrollPane(ListaMenu);
        ScrollMenu.setBounds(100, 100, 450, 250);
        PnlPrincipal.add(ScrollMenu);

        BtnSeleccionar = new JButton("Seleccionar");
        BtnSeleccionar.setBounds(120, 380, 180, 40);
        PnlPrincipal.add(BtnSeleccionar);

        BtnVerCarrito = new JButton("Ver Carrito");
        BtnVerCarrito.setBounds(340, 380, 180, 40);
        PnlPrincipal.add(BtnVerCarrito);

        BtnRegresar = new JButton("Regresar");
        BtnRegresar.setBounds(230, 450, 180, 35);
        PnlPrincipal.add(BtnRegresar);
    }

    private void aplicarEstilos() {
        PnlPrincipal.setBackground(new Color(255, 248, 220));
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        BtnSeleccionar.setBackground(new Color(255, 140, 0));
        BtnSeleccionar.setForeground(Color.WHITE);
        BtnVerCarrito.setBackground(new Color(255, 140, 0));
        BtnVerCarrito.setForeground(Color.WHITE);
        BtnRegresar.setBackground(new Color(200, 0, 0));
        BtnRegresar.setForeground(Color.WHITE);
    }

    private void cargarMenuDesdeBD() {
        try {
            listaProductosBD = productoBO.obtenerProductosDisponibles();
            modeloMenu.clear();
            for (ProductoDTO prod : listaProductosBD) {
                modeloMenu.addElement(prod.getNombre() + " - $" + prod.getPrecio());
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar el menú: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarEventos() {
        BtnRegresar.addActionListener(e -> {
            // new FrmMenuUsuario(); 
            dispose();
        });

        BtnSeleccionar.addActionListener(e -> {
            int index = ListaMenu.getSelectedIndex();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un producto", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ProductoDTO productoElegido = listaProductosBD.get(index);
            new FrmProgramadoPedido(productoElegido, pedidoActual);
            dispose();
        });

        BtnVerCarrito.addActionListener(e -> {
            new FrmProgramadoCarrito(pedidoActual);
            dispose();
        });
    }
}

