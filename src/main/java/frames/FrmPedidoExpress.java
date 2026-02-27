/*
 * click nbfsnbhostsystemfilesystemtemplateslicenseslicense-defaulttxt to change this license
 * click nbfsnbhostsystemfilesystemtemplatesclassesclassjava to edit this template
 */
package frames;

import javax.swing.*;
import java.awt.*;

/**
 * representa la pantalla para realizar pedidos express en el sistema
 * permite a los clientes seleccionar productos del menu rapido y agregarlos
 * a un carrito de compras temporal sin necesidad de registrarse
 * forma parte de la capa de presentacion
 *
 * @author Noelia E.N.
 */
public class FrmPedidoExpress extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;

    private JList<String> ListaPizzas;
    private JScrollPane ScrollMenu;

    private JButton BtnAgregar;
    private JButton BtnIrAlCarrito;
    private JButton BtnRegresar;

    private DefaultListModel<String> modeloLista;

    /**
     * constructor principal de la ventana de pedido express
     * establece las dimensiones el comportamiento de cierre
     * y llama a los metodos para construir la interfaz
     */
    public FrmPedidoExpress() {

        setTitle("Pedido Express - Mayes Pizzas");
        setSize(600, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();
        aplicarEstilos();
        agregarEventos();

        setVisible(true);
    }

    /**
     * inicializa y posiciona los elementos visuales de la ventana
     * configura la lista de productos disponibles y los botones de accion
     */
    private void inicializarComponentes() {

        PnlPrincipal = new JPanel();
        PnlPrincipal.setLayout(null);
        add(PnlPrincipal);

        LblTitulo = new JLabel("PEDIDO EXPRESS");
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        LblTitulo.setBounds(150, 30, 300, 40);
        PnlPrincipal.add(LblTitulo);

        // se agregan pizzas de ejemplo a la lista visual
        modeloLista = new DefaultListModel<>();
        modeloLista.addElement("Pizza Pepperoni - $150");
        modeloLista.addElement("Pizza Queso - $125");
        modeloLista.addElement("Pizza Hawaiana - $160");

        ListaPizzas = new JList<>(modeloLista);
        ScrollMenu = new JScrollPane(ListaPizzas);
        ScrollMenu.setBounds(120, 100, 350, 200);
        PnlPrincipal.add(ScrollMenu);

        BtnAgregar = new JButton("Agregar al carrito");
        BtnAgregar.setBounds(200, 320, 200, 35);
        PnlPrincipal.add(BtnAgregar);

        BtnIrAlCarrito = new JButton("Ir al carrito");
        BtnIrAlCarrito.setBounds(200, 370, 200, 35);
        PnlPrincipal.add(BtnIrAlCarrito);

        BtnRegresar = new JButton("Regresar");
        BtnRegresar.setBounds(200, 430, 200, 35);
        PnlPrincipal.add(BtnRegresar);
    }

    /**
     * aplica la paleta de colores de la pizzeria a los botones y al fondo
     */
    private void aplicarEstilos() {

        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnAgregar.setBackground(new Color(255, 140, 0));
        BtnAgregar.setForeground(Color.WHITE);

        BtnIrAlCarrito.setBackground(new Color(255, 140, 0));
        BtnIrAlCarrito.setForeground(Color.WHITE);

        BtnRegresar.setBackground(new Color(200, 0, 0));
        BtnRegresar.setForeground(Color.WHITE);
    }

    /**
     * define el comportamiento interactivo de los botones
     * valida selecciones en la lista y maneja la navegacion
     */
    private void agregarEventos() {

        // accion para volver al menu de inicio
        BtnRegresar.addActionListener(e -> {
            new FrmPantallaBienvenida();
            dispose();
        });

        // valida si se eligio un elemento de la lista antes de agregarlo
        BtnAgregar.addActionListener(e -> {

            String seleccion = ListaPizzas.getSelectedValue();

            if (seleccion == null) {
                JOptionPane.showMessageDialog(this,
                        "debe seleccionar una pizza",
                        "error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "agregado: " + seleccion,
                    "carrito",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // navegacion hacia el resumen del pedido
        BtnIrAlCarrito.addActionListener(e -> {
            // aqui va el carrito del express
        });
    }
}
