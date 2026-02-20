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

public class FrmMisTelefonos extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;

    private JLabel LblNuevoTelefono;
    private JTextField TxtNuevoTelefono;

    private JLabel LblEtiqueta;
    private JTextField TxtEtiqueta;

    private JButton BtnAgregar;

    private JTextArea TxtAreaTelefonos;
    private JScrollPane ScrollTelefonos;

    private JButton BtnGuardar;
    private JButton BtnRegresar;

    public FrmMisTelefonos() {

        setTitle("Mis teléfonos - Maye´s Pizzas");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializarComponentes();
        aplicarEstilos();
        agregarEventos();

        setVisible(true);

        cargarTelefonosFake();
    }

    private void inicializarComponentes() {

        PnlPrincipal = new JPanel();
        PnlPrincipal.setLayout(null);
        add(PnlPrincipal);

        LblTitulo = new JLabel("MIS TELÉFONOS");
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        LblTitulo.setBounds(150, 30, 300, 40);
        PnlPrincipal.add(LblTitulo);

        // Nuevo teléfono
        LblNuevoTelefono = new JLabel("Nuevo teléfono *");
        LblNuevoTelefono.setBounds(100, 100, 200, 25);
        PnlPrincipal.add(LblNuevoTelefono);

        TxtNuevoTelefono = new JTextField();
        TxtNuevoTelefono.setBounds(100, 130, 200, 30);
        PnlPrincipal.add(TxtNuevoTelefono);

        // Etiqueta
        LblEtiqueta = new JLabel("Etiqueta (Casa, Trabajo...)");
        LblEtiqueta.setBounds(320, 100, 200, 25);
        PnlPrincipal.add(LblEtiqueta);

        TxtEtiqueta = new JTextField();
        TxtEtiqueta.setBounds(320, 130, 180, 30);
        PnlPrincipal.add(TxtEtiqueta);

        // Botón agregar
        BtnAgregar = new JButton("Agregar");
        BtnAgregar.setBounds(220, 180, 140, 35);
        PnlPrincipal.add(BtnAgregar);

        // Área teléfonos
        TxtAreaTelefonos = new JTextArea();
        TxtAreaTelefonos.setEditable(false);

        ScrollTelefonos = new JScrollPane(TxtAreaTelefonos);
        ScrollTelefonos.setBounds(100, 240, 400, 200);
        PnlPrincipal.add(ScrollTelefonos);

        // Guardar y regresar
        BtnGuardar = new JButton("Guardar");
        BtnGuardar.setBounds(150, 480, 140, 35);
        PnlPrincipal.add(BtnGuardar);

        BtnRegresar = new JButton("Regresar");
        BtnRegresar.setBounds(310, 480, 140, 35);
        PnlPrincipal.add(BtnRegresar);
    }

    private void aplicarEstilos() {

        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnAgregar.setBackground(new Color(255, 140, 0));
        BtnAgregar.setForeground(Color.WHITE);

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

        BtnAgregar.addActionListener(e -> {

            String telefono = TxtNuevoTelefono.getText().trim();
            String etiqueta = TxtEtiqueta.getText().trim();

            if (telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe ingresar un número de teléfono",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (etiqueta.isEmpty()) {
                etiqueta = "Sin etiqueta";
            }

            TxtAreaTelefonos.append("Tel: " + telefono + " - " + etiqueta + "\n");

            TxtNuevoTelefono.setText("");
            TxtEtiqueta.setText("");
        });

        BtnGuardar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Teléfonos guardados correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void cargarTelefonosFake() {

        TxtAreaTelefonos.append("Tel: 6441223344 - Casa\n");
        TxtAreaTelefonos.append("Tel: 6444556677 - Trabajo\n");
    }
}
