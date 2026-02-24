/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package frames;

import javax.swing.*;
import java.awt.*;
import negocio.BOs.CuponBO;
import negocio.BOs.ICuponBO;
import negocio.BOs.IPedidoBO;
import negocio.BOs.PedidoBO;
import negocio.DTOs.CuponDTO;
import negocio.DTOs.PedidoDTO;
import negocio.DTOs.ProductoCarritoDTO;
import negocio.Exception.NegocioException;
import persistencia.DAOs.CuponDAO;
import persistencia.DAOs.PedidoDAO;
import persistencia.conexion.ConexionBD;

public class FrmProgramadoCarrito extends JFrame {

    private JTextArea TxtResumen;
    private JLabel LblSubtotal, LblDescuento, LblTotal;
    private JTextField TxtCupon;
    private JButton BtnAplicarCupon, BtnSeguirAgregando, BtnConfirmar;

    // lo nuevo
    private PedidoDTO pedidoActual;
    private ICuponBO cuponBO;
    private IPedidoBO pedidoBO;
    
    private double subtotal = 0.0;
    private double descuento = 0.0;
    private double total = 0.0;

    /**
     * constructor para el pedido dtp
     * @param pedido 
     */
    public FrmProgramadoCarrito(PedidoDTO pedido) {
        this.pedidoActual = pedido;

        setTitle("Carrito - Pedido Programado");
        setSize(650, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 248, 220));
        
        ConexionBD conexion = new ConexionBD();
        this.cuponBO = new CuponBO(new CuponDAO(conexion));
        this.pedidoBO = new PedidoBO(new PedidoDAO(conexion));

        inicializarComponentes();
        cargarCarritoReal();
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
        JScrollPane ScrollResumen = new JScrollPane(TxtResumen);
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

    private void cargarCarritoReal() {
        subtotal = 0.0;
        StringBuilder sb = new StringBuilder();

        if (pedidoActual.getProductos() != null && !pedidoActual.getProductos().isEmpty()) {
            for (ProductoCarritoDTO item : pedidoActual.getProductos()) {
                double precioItem = item.getProducto().getPrecio() * item.getCantidad();
                sb.append(item.getProducto().getNombre())
                  .append(" x").append(item.getCantidad())
                  .append(" - $").append(precioItem).append("\n");
                
                subtotal += precioItem;
            }
        } else {
            sb.append("El carrito esta vacio ");
            BtnConfirmar.setEnabled(false);
        }
        
        TxtResumen.setText(sb.toString());
    }

    private void actualizarTotales() {
        total = subtotal - descuento;
        LblSubtotal.setText("Subtotal: $" + subtotal);
        LblDescuento.setText("Descuento: $" + descuento);
        LblTotal.setText("Total: $" + total);
    }

    private void agregarEventos() {
        BtnAplicarCupon.addActionListener(e -> {
            String codigoCupon = TxtCupon.getText().trim();
            if(codigoCupon.isEmpty()) return;

            try {
                CuponDTO cuponReal = cuponBO.obtenerCuponPorNombre(codigoCupon);
                // buscamos si el cupon existe con el cuponBO de Yulian
                
                if (cuponBO.validarCupon(cuponReal.getIdCupon())) {
                    descuento = subtotal * (cuponReal.getPorcentaje() / 100);
                    JOptionPane.showMessageDialog(this, "Cupon aplicado con exito");
                    actualizarTotales();
                } else {
                    JOptionPane.showMessageDialog(this, "El cupon ha expirado o ya no tiene usos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        BtnSeguirAgregando.addActionListener(e -> {
            new FrmProgramadoMenu(pedidoActual); 
            dispose();
        });

        BtnConfirmar.addActionListener(e -> {
            BtnConfirmar.setEnabled(false);
            try {
                pedidoActual.setTotalPagar(total);
                pedidoActual.setEstado("PENDIENTE");
                PedidoDTO pedidoGuardado = pedidoBO.registrarPedido(pedidoActual);
                
                // new FrmProgramadoFinal(String.valueOf(pedidoGuardado.getIdPedido())); esto lo cambie ahorita se correige
                dispose();
                
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, "Error al confirmar tu pedido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                BtnConfirmar.setEnabled(true);
            }
        });
    }
}

