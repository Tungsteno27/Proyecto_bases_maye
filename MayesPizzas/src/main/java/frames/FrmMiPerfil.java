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

public class FrmMiPerfil extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;

    private JLabel LblNombreCompleto;
    private JTextField TxtNombreCompleto;

    private JLabel LblFechaNacimiento;
    private JTextField TxtFechaNacimiento;

    private JLabel LblDomicilio;
    private JTextField TxtDomicilio;

    private JLabel LblContrasena;
    private JPasswordField PswContrasena;

    private JLabel LblConfirmarContrasena;
    private JPasswordField PswConfirmarContrasena;

    private JLabel LblTelefono;
    private JTextField TxtTelefono;

    private JButton BtnGuardar;
    private JButton BtnRegresar;

    public FrmMiPerfil() {

        setTitle("Mi perfil - Maye´s Pizzas");
        setSize(550, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializarComponentes();
        aplicarEstilos();
        agregarEventos();

        setVisible(true);

        cargarDatosSimulados();
    }

    private void inicializarComponentes() {

        PnlPrincipal = new JPanel();
        PnlPrincipal.setLayout(null);
        add(PnlPrincipal);

        LblTitulo = new JLabel("MI PERFIL");
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        LblTitulo.setBounds(100, 30, 350, 40);
        PnlPrincipal.add(LblTitulo);

        // Nombre
        LblNombreCompleto = new JLabel("Nombre completo *");
        LblNombreCompleto.setBounds(100, 100, 300, 25);
        PnlPrincipal.add(LblNombreCompleto);

        TxtNombreCompleto = new JTextField();
        TxtNombreCompleto.setBounds(100, 130, 350, 30);
        PnlPrincipal.add(TxtNombreCompleto);

        // Fecha nacimiento
        LblFechaNacimiento = new JLabel("Fecha de nacimiento *");
        LblFechaNacimiento.setBounds(100, 180, 300, 25);
        PnlPrincipal.add(LblFechaNacimiento);

        TxtFechaNacimiento = new JTextField();
        TxtFechaNacimiento.setBounds(100, 210, 350, 30);
        PnlPrincipal.add(TxtFechaNacimiento);

        // Domicilio
        LblDomicilio = new JLabel("Domicilio *");
        LblDomicilio.setBounds(100, 260, 300, 25);
        PnlPrincipal.add(LblDomicilio);

        TxtDomicilio = new JTextField();
        TxtDomicilio.setBounds(100, 290, 350, 30);
        PnlPrincipal.add(TxtDomicilio);

        // Contraseña
        LblContrasena = new JLabel("Contraseña *");
        LblContrasena.setBounds(100, 340, 300, 25);
        PnlPrincipal.add(LblContrasena);

        PswContrasena = new JPasswordField();
        PswContrasena.setBounds(100, 370, 350, 30);
        PnlPrincipal.add(PswContrasena);

        // Confirmar contraseña
        LblConfirmarContrasena = new JLabel("Confirmar contraseña *");
        LblConfirmarContrasena.setBounds(100, 420, 300, 25);
        PnlPrincipal.add(LblConfirmarContrasena);

        PswConfirmarContrasena = new JPasswordField();
        PswConfirmarContrasena.setBounds(100, 450, 350, 30);
        PnlPrincipal.add(PswConfirmarContrasena);

        // Teléfono
        LblTelefono = new JLabel("Teléfono *");
        LblTelefono.setBounds(100, 500, 300, 25);
        PnlPrincipal.add(LblTelefono);

        TxtTelefono = new JTextField();
        TxtTelefono.setBounds(100, 530, 350, 30);
        PnlPrincipal.add(TxtTelefono);

        // Botones
        BtnGuardar = new JButton("Guardar");
        BtnGuardar.setBounds(120, 580, 140, 35);
        PnlPrincipal.add(BtnGuardar);

        BtnRegresar = new JButton("Regresar");
        BtnRegresar.setBounds(300, 580, 140, 35);
        PnlPrincipal.add(BtnRegresar);
    }

    private void aplicarEstilos() {

        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnGuardar.setBackground(new Color(255, 140, 0));
        BtnGuardar.setForeground(Color.WHITE);

        BtnRegresar.setBackground(new Color(200, 0, 0));
        BtnRegresar.setForeground(Color.WHITE);
    }

    private void agregarEventos() {

        BtnRegresar.addActionListener(e -> {
            new FrmMenuUsuario();
            dispose();
        });

        BtnGuardar.addActionListener(e -> {

            String nombre = TxtNombreCompleto.getText().trim();
            String fecha = TxtFechaNacimiento.getText().trim();
            String domicilio = TxtDomicilio.getText().trim();
            String telefono = TxtTelefono.getText().trim();

            String contrasena = new String(PswContrasena.getPassword());
            String confirmar = new String(PswConfirmarContrasena.getPassword());

            if (nombre.isEmpty() || fecha.isEmpty() || domicilio.isEmpty()
                    || telefono.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Debe completar todos los campos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!contrasena.equals(confirmar)) {
                JOptionPane.showMessageDialog(this,
                        "Las contraseñas no coinciden",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "Datos actualizados correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void cargarDatosSimulados() {

        TxtNombreCompleto.setText("Juan Pérez");
        TxtFechaNacimiento.setText("27/11/2000");
        TxtDomicilio.setText("Calle Falsa 123");
        TxtTelefono.setText("6441234567");
        PswContrasena.setText("1234");
        PswConfirmarContrasena.setText("1234");
    }
}
