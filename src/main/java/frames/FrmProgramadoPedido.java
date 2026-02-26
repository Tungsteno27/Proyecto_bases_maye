
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;

import javax.swing.*;
import java.awt.*;
import negocio.DTOs.PedidoDTO;
import negocio.DTOs.ProductoCarritoDTO;
import negocio.DTOs.ProductoDTO;

/**
 *
 * @author Noelia E.N.
 */
public class FrmProgramadoPedido extends JFrame {

    private JLabel LblTitulo, LblProducto,LblCantidad, LblNotas, LblResumen;
    private JSpinner SpnCantidad;
    private JTextArea TxtNotas, TxtResumen;
    private JButton BtnAgregar, BtnRegresar, BtnVerCarrito;

    // cambiamos el String de la noelia por los DTOs reales
    private ProductoDTO productoSeleccionado;
    private PedidoDTO pedidoActual;

    public FrmProgramadoPedido(ProductoDTO producto, PedidoDTO pedido) {

        this.productoSeleccionado = producto;
        this.pedidoActual = pedido;
        if(pedidoActual.getTipo().equals("EXPRESS")){
            setTitle("Personalizar Pedido Express");
        }else{
        setTitle("Personalizar Pedido Programado");
        }
        setSize(600, 520);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(255, 248, 220));

        inicializarComponentes();
        agregarEventos();
        actualizarResumen();

        setVisible(true);
    }

    private void inicializarComponentes() {
        LblTitulo = new JLabel("PERSONALIZAR PEDIDO");
        LblTitulo.setBounds(150, 20, 400, 30);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(LblTitulo);

        LblProducto = new JLabel("Producto: " + productoSeleccionado.getNombre()
                + " | Tamaño: " + productoSeleccionado.getTamanio()
                + " | Precio: $" + productoSeleccionado.getPrecio());
        LblProducto.setBounds(50, 70, 500, 25);
        add(LblProducto);

        LblCantidad = new JLabel("Cantidad:");
        LblCantidad.setBounds(50, 120, 100, 25);
        add(LblCantidad);

        SpnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        SpnCantidad.setBounds(130, 120, 60, 25);
        add(SpnCantidad);

        LblNotas = new JLabel("Notas adicionales (opcional):");
        LblNotas.setBounds(50, 170, 250, 25);
        add(LblNotas);

        TxtNotas = new JTextArea();
        TxtNotas.setLineWrap(true);
        JScrollPane scrollNotas = new JScrollPane(TxtNotas);
        scrollNotas.setBounds(50, 200, 480, 80);
        add(scrollNotas);

        LblResumen = new JLabel("Resumen:");
        LblResumen.setBounds(50, 300, 100, 25);
        add(LblResumen);

        TxtResumen = new JTextArea();
        TxtResumen.setEditable(false);
        JScrollPane scrollResumen = new JScrollPane(TxtResumen);
        scrollResumen.setBounds(50, 330, 480, 80);
        add(scrollResumen);

        BtnAgregar = new JButton("Agregar al carrito");
        BtnAgregar.setBounds(60, 430, 180, 40);
        BtnAgregar.setBackground(new Color(255, 140, 0));
        BtnAgregar.setForeground(Color.WHITE);
        add(BtnAgregar);

        BtnVerCarrito = new JButton("Ver carrito");
        BtnVerCarrito.setBounds(260, 430, 150, 40);
        BtnVerCarrito.setBackground(new Color(0, 128, 0));
        BtnVerCarrito.setForeground(Color.WHITE);
        add(BtnVerCarrito);

        BtnRegresar = new JButton("Regresar al Menú");
        BtnRegresar.setBounds(430, 430, 150, 40);
        BtnRegresar.setBackground(new Color(200, 0, 0));
        BtnRegresar.setForeground(Color.WHITE);
        add(BtnRegresar);
    }

    private void agregarEventos() {


        BtnAgregar.addActionListener(e -> {
            
            // Creamos el renglón del carrito y lo metemos a la mochila
            ProductoCarritoDTO item = new ProductoCarritoDTO();
            item.setProducto(productoSeleccionado);
            item.setCantidad((int) SpnCantidad.getValue());
            
            String notas = TxtNotas.getText().trim();
            if(notas.isEmpty()){
                item.setNotas(null);
            }else{
                item.setNotas(notas);
            }
            pedidoActual.getProductos().add(item);

            JOptionPane.showMessageDialog(this,
                    "Producto agregado al carrito",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            TxtNotas.setText(""); // Limpiamos las notas por si quiere otro
            SpnCantidad.setValue(1);
            actualizarResumen(); //AQUÍ PRÁCTICAMENTE NO HICE NADA DE NADA (W)
        });

        BtnRegresar.addActionListener(e -> {
            new FrmProgramadoMenu(pedidoActual); 
            dispose();
        });

        BtnVerCarrito.addActionListener(e -> {
            new FrmProgramadoCarrito(pedidoActual); 
            dispose();
        });
        
        /**
         * Método listener que se ejecuta cada que el spnCantidad cambia su valor
         */
        SpnCantidad.addChangeListener(e -> {
            actualizarResumen();
        });

    }

    private void actualizarResumen() {

        int cantidad = (int) SpnCantidad.getValue();
        double totalItem = productoSeleccionado.getPrecio() * cantidad;

        TxtResumen.setText(
                "Producto: " + productoSeleccionado.getNombre()
                + "\nTamaño: " + productoSeleccionado.getTamanio()
                + "\nPrecio unitario: $" + productoSeleccionado.getPrecio()
                + "\nCantidad: " + cantidad
                + "\nTotal: $" + totalItem
        );
    }
    
    
}
