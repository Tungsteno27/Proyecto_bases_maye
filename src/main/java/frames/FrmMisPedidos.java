/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;

import javax.swing.*;
import java.awt.*;
import negocio.DTOs.UsuarioDTO;
import java.util.List;

import negocio.DTOs.UsuarioDTO;
import negocio.DTOs.PedidoDTO;
import negocio.BOs.IPedidoBO;
import negocio.BOs.PedidoBO;
import negocio.Exception.NegocioException;

import persistencia.DAOs.IPedidoDAO;
import persistencia.DAOs.PedidoDAO;
import persistencia.conexion.IConexionBD;
import persistencia.conexion.ConexionBD;

/**
 * Ventana que muestra los pedidos programados del cliente logueado.
 *
 * @author Noelia
 */
public class FrmMisPedidos extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;
    private JButton BtnFiltrar;
    private JTextArea TxtAreaPedidos;
    private JScrollPane ScrollPedidos;
    private JButton BtnRegresar;

    private UsuarioDTO sesionActual;
    private IPedidoBO pedidoBO;

    public FrmMisPedidos(UsuarioDTO sesion) {
        this.sesionActual = sesion;

        inicializarBOs();

        setTitle("Mis pedidos - Maye´s Pizzas");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializarComponentes();
        aplicarEstilos();
        agregarEventos();

        setVisible(true);
    }

    private void inicializarBOs() {
        IConexionBD conexion = new ConexionBD();
        IPedidoDAO pedidoDAO = new PedidoDAO(conexion);
        pedidoBO = new PedidoBO(pedidoDAO);
    }

    private void inicializarComponentes() {

        PnlPrincipal = new JPanel();
        PnlPrincipal.setLayout(null);
        add(PnlPrincipal);

        LblTitulo = new JLabel("MIS PEDIDOS");
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        LblTitulo.setBounds(150, 30, 300, 40);
        PnlPrincipal.add(LblTitulo);

        BtnFiltrar = new JButton("Cargar pedidos");
        BtnFiltrar.setBounds(220, 100, 160, 30);
        PnlPrincipal.add(BtnFiltrar);

        TxtAreaPedidos = new JTextArea();
        TxtAreaPedidos.setEditable(false);

        ScrollPedidos = new JScrollPane(TxtAreaPedidos);
        ScrollPedidos.setBounds(80, 160, 440, 300);
        PnlPrincipal.add(ScrollPedidos);

        BtnRegresar = new JButton("Regresar");
        BtnRegresar.setBounds(230, 500, 140, 35);
        PnlPrincipal.add(BtnRegresar);
    }

    private void aplicarEstilos() {

        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnFiltrar.setBackground(new Color(255, 140, 0));
        BtnFiltrar.setForeground(Color.WHITE);

        BtnRegresar.setBackground(new Color(200, 0, 0));
        BtnRegresar.setForeground(Color.WHITE);
    }

    private void agregarEventos() {

        BtnRegresar.addActionListener(e -> {
            new FrmMenuUsuario(sesionActual);
            dispose();
        });

        BtnFiltrar.addActionListener(e -> {

            try {

                TxtAreaPedidos.setText("");

                List<PedidoDTO> pedidos
                        = pedidoBO.consultarPedidosProgramadosPorCliente(
                                sesionActual.getIdUsuario()
                        );

                if (pedidos.isEmpty()) {
                    TxtAreaPedidos.append("No tienes pedidos programados.\n");
                    return;
                }

                for (PedidoDTO p : pedidos) {

                    TxtAreaPedidos.append(
                            "Pedido #" + p.getIdPedido()
                            + "\nFecha: " + p.getFechaCreacion()
                            + "\nEstado: " + p.getEstado()
                            + "\nTotal: $" + p.getTotalPagar()
                    );
                }

            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
