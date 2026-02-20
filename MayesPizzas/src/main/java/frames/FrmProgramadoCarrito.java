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

public class FrmProgramadoCarrito extends JFrame {

    private JTextArea TxtResumen;
    private JScrollPane ScrollResumen;

    private JLabel LblSubtotal;
    private JLabel LblDescuento;
    private JLabel LblTotal;

    private JTextField TxtCupon;
    private JButton BtnAplicarCupon;

    private JButton BtnSeguirAgregando;
    private JButton BtnConfirmar;

    private double subtotal = 370.0; // simulado
    private double descuento = 0.0;
    private double total = 370.0;

    public FrmProgramadoCarrito() {

        setTitle("Carrito - Pedido Programado");
        setSize(650, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(255, 248, 220));

        inicializarComponentes();
        actualizarTotales();
        agregarEventos();

        setVisible(true);
    }

    private void inicializarComponentes() {

        JLabel LblTitulo = new JLabel("Resumen del Pedido");
        LblTitulo.setBounds(200, 30, 300, 35);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(LblTitulo);

        TxtResumen = new JTextArea();
        TxtResumen.setEditable(false);
        TxtResumen.setText(
                "Pizza Pepperoni x2 - $360\n" +
                "Refresco 2L x1 - $45\n"
        );

        ScrollResumen = new JScrollPane(TxtResumen);
        ScrollResumen.setBounds(120, 80, 400, 150);
        add(ScrollResumen);

        LblSubtotal = new JLabel();
        LblSubtotal.setBounds(120, 250, 300, 25);
        add(LblSubtotal);

        JLabel LblCupon = new JLabel("Cupón:");
        LblCupon.setBounds(120, 290, 80, 25);
        add(LblCupon);

        TxtCupon = new JTextField();
        TxtCupon.setBounds(180, 290, 180, 30);
        add(TxtCupon);

        BtnAplicarCupon = new JButton("Aplicar");
        BtnAplicarCupon.setBounds(380, 290, 100, 30);
        BtnAplicarCupon.setBackground(new Color(255, 140, 0));
        BtnAplicarCupon.setForeground(Color.WHITE);
        add(BtnAplicarCupon);

        LblDescuento = new JLabel();
        LblDescuento.setBounds(120, 340, 300, 25);
        add(LblDescuento);

        LblTotal = new JLabel();
        LblTotal.setBounds(120, 380, 300, 30);
        LblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        add(LblTotal);

        BtnSeguirAgregando = new JButton("Seguir agregando");
        BtnSeguirAgregando.setBounds(120, 450, 200, 40);
        BtnSeguirAgregando.setBackground(new Color(255, 140, 0));
        BtnSeguirAgregando.setForeground(Color.WHITE);
        add(BtnSeguirAgregando);

        BtnConfirmar = new JButton("Confirmar pedido");
        BtnConfirmar.setBounds(350, 450, 200, 40);
        BtnConfirmar.setBackground(new Color(200, 0, 0));
        BtnConfirmar.setForeground(Color.WHITE);
        add(BtnConfirmar);
    }

    private void actualizarTotales() {
        total = subtotal - descuento;

        LblSubtotal.setText("Subtotal: $" + subtotal);
        LblDescuento.setText("Descuento: $" + descuento);
        LblTotal.setText("Total: $" + total);
    }

    private void agregarEventos() {

        BtnAplicarCupon.addActionListener(e -> {

            String cupon = TxtCupon.getText().trim();

            if (cupon.equalsIgnoreCase("PIZZA10")) {
                descuento = subtotal * 0.10;
                JOptionPane.showMessageDialog(this,
                        "Cupón aplicado: 10% de descuento");
            } else {
                descuento = 0;
                JOptionPane.showMessageDialog(this,
                        "Cupón inválido",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            actualizarTotales();
        });

        BtnSeguirAgregando.addActionListener(e -> {
            new FrmProgramadoMenu();
            dispose();
        });

        BtnConfirmar.addActionListener(e -> {
            new FrmProgramadoFinal();
            dispose();
        });
    }
}

