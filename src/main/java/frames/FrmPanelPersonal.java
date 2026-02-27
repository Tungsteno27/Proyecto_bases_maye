/*
 * click nbfsnbhostsystemfilesystemtemplateslicenseslicense-defaulttxt to change this license
 * click nbfsnbhostsystemfilesystemtemplatesclassesclassjava to edit this template
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
 * panel de control principal para el personal autorizado
 * sirve como menu central para navegar entre los distintos
 * modulos de gestion de la pizzeria como productos clientes y entregas
 *
 * @author julian izaguirre
 */
public class FrmPanelPersonal extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;
    private JButton BtnGestionProductos;
    private JButton BtnGestionClientes;
    private JButton BtnGestionEntregas; // nuevo
    private JButton BtnCerrarSesion;

    private UsuarioDTO sesionActual;

    /**
     * constructor del panel de control
     * recibe la sesion del usuario para mostrar su rol en el titulo y
     * pasarlo a las siguientes ventanas para mantener la seguridad
     * * @param sesion el usuario actual con sus permisos y datos
     */
    public FrmPanelPersonal(UsuarioDTO sesion) {
        this.sesionActual = sesion;

        setTitle("Panel de Control - Maye´s Pizzas");
        setSize(500, 500); // se aumento un poco para acomodar el boton nuevo
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        inicializarComponentes();
        aplicarEstilos();
        agregarEventos();
        
        setVisible(true);
    }

    /**
     * crea y posiciona los botones y etiquetas en el panel
     * acomoda las opciones de gestion en forma de lista vertical
     */
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

        // nuevo se agrega entre clientes y cerrar sesion
        BtnGestionEntregas = new JButton("Gestión de Entregas");
        BtnGestionEntregas.setBounds(125, 240, 250, 40);
        PnlPrincipal.add(BtnGestionEntregas);

        // se bajo de posicion en y para que no se encime con el boton nuevo
        BtnCerrarSesion = new JButton("Cerrar sesión");
        BtnCerrarSesion.setBounds(125, 340, 250, 40);
        PnlPrincipal.add(BtnCerrarSesion);
    }

    /**
     * asigna los colores de fondo y letras a la ventana y los botones
     * para seguir la paleta de colores institucional del sistema
     */
    private void aplicarEstilos() {
        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnGestionProductos.setBackground(new Color(183, 28, 28));
        BtnGestionProductos.setForeground(Color.WHITE);

        BtnGestionClientes.setBackground(new Color(183, 28, 28));
        BtnGestionClientes.setForeground(Color.WHITE);

        // estilo del boton nuevo
        BtnGestionEntregas.setBackground(new Color(183, 28, 28));
        BtnGestionEntregas.setForeground(Color.WHITE);

        BtnCerrarSesion.setBackground(new Color(50, 50, 50));
        BtnCerrarSesion.setForeground(Color.WHITE);
    }

    /**
     * vincula las acciones de clic a cada boton del menu
     * cierra esta ventana y abre el formulario correspondiente a la eleccion
     */
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

        // evento del modulo nuevo
        BtnGestionEntregas.addActionListener(e -> {
            new FrmGestionEntregas(sesionActual);
            dispose();
        });
    }

}
