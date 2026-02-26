/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import negocio.DTOs.UsuarioDTO;

/**
 *
 * @author julian izaguirre
 */
public class FrmPanelPersonal extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;
    private JButton BtnGestionProductos;
    private JButton BtnGestionClientes;
    private JButton BtnGestionEntregas; // NUEVO
    private JButton BtnCerrarSesion;

    private UsuarioDTO sesionActual;

    public FrmPanelPersonal(UsuarioDTO sesion) {
        this.sesionActual = sesion;

        setTitle("Panel de Control - Maye´s Pizzas");
        setSize(500, 500); // Se aumentó un poco para acomodar el botón nuevo
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

        LblTitulo = new JLabel("PANEL DE " + sesionActual.getRol().name());
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        LblTitulo.setBounds(100, 40, 300, 40);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        PnlPrincipal.add(LblTitulo);

        BtnGestionProductos = new JButton("Gestión de Productos");
        BtnGestionProductos.setBounds(125, 120, 250, 40);
        PnlPrincipal.add(BtnGestionProductos);

        BtnGestionClientes = new JButton("Gestión de Clientes");
        BtnGestionClientes.setBounds(125, 180, 250, 40);
        PnlPrincipal.add(BtnGestionClientes);

        // NUEVO - se agrega entre Clientes y Cerrar Sesión
        BtnGestionEntregas = new JButton("Gestión de Entregas");
        BtnGestionEntregas.setBounds(125, 240, 250, 40);
        PnlPrincipal.add(BtnGestionEntregas);

        // Se bajó de y=280 a y=340 para que no se encime con el botón nuevo
        BtnCerrarSesion = new JButton("Cerrar sesión");
        BtnCerrarSesion.setBounds(125, 340, 250, 40);
        PnlPrincipal.add(BtnCerrarSesion);
    }

    private void aplicarEstilos() {
        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnGestionProductos.setBackground(new Color(183, 28, 28));
        BtnGestionProductos.setForeground(Color.WHITE);

        BtnGestionClientes.setBackground(new Color(183, 28, 28));
        BtnGestionClientes.setForeground(Color.WHITE);

        // NUEVO
        BtnGestionEntregas.setBackground(new Color(183, 28, 28));
        BtnGestionEntregas.setForeground(Color.WHITE);

        BtnCerrarSesion.setBackground(new Color(50, 50, 50));
        BtnCerrarSesion.setForeground(Color.WHITE);
    }

    private void agregarEventos() {

        BtnCerrarSesion.addActionListener(e -> {
            new FrmPantallaBienvenida();
            dispose();
        });

        BtnGestionProductos.addActionListener(e -> {
            new FrmGestionProductos(sesionActual);
            dispose();
        });

        BtnGestionClientes.addActionListener(e -> {
            new FrmGestionClientes(sesionActual);
            dispose();
        });

        // Nuevo
        BtnGestionEntregas.addActionListener(e -> {
            new FrmGestionEntregas(sesionActual);
            dispose();
        });
    }
}
