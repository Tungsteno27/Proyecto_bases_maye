/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;

import javax.swing.*;
import java.awt.*;
import negocio.DTOs.UsuarioDTO;


/**
 * Representa el menú principal del usuario dentro del sistema.
 * <p>
 * Esta ventana se muestra después de que el usuario inicia sesión
 * correctamente. Desde aquí se puede navegar a las distintas funcionalidades
 * del sistema, como crear un pedido programado, consultar pedidos, gestionar
 * perfil y administrar teléfonos registrados.
 * </p>
 * Pertenece a la capa de presentación.
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

    private UsuarioDTO sesionActual;

    /**
     * Constructor de la clase FrmMenuUsuario. Inicializa la ventana, sus
     * componentes, estilos y eventos asociados.
     */
    public FrmMenuUsuario(UsuarioDTO sesionActual) {
        this.sesionActual = sesionActual;

        setTitle("Menú - Maye´s Pizzas");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();
        aplicarEstilos();
        agregarEventos();

        setVisible(true);
    }

    /**
     * Inicializa y posiciona todos los componentes gráficos dentro del panel
     * principal.
     */
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

    /**
     * Aplica estilos visuales personalizados a los componentes de la ventana.
     */
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

     /**
     * Asocia los eventos de acción a los botones de navegación del menú.
     */
     private void agregarEventos() {

        BtnCerrarSesion.addActionListener(e -> {
            new FrmPantallaBienvenida();
            dispose();
        });

        BtnCrearPedidoProgramado.addActionListener(e -> {
            new FrmProgramadoMenu(sesionActual); //Aquí lo que hice fue meterle el usuario en cuestión (W)
            dispose();
        });

        BtnMisPedidos.addActionListener(e -> {
            new FrmMisPedidos(sesionActual); 
            dispose();
        });

        BtnMiPerfil.addActionListener(e -> {
            new FrmMiPerfil(sesionActual);
            dispose();
        });

        BtnMisTelefonos.addActionListener(e -> {
            new FrmMisTelefonos(sesionActual);
            dispose();
        });
    }
}
