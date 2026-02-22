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
 *
 * @author Noelia E.N.
 */
public class FrmLogin extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;
    //Se cambió el login de teléfono por correo electrónico
    private JLabel LblCorreo;
    private JTextField TxtCorreo;
    private JLabel LblContrasena;
    private JPasswordField PswContrasena;
    private JButton BtnIngresar;
    private JButton BtnRegresar;

    public FrmLogin() {

        setTitle("Login - Maye´s Pizzas");
        setSize(500, 450);
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

        // Título
        LblTitulo = new JLabel("INICIAR SESIÓN");
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        LblTitulo.setBounds(100, 40, 300, 40);
        PnlPrincipal.add(LblTitulo);

        //Correo
        LblCorreo = new JLabel("Correo electrónico *");
        LblCorreo.setBounds(100, 120, 200, 25);
        PnlPrincipal.add(LblCorreo);
        TxtCorreo = new JTextField();
        TxtCorreo.setBounds(100, 150, 300, 30);
        PnlPrincipal.add(TxtCorreo);

        // Contraseña
        LblContrasena = new JLabel("Contraseña *");
        LblContrasena.setBounds(100, 200, 200, 25);
        PnlPrincipal.add(LblContrasena);

        PswContrasena = new JPasswordField();
        PswContrasena.setBounds(100, 230, 300, 30);
        PnlPrincipal.add(PswContrasena);

        // Botones
        BtnIngresar = new JButton("Ingresar");
        BtnIngresar.setBounds(100, 300, 140, 35);
        PnlPrincipal.add(BtnIngresar);

        BtnRegresar = new JButton("Regresar");
        BtnRegresar.setBounds(260, 300, 140, 35);
        PnlPrincipal.add(BtnRegresar);
    }

    private void aplicarEstilos() {

        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnIngresar.setBackground(new Color(255, 140, 0));
        BtnIngresar.setForeground(Color.WHITE);

        BtnRegresar.setBackground(new Color(200, 0, 0));
        BtnRegresar.setForeground(Color.WHITE);
    }

    private void agregarEventos() {

        BtnRegresar.addActionListener(e -> {
            new FrmPantallaBienvenida();
            dispose();
        });

        BtnIngresar.addActionListener(e -> {
            String correo = TxtCorreo.getText().trim();
            String contrasena = new String(PswContrasena.getPassword());

            if (correo.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe completar todos los campos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Masomenos así se le va a hacer en cada caso que se ocupe ingresar cosas e insertar o modificar la base de datos
            try {
                ConexionBD conexionBD = new ConexionBD();
                IUsuarioDAO usuarioDAO = new UsuarioDAO(conexionBD);
                IUsuarioBO usuarioBO = new UsuarioBO(usuarioDAO);

                UsuarioDTO sesion = usuarioBO.iniciarSesion(correo, contrasena);

                if (sesion.getRol() == RolUsuario.CLIENTE) {
                    //Tal vez sea buena idea cambiar el constructor para que reciba el id del usuario, así manejamos diferentes perfiles
                    new FrmMenuUsuario();
                } else {
                    //AQUÍ AGREGAR LUEGO EL MENÚ DEL EMPLEADO PLOXXXXXXXXXXXXXXXXXXXXX
                }
                dispose();
                
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
