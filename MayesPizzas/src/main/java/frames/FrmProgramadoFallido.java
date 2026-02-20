/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Dell PC
 */

public class FrmProgramadoFallido extends JFrame {

    private JLabel LblTitulo;
    private JLabel LblMensaje;
    private JButton BtnInicio;

    public FrmProgramadoFallido() {

        setTitle("Pedido Fallido");
        setSize(500, 420);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(255, 248, 220));

        inicializarComponentes();

        setVisible(true);
    }

    private void inicializarComponentes() {

        LblTitulo = new JLabel("Pedido Fallido ❌");
        LblTitulo.setBounds(120, 60, 300, 40);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        LblTitulo.setForeground(new Color(200, 0, 0));
        add(LblTitulo);

        LblMensaje = new JLabel("Límite alcanzado."
                + "Debes esperar a que un pedido sea entregado "
                + "o cancelado para poder continuar.");
        LblMensaje.setBounds(60, 150, 380, 80);
        LblMensaje.setFont(new Font("Arial", Font.PLAIN, 16));
        add(LblMensaje);

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

