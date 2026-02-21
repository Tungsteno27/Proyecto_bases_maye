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

public class FrmProgramadoMenu extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;

    private JList<String> ListaMenu;
    private JScrollPane ScrollMenu;
    private DefaultListModel<String> modeloMenu;

    private JButton BtnSeleccionar;
    private JButton BtnVerCarrito;
    private JButton BtnRegresar;

    public FrmProgramadoMenu() {

        setTitle("Pedido Programado - Menú");
        setSize(650, 600);
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

        LblTitulo = new JLabel("MENÚ - PEDIDO PROGRAMADO");
        LblTitulo.setBounds(120, 30, 400, 40);
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        PnlPrincipal.add(LblTitulo);

        modeloMenu = new DefaultListModel<>();

        modeloMenu.addElement("Pizza Pepperoni Grande - $180");
        modeloMenu.addElement("Pizza Queso Grande - $160");
        modeloMenu.addElement("Pizza Hawaiana Grande - $190");
        modeloMenu.addElement("Pizza Mexicana Grande - $200");
        modeloMenu.addElement("Pizza Tolano - $220");

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

    private void agregarEventos() {

        BtnRegresar.addActionListener(e -> {
            new FrmMenuUsuario();
            dispose();
        });

        BtnSeleccionar.addActionListener(e -> {

            String seleccion = ListaMenu.getSelectedValue();

            if (seleccion == null) {
                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar un producto",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            new FrmProgramadoPedido(seleccion);
            dispose();
        });

        BtnVerCarrito.addActionListener(e -> {
            new FrmProgramadoCarrito();
            dispose();
        });
    }
}

