/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Dell PC
 */
public class FrmMisPedidos extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;
    private JComboBox<String> CmbFiltro;
    private JButton BtnFiltrar;
    private JTextArea TxtAreaPedidos;
    private JScrollPane ScrollPedidos;
    private JButton BtnRegresar;

    public FrmMisPedidos() {

        setTitle("Mis pedidos - Maye´s Pizzas");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializarComponentes();
        aplicarEstilos();
        agregarEventos();

        setVisible(true);
    }

    private void inicializarComponentes() {

        PnlPrincipal = new JPanel();
        PnlPrincipal.setLayout(null);
        add(PnlPrincipal);

        LblTitulo = new JLabel("MIS PEDIDOS");
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        LblTitulo.setBounds(150, 30, 300, 40);
        PnlPrincipal.add(LblTitulo);

        // Filtro
        CmbFiltro = new JComboBox<>();
        CmbFiltro.addItem("Programados");
        CmbFiltro.addItem("Express");
        CmbFiltro.setBounds(150, 100, 200, 30);
        PnlPrincipal.add(CmbFiltro);

        BtnFiltrar = new JButton("Filtrar");
        BtnFiltrar.setBounds(370, 100, 100, 30);
        PnlPrincipal.add(BtnFiltrar);

        // Área de pedidos
        TxtAreaPedidos = new JTextArea();
        TxtAreaPedidos.setEditable(false);

        ScrollPedidos = new JScrollPane(TxtAreaPedidos);
        ScrollPedidos.setBounds(80, 160, 440, 300);
        PnlPrincipal.add(ScrollPedidos);

        // Botón regresar
        BtnRegresar = new JButton("Regresar");
        BtnRegresar.setBounds(230, 500, 140, 35);
        PnlPrincipal.add(BtnRegresar);
    }

    private void aplicarEstilos() {

        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnFiltrar.setBackground(new Color(255, 140, 0));
        BtnFiltrar.setForeground(Color.WHITE);

        BtnRegresar.setBackground(new Color(200, 0, 0));
        BtnRegresar.setForeground(Color.WHITE);
    }

    private void agregarEventos() {

        BtnRegresar.addActionListener(e -> {
            new FrmMenuUsuario();
            dispose();
        });

        BtnFiltrar.addActionListener(e -> {

            String filtro = (String) CmbFiltro.getSelectedItem();

            TxtAreaPedidos.setText(""); // limpiar

            if (filtro.equals("Programados")) {

                TxtAreaPedidos.append("27/11/25 - Pizza Pepperoni x 2- $300\n");
                TxtAreaPedidos.append("21/11/25 - Pizza Hawaiana - $250\n");
                TxtAreaPedidos.append("13/11/25 - Pizza Queso - $125\n");

            } else {

                TxtAreaPedidos.append("02/12/25 - Pizza Express Pepperoni - $150\n");
                TxtAreaPedidos.append("30/11/25 - Pizza Express Queso - $125\n");
            }
        });
    }
}
