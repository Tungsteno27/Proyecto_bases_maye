/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;

import javax.swing.*;
import java.awt.*;
import negocio.DTOs.UsuarioDTO;

public class FrmProgramadoFinal extends JFrame {

    private JLabel LblTitulo, LblNumeroPedido, LblEstado;
    private JButton BtnInicio;
    private String numeroPedidoReal;

    private UsuarioDTO sesionActual; // Mochila

    public FrmProgramadoFinal(String idPedidoReal, UsuarioDTO sesion) {
        this.numeroPedidoReal = idPedidoReal;
        this.sesionActual = sesion;

        setTitle("Pedido Finalizado");
        setSize(500, 450);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 248, 220));

        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        LblTitulo = new JLabel("Pedido Exitoso");
        LblTitulo.setBounds(130, 60, 300, 40);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        add(LblTitulo);

        LblNumeroPedido = new JLabel("Folio de pedido: #" + numeroPedidoReal);
        LblNumeroPedido.setBounds(110, 150, 300, 30);
        LblNumeroPedido.setFont(new Font("Arial", Font.PLAIN, 18));
        add(LblNumeroPedido);

        LblEstado = new JLabel("Estado: PENDIENTE");
        LblEstado.setBounds(110, 190, 300, 30);
        LblEstado.setFont(new Font("Arial", Font.PLAIN, 18));
        add(LblEstado);

        BtnInicio = new JButton("Inicio");
        BtnInicio.setBounds(170, 270, 150, 45);
        BtnInicio.setBackground(new Color(200, 0, 0));
        BtnInicio.setForeground(Color.WHITE);
        add(BtnInicio);

        BtnInicio.addActionListener(e -> {
            new FrmMenuUsuario(sesionActual); 
            dispose();
        });
    }
}

