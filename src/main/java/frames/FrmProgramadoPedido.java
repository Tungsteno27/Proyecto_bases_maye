/*
 * click nbfsnbhostsystemfilesystemtemplateslicenseslicense-defaulttxt to change this license
 * click nbfsnbhostsystemfilesystemtemplatesclassesclassjava to edit this template
 */
package frames;

import javax.swing.*;
import java.awt.*;
import negocio.DTOs.PedidoDTO;
import negocio.DTOs.ProductoCarritoDTO;
import negocio.DTOs.ProductoDTO;

/**
 * clase que representa la ventana de personalizacion de un producto
 * permite al cliente seleccionar la cantidad agregar notas especiales y ver un
 * resumen del costo antes de agregar el item a su carrito de compras
 *
 * @author Noelia E.N.
 */
public class FrmProgramadoPedido extends JFrame {

    private JLabel LblTitulo, LblProducto,LblCantidad, LblNotas, LblResumen;
    private JSpinner SpnCantidad;
    private JTextArea TxtNotas, TxtResumen;
    private JButton BtnAgregar, BtnRegresar, BtnVerCarrito;

    // dtos reales del negocio para mantener la informacion del carrito
    private ProductoDTO productoSeleccionado;
    private PedidoDTO pedidoActual;

    /**
     * constructor de la ventana de personalizacion de pedido
     * recibe el producto que el cliente quiere y el carrito actual
     * ajusta el titulo dependiendo de si el flujo es express o programado
     * * @param producto el producto especifico seleccionado en el menu
     * * @param pedido el pedido en curso con la lista de productos acumulada
     */
    public FrmProgramadoPedido(ProductoDTO producto, PedidoDTO pedido) {

        this.productoSeleccionado = producto;
        this.pedidoActual = pedido;
        
        // cambia el titulo de la ventana segun el tipo de pedido
        if("EXPRESS".equals(pedidoActual.getTipo())){
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

    /**
     * construye y posiciona los elementos de la interfaz grafica
     * carga la informacion basica del producto como su nombre y precio
     * y configura los controles de entrada de texto y seleccion
     */
    private void inicializarComponentes() {
        LblTitulo = new JLabel("PERSONALIZAR PEDIDO");
        LblTitulo.setBounds(150, 20, 400, 30);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(LblTitulo);

        // se arma la etiqueta con los datos del producto
        LblProducto = new JLabel("Producto: " + productoSeleccionado.getNombre()
                + " | Tamano: " + productoSeleccionado.getTamanio()
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

        BtnRegresar = new JButton("Regresar al Menu");
        BtnRegresar.setBounds(430, 430, 150, 40);
        BtnRegresar.setBackground(new Color(200, 0, 0));
        BtnRegresar.setForeground(Color.WHITE);
        add(BtnRegresar);
    }

    /**
     * define el comportamiento interactivo de los botones y el selector de cantidad
     * maneja la insercion del producto al objeto del pedido y la navegacion
     * entre las ventanas del flujo de compra
     */
    private void agregarEventos() {

        BtnAgregar.addActionListener(e -> {
            
            // creamos el renglon del carrito y lo metemos al objeto general del pedido
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
                    "producto agregado al carrito",
                    "exito",
                    JOptionPane.INFORMATION_MESSAGE);
            TxtNotas.setText(""); // limpiamos las notas por si el usuario quiere agregar otro producto
            SpnCantidad.setValue(1);
            actualizarResumen(); 
        });

        // navegacion de retroceso al catalogo
        BtnRegresar.addActionListener(e -> {
            new FrmProgramadoMenu(pedidoActual); 
            dispose();
        });

        // avance hacia la ventana de pago o revision del carrito
        BtnVerCarrito.addActionListener(e -> {
            new FrmProgramadoCarrito(pedidoActual); 
            dispose();
        });
        
        /**
         * listener dinamico que se ejecuta cada que el spinner de cantidad cambia
         * recalcula el subtotal al instante
         */
        SpnCantidad.addChangeListener(e -> {
            actualizarResumen();
        });

    }

    /**
     * calcula el total multiplicando el precio unitario del producto seleccionado
     * por la cantidad elegida en el spinner y plasma el desglose en el area de texto inferior
     */
    private void actualizarResumen() {

        int cantidad = (int) SpnCantidad.getValue();
        double totalItem = productoSeleccionado.getPrecio() * cantidad;

        TxtResumen.setText(
                "Producto: " + productoSeleccionado.getNombre()
                + "\nTamano: " + productoSeleccionado.getTamanio()
                + "\nPrecio unitario: $" + productoSeleccionado.getPrecio()
                + "\nCantidad: " + cantidad
                + "\nTotal: $" + totalItem
        );
    }
}
