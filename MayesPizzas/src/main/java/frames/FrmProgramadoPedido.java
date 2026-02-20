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
public class FrmProgramadoPedido extends JFrame {

    private JLabel LblTitulo;
    private JLabel LblProducto;
    private JLabel LblTamano;
    private JLabel LblCantidad;
    private JLabel LblNotas;
    private JLabel LblResumen;

    private JRadioButton RbChica;
    private JRadioButton RbMediana;
    private JRadioButton RbGrande;
    private ButtonGroup GrupoTamano;

    private JSpinner SpnCantidad;
    private JTextArea TxtNotas;

    private JTextArea TxtResumen;

    private JButton BtnAgregar;
    private JButton BtnRegresar;
    private JButton BtnVerCarrito;

    private String productoSeleccionado;
    private double precioBase = 180; // ejemplo

    public FrmProgramadoPedido(String producto) {

        this.productoSeleccionado = producto;

        setTitle("Personalizar Pedido Programado");
        setSize(600, 650);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(255, 248, 220));

        inicializarComponentes();
        agregarEventos();
        actualizarResumen();

        setVisible(true);
    }

    private void inicializarComponentes() {

        LblTitulo = new JLabel("PERSONALIZAR PEDIDO");
        LblTitulo.setBounds(150, 20, 400, 30);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(LblTitulo);

        LblProducto = new JLabel("Producto: " + productoSeleccionado);
        LblProducto.setBounds(50, 70, 400, 25);
        add(LblProducto);

        LblTamano = new JLabel("Tamaño:");
        LblTamano.setBounds(50, 110, 100, 25);
        add(LblTamano);

        RbChica = new JRadioButton("Chica (+$0)");
        RbChica.setBounds(50, 140, 150, 25);

        RbMediana = new JRadioButton("Mediana (+$30)");
        RbMediana.setBounds(50, 170, 150, 25);

        RbGrande = new JRadioButton("Grande (+$60)");
        RbGrande.setBounds(50, 200, 150, 25);

        GrupoTamano = new ButtonGroup();
        GrupoTamano.add(RbChica);
        GrupoTamano.add(RbMediana);
        GrupoTamano.add(RbGrande);

        RbChica.setSelected(true);

        add(RbChica);
        add(RbMediana);
        add(RbGrande);

        LblCantidad = new JLabel("Cantidad:");
        LblCantidad.setBounds(50, 250, 100, 25);
        add(LblCantidad);

        SpnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        SpnCantidad.setBounds(130, 250, 60, 25);
        add(SpnCantidad);

        LblNotas = new JLabel("Notas adicionales:");
        LblNotas.setBounds(50, 300, 150, 25);
        add(LblNotas);

        TxtNotas = new JTextArea();
        TxtNotas.setLineWrap(true);
        JScrollPane scrollNotas = new JScrollPane(TxtNotas);
        scrollNotas.setBounds(50, 330, 480, 80);
        add(scrollNotas);

        LblResumen = new JLabel("Resumen:");
        LblResumen.setBounds(50, 430, 100, 25);
        add(LblResumen);

        TxtResumen = new JTextArea();
        TxtResumen.setEditable(false);
        JScrollPane scrollResumen = new JScrollPane(TxtResumen);
        scrollResumen.setBounds(50, 460, 480, 80);
        add(scrollResumen);

        BtnAgregar = new JButton("Agregar al carrito");
        BtnAgregar.setBounds(60, 550, 200, 40);
        BtnAgregar.setBackground(new Color(255, 140, 0));
        BtnAgregar.setForeground(Color.WHITE);
        add(BtnAgregar);

        BtnRegresar = new JButton("Regresar");
        BtnRegresar.setBounds(330, 550, 200, 40);
        BtnRegresar.setBackground(new Color(200, 0, 0));
        BtnRegresar.setForeground(Color.WHITE);
        add(BtnRegresar);

        BtnVerCarrito = new JButton("Ver carrito");
        BtnVerCarrito.setBounds(180, 600, 200, 40);
        BtnVerCarrito.setBackground(new Color(0, 128, 0));
        BtnVerCarrito.setForeground(Color.WHITE);
        add(BtnVerCarrito);

    }

    private void agregarEventos() {

        RbChica.addActionListener(e -> actualizarResumen());
        RbMediana.addActionListener(e -> actualizarResumen());
        RbGrande.addActionListener(e -> actualizarResumen());
        SpnCantidad.addChangeListener(e -> actualizarResumen());

        BtnAgregar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Producto agregado al carrito",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        BtnRegresar.addActionListener(e -> {
            new FrmProgramadoMenu();
            dispose();
        });

        BtnVerCarrito.addActionListener(e -> {
            new FrmProgramadoCarrito();
            dispose();
        });

    }

    private void actualizarResumen() {

        int cantidad = (int) SpnCantidad.getValue();
        double extra = 0;
        String tamano = "Chica";

        if (RbMediana.isSelected()) {
            extra = 30;
            tamano = "Mediana";
        } else if (RbGrande.isSelected()) {
            extra = 60;
            tamano = "Grande";
        }

        double total = (precioBase + extra) * cantidad;

        TxtResumen.setText(
                "Producto: " + productoSeleccionado
                + "\nTamaño: " + tamano
                + "\nCantidad: " + cantidad
                + "\nTotal: $" + total
        );
    }
}
