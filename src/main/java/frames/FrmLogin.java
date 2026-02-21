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
public class FrmLogin extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;
    private JLabel LblTelefono;
    private JTextField TxtTelefono;
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

        // Teléfono
        LblTelefono = new JLabel("Teléfono *");
        LblTelefono.setBounds(100, 120, 200, 25);
        PnlPrincipal.add(LblTelefono);

        TxtTelefono = new JTextField();
        TxtTelefono.setBounds(100, 150, 300, 30);
        PnlPrincipal.add(TxtTelefono);

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

            String telefono = TxtTelefono.getText().trim();
            String contrasena = new String(PswContrasena.getPassword());

            if (telefono.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe completar todos los campos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Simulación
            if (telefono.equals("6441234567") && contrasena.equals("1234")) {
                new FrmMenuUsuario();
                dispose();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Teléfono o contraseña incorrectos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
