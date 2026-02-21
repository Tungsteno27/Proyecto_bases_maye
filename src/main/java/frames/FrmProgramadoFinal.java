/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 *
 * @author Noelia E.N.
 */

public class FrmProgramadoFinal extends JFrame {

    private JLabel LblTitulo;
    private JLabel LblNumeroPedido;
    private JLabel LblEstado;

    private JButton BtnInicio;

    private String numeroPedido;

    public FrmProgramadoFinal() {

        setTitle("Pedido Finalizado");
        setSize(500, 450);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(255, 248, 220));

        generarNumeroPedido();
        inicializarComponentes();

        setVisible(true);
    }

    private void generarNumeroPedido() {

        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 7; i++) {
            sb.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }

        numeroPedido = sb.toString();
    }

    private void inicializarComponentes() {

        LblTitulo = new JLabel("Pedido Exitoso");
        LblTitulo.setBounds(130, 60, 300, 40);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        add(LblTitulo);

        LblNumeroPedido = new JLabel("Número de pedido: " + numeroPedido);
        LblNumeroPedido.setBounds(110, 150, 300, 30);
        LblNumeroPedido.setFont(new Font("Arial", Font.PLAIN, 18));
        add(LblNumeroPedido);

        LblEstado = new JLabel("Estado: Pendiente");
        LblEstado.setBounds(110, 190, 300, 30);
        LblEstado.setFont(new Font("Arial", Font.PLAIN, 18));
        add(LblEstado);

        BtnInicio = new JButton("Inicio");
        BtnInicio.setBounds(170, 270, 150, 45);
        BtnInicio.setBackground(new Color(200, 0, 0));
        BtnInicio.setForeground(Color.WHITE);
        add(BtnInicio);

        BtnInicio.addActionListener(e -> {
            new FrmMenuUsuario();
            dispose();
        });
    }
}

