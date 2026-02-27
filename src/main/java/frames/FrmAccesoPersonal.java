/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;
import javax.swing.*;
import java.awt.*;
import negocio.BOs.IUsuarioBO;
import negocio.BOs.UsuarioBO;
import negocio.DTOs.UsuarioDTO;
import negocio.Exception.NegocioException;
import persistencia.DAOs.IUsuarioDAO;
import persistencia.DAOs.UsuarioDAO;
import persistencia.Dominio.RolUsuario;
import persistencia.conexion.ConexionBD;

/**
 * Representa la ventana de acceso para el personal del sistemas.
 * <p>
 * Esta clase permite a los empleados ingresar mediante usuario y contraseña
 * para acceder al panel administrativo. También ofrece la opción de regresar a
 * la pantalla principal.
 * </p>
 * Forma parte de la capa de presentación.
 *
 * @author Noelia E.N.
 */
public class FrmAccesoPersonal extends JFrame {

    private JLabel LblTitulo;
    private JLabel LblUsuario;
    private JLabel LblContrasena;

    private JTextField TxtUsuario;
    private JPasswordField PswContrasena;

    private JButton BtnIngresar;
    private JButton BtnVolver;

    /**
     * Constructor de la clase FrmAccesoPersonal. Configura la ventana e
     * inicializa sus componentes visuales y eventos asociados.
     *
     * Inicializa y posiciona los componentes gráficos que conforman la interfaz
     * de acceso.
     *
     * Define los eventos asociados a los botones.
     * Permite validar el acceso del personal o regresar a la pantalla
     * principal.
     */
    public FrmAccesoPersonal() {

        setTitle("Acceso Personal - Maye´s Pizzas");
        setSize(400, 500);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(183, 28, 28));

        LblTitulo = new JLabel("Acceso Personal");
        LblTitulo.setBounds(90, 40, 250, 40);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        LblTitulo.setForeground(Color.WHITE);
        add(LblTitulo);

        // Se asume que el usuario entra con su correo
        LblUsuario = new JLabel("Correo electrónico:");
        LblUsuario.setBounds(60, 130, 150, 25);
        LblUsuario.setForeground(Color.WHITE);
        add(LblUsuario);

        TxtUsuario = new JTextField();
        TxtUsuario.setBounds(60, 160, 270, 35);
        add(TxtUsuario);

        LblContrasena = new JLabel("Contraseña:");
        LblContrasena.setBounds(60, 220, 100, 25);
        LblContrasena.setForeground(Color.WHITE);
        add(LblContrasena);

        PswContrasena = new JPasswordField();
        PswContrasena.setBounds(60, 250, 270, 35);
        add(PswContrasena);

        BtnIngresar = new JButton("Ingresar");
        BtnIngresar.setBounds(60, 320, 270, 40);
        BtnIngresar.setBackground(new Color(255, 167, 38)); // naranja pizza
        BtnIngresar.setFocusPainted(false);
        add(BtnIngresar);

        BtnVolver = new JButton("Volver");
        BtnVolver.setBounds(60, 380, 270, 35);
        BtnVolver.setBackground(Color.WHITE);
        BtnVolver.setFocusPainted(false);
        add(BtnVolver);

        agregarEventos();

        setVisible(true);
    }
    
    /**
     * define y agrupa todos los eventos de accion para los botones de la ventana
     * recolecta los datos ingresados hace validaciones basicas y se comunica
     * con la capa de negocio para iniciar sesion ademas bloquea el acceso a
     * los usuarios con rol de cliente
     */
    private void agregarEventos() {
        BtnIngresar.addActionListener(e -> {
            String correo = TxtUsuario.getText().trim();
            String contrasena = new String(PswContrasena.getPassword());

            if (correo.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // connection con la BD
                ConexionBD conexionBD = new ConexionBD();
                IUsuarioDAO usuarioDAO = new UsuarioDAO(conexionBD);
                IUsuarioBO usuarioBO = new UsuarioBO(usuarioDAO);

                UsuarioDTO sesion = usuarioBO.iniciarSesion(correo, contrasena);
                // validar que noid sea un cliente normal
                if (sesion.getRol() == RolUsuario.CLIENTE) {
                    JOptionPane.showMessageDialog(this, "Acceso denegado, esta area es exclusiva para personal autorizado como tolano", "Acceso Restringido", JOptionPane.WARNING_MESSAGE);
                    return; 
                }

                // abrimos el CRUD
                JOptionPane.showMessageDialog(this, "welcome w " + sesion.getRol().name());
                new FrmPanelPersonal(sesion); 
                dispose();

            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        BtnVolver.addActionListener(e -> {
            new FrmPantallaBienvenida();
            dispose();
        });
    }
}


