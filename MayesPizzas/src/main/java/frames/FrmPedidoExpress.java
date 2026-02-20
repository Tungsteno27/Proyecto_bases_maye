/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;

import javax.swing.*;
import java.awt.*;

/**
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

    public FrmPedidoExpress() {

        setTitle("Pedido Express - Maye´s Pizzas");
        setSize(600, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();
        aplicarEstilos();
        agregarEventos();

        setVisible(true);
    }

    private void inicializarComponentes() {

        PnlPrincipal = new JPanel();
        PnlPrincipal.setLayout(null);
        add(PnlPrincipal);

        LblTitulo = new JLabel("PEDIDO EXPRESS");
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        LblTitulo.setBounds(150, 30, 300, 40);
        PnlPrincipal.add(LblTitulo);

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

    private void aplicarEstilos() {

        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnAgregar.setBackground(new Color(255, 140, 0));
        BtnAgregar.setForeground(Color.WHITE);

        BtnIrAlCarrito.setBackground(new Color(255, 140, 0));
        BtnIrAlCarrito.setForeground(Color.WHITE);

        BtnRegresar.setBackground(new Color(200, 0, 0));
        BtnRegresar.setForeground(Color.WHITE);
    }

    private void agregarEventos() {

        BtnRegresar.addActionListener(e -> {
            new FrmPantallaBienvenida();
            dispose();
        });

        BtnAgregar.addActionListener(e -> {

            String seleccion = ListaPizzas.getSelectedValue();

            if (seleccion == null) {
                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar una pizza",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "Agregado: " + seleccion,
                    "Carrito",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        BtnIrAlCarrito.addActionListener(e -> {
            //aqui va el carrito del express
        });
    }
}
