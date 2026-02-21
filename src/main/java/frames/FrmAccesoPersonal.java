/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;
import javax.swing.*;
import java.awt.*;

/**
 * Frame para el acceso de los empleados
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

        LblUsuario = new JLabel("Usuario:");
        LblUsuario.setBounds(60, 130, 100, 25);
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

        // Eventos
        BtnIngresar.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Todo fine");
            // de aca va el FrmPanelPersonal
        });

        BtnVolver.addActionListener(e -> {
            new FrmPantallaBienvenida();
            dispose();
        });

        setVisible(true);
    }
}

