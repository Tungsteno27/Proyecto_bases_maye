public class FrmProgramadoCarrito extends JFrame {

    private JTextArea TxtResumen;
    private JLabel LblSubtotal, LblDescuento, LblTotal, LblCupon;
    private JTextField TxtCupon;
    private JButton BtnAplicarCupon, BtnSeguirAgregando, BtnConfirmar;

    private PedidoDTO pedidoActual;
    private ICuponBO cuponBO;

    private double subtotal = 0.0;
    private double descuento = 0.0;
    private double total = 0.0;

    public FrmProgramadoCarrito(PedidoDTO pedido) {
        this.pedidoActual = pedido;

        if (pedidoActual.getTipo().equals("EXPRESS")) {
            setTitle("Carrito - Pedido Express");
        } else {
            setTitle("Carrito - Pedido Programado");
        }

        setSize(650, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 248, 220));

        ConexionBD conexion = new ConexionBD();
        this.cuponBO = new CuponBO(new CuponDAO(conexion));

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

        LblCupon = new JLabel("Cupón:");
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

        // Si es express oculta todo lo relacionado al cupón
        if (pedidoActual.getTipo().equals("EXPRESS")) {
            LblCupon.setVisible(false);
            TxtCupon.setVisible(false);
            BtnAplicarCupon.setVisible(false);
            LblDescuento.setVisible(false);
        }

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
                  .append(" - $").append(String.format("%.2f", precioItem)).append("\n");
                subtotal += precioItem;
            }
        } else {
            sb.append("El carrito está vacío");
            BtnConfirmar.setEnabled(false);
        }

        TxtResumen.setText(sb.toString());
    }

    private void actualizarTotales() {
        total = subtotal - descuento;
        LblSubtotal.setText("Subtotal: $" + String.format("%.2f", subtotal));
        LblDescuento.setText("Descuento: $" + String.format("%.2f", descuento));
        LblTotal.setText("Total: $" + String.format("%.2f", total));
    }

    private void agregarEventos() {

        BtnAplicarCupon.addActionListener(e -> {
            String codigoCupon = TxtCupon.getText().trim();
            if (codigoCupon.isEmpty()) return;

            try {
                CuponDTO cuponReal = cuponBO.obtenerCuponPorNombre(codigoCupon);

                if (cuponBO.validarCupon(cuponReal.getIdCupon())) {
                    descuento = subtotal * (cuponReal.getPorcentaje() / 100);
                    pedidoActual.setCodigoCupon(codigoCupon);
                    actualizarTotales();
                    JOptionPane.showMessageDialog(this, "Cupón aplicado con éxito");
                    
                    TxtCupon.setEditable(false);
                    BtnAplicarCupon.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "El cupón ha expirado o ya no tiene usos",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
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
                ConexionBD conexion = new ConexionBD();
                pedidoActual.setTotalPagar(total);

                if (pedidoActual.getTipo().equals("EXPRESS")) {
                    IPedidoExpressBO pedidoExpressBO = new PedidoExpressBO(
                            new PedidoExpressDAO(conexion),
                            new PedidoDAO(conexion),
                            new PedidoProductoDAO(conexion)
                    );
                    PedidoExpressDTO resultado = pedidoExpressBO.crearPedidoExpress(
                            pedidoActual.getProductos()
                    );
                    JOptionPane.showMessageDialog(this,
                            "Pedido creado exitosamente\n"
                            + "Folio: " + resultado.getFolio() + "\n"
                            + "PIN: " + resultado.getPin() + "\n"
                            + "Guarda estos datos para recoger tu pedido." + "\n"
                            + "RECOGE TU PEDIDO EN MENOS DE 20 MINUTOS",
                            "Pedido Express Confirmado",
                            JOptionPane.INFORMATION_MESSAGE);
                    new FrmPantallaBienvenida();

                } else {
                    IPedidoProgramadoBO pedidoProgramadoBO = new PedidoProgramadoBO(
                            new PedidoProgramadoDAO(conexion),
                            new PedidoDAO(conexion),
                            new PedidoProductoDAO(conexion),
                            new CuponDAO(conexion)
                    );
                    pedidoProgramadoBO.crearPedidoProgramado(
                            pedidoActual.getProductos(),
                            pedidoActual.getIdCliente(),
                            pedidoActual.getCodigoCupon() 
                    );
                    JOptionPane.showMessageDialog(this,
                            "¡Pedido creado exitosamente!\n"
                            + "Estado: Pendiente",
                            "Pedido Confirmado",
                            JOptionPane.INFORMATION_MESSAGE);
                    new FrmMenuUsuario(pedidoActual.getUsuario());
                }
                dispose();

            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al confirmar tu pedido: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                BtnConfirmar.setEnabled(true);
            }
        });
    }
}
