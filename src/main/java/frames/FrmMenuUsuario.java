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

public class FrmMenuUsuario extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;
    private JButton BtnCrearPedidoProgramado;
    private JButton BtnMisPedidos;
    private JButton BtnMiPerfil;
    private JButton BtnMisTelefonos;
    private JButton BtnCerrarSesion;

    public FrmMenuUsuario() {

        setTitle("Menú - Maye´s Pizzas");
        setSize(500, 550);
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

        LblTitulo = new JLabel("BIENVENIDO");
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        LblTitulo.setBounds(100, 40, 300, 40);
        PnlPrincipal.add(LblTitulo);

        BtnCrearPedidoProgramado = new JButton("Crear pedido programado");
        BtnCrearPedidoProgramado.setBounds(125, 120, 250, 40);
        PnlPrincipal.add(BtnCrearPedidoProgramado);

        BtnMisPedidos = new JButton("Mis pedidos");
        BtnMisPedidos.setBounds(125, 180, 250, 40);
        PnlPrincipal.add(BtnMisPedidos);

        BtnMiPerfil = new JButton("Mi perfil");
        BtnMiPerfil.setBounds(125, 240, 250, 40);
        PnlPrincipal.add(BtnMiPerfil);

        BtnMisTelefonos = new JButton("Mis teléfonos");
        BtnMisTelefonos.setBounds(125, 300, 250, 40);
        PnlPrincipal.add(BtnMisTelefonos);

        BtnCerrarSesion = new JButton("Cerrar sesión");
        BtnCerrarSesion.setBounds(125, 380, 250, 40);
        PnlPrincipal.add(BtnCerrarSesion);
    }

    private void aplicarEstilos() {

        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnCrearPedidoProgramado.setBackground(new Color(255, 140, 0));
        BtnCrearPedidoProgramado.setForeground(Color.WHITE);

        BtnMisPedidos.setBackground(new Color(255, 140, 0));
        BtnMisPedidos.setForeground(Color.WHITE);

        BtnMiPerfil.setBackground(new Color(255, 140, 0));
        BtnMiPerfil.setForeground(Color.WHITE);

        BtnMisTelefonos.setBackground(new Color(255, 140, 0));
        BtnMisTelefonos.setForeground(Color.WHITE);

        BtnCerrarSesion.setBackground(new Color(200, 0, 0));
        BtnCerrarSesion.setForeground(Color.WHITE);
    }

    private void agregarEventos() {

        BtnCerrarSesion.addActionListener(e -> {
            new FrmPantallaBienvenida();
            dispose();
        });

        BtnCrearPedidoProgramado.addActionListener(e -> {
            new FrmProgramadoMenu();
            dispose();
        });

        BtnMisPedidos.addActionListener(e -> {
            new FrmMisPedidos();
            dispose();
        });

        BtnMiPerfil.addActionListener(e -> {
            new FrmMiPerfil();
            dispose();
        });

        BtnMisTelefonos.addActionListener(e -> {
            new FrmMisTelefonos();
            dispose();
        });
    }
}
