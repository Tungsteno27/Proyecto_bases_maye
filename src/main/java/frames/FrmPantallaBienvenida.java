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
public class FrmPantallaBienvenida extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblLogo;
    private JButton BtnIniciarSesion;
    private JButton BtnRegistrarse;
    private JButton BtnPedidoExpress;
    private JButton BtnAccesoPersonal;
    private JButton BtnSalir;
    
    
    public FrmPantallaBienvenida() {

        // Configuración básica del frame
        setTitle("Maye's Pizzas");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializarComponentes();
        aplicarEstilos();
        agregarEventos();

        setVisible(true);
    }

    private void inicializarComponentes() {

        // Panel principal
        PnlPrincipal = new JPanel();
        PnlPrincipal.setLayout(null);
        add(PnlPrincipal);

        // Logo
        LblLogo = new JLabel("MAYE'S PIZZAS");
        LblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        LblLogo.setBounds(50, 40, 400, 50);
        PnlPrincipal.add(LblLogo);

        // Botones
        BtnIniciarSesion = new JButton("Iniciar Sesión");
        BtnIniciarSesion.setBounds(150, 120, 200, 35);
        PnlPrincipal.add(BtnIniciarSesion);

        BtnRegistrarse = new JButton("Registrarse");
        BtnRegistrarse.setBounds(150, 170, 200, 35);
        PnlPrincipal.add(BtnRegistrarse);

        BtnPedidoExpress = new JButton("Pedido Express");
        BtnPedidoExpress.setBounds(150, 220, 200, 35);
        PnlPrincipal.add(BtnPedidoExpress);

        BtnAccesoPersonal = new JButton("Acceso Personal");
        BtnAccesoPersonal.setBounds(150, 270, 200, 35);
        PnlPrincipal.add(BtnAccesoPersonal);

        BtnSalir = new JButton("Salir");
        BtnSalir.setBounds(150, 320, 200, 35);
        PnlPrincipal.add(BtnSalir);
    }

    /**
     * Método para ponerle color a los componentes
     */
    private void aplicarEstilos() {

        // Fondo cremita
        PnlPrincipal.setBackground(new Color(255, 248, 220));

        // Botones naranjas
        BtnIniciarSesion.setBackground(new Color(255, 140, 0));
        BtnIniciarSesion.setForeground(Color.WHITE);

        BtnRegistrarse.setBackground(new Color(255, 140, 0));
        BtnRegistrarse.setForeground(Color.WHITE);

        BtnPedidoExpress.setBackground(new Color(255, 140, 0));
        BtnPedidoExpress.setForeground(Color.WHITE);

        BtnAccesoPersonal.setBackground(new Color(255, 140, 0));
        BtnAccesoPersonal.setForeground(Color.WHITE);

        // Botón rojo
        BtnSalir.setBackground(new Color(200, 0, 0));
        BtnSalir.setForeground(Color.WHITE);
    }

    private void agregarEventos() {

        BtnSalir.addActionListener(e -> System.exit(0));

        BtnIniciarSesion.addActionListener(e -> {
            new FrmLogin();
            dispose();
        });
        
        BtnRegistrarse.addActionListener(e -> {
            new FrmRegistro();
            dispose();
        });
        
        BtnAccesoPersonal.addActionListener(e -> {
            new FrmAccesoPersonal();
            dispose();
        });
        
        BtnPedidoExpress.addActionListener(e -> {
            new FrmPedidoExpress();
            dispose();
        });
        
    }

    public static void main(String[] args) {
        new FrmPantallaBienvenida();
    }
}
