/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import negocio.DTOs.UsuarioDTO;
import negocio.DTOs.TelefonoDTO;
import negocio.BOs.ITelefonoBO;
import negocio.BOs.TelefonoBO;
import negocio.Exception.NegocioException;

import persistencia.DAOs.ITelefonoDAO;
import persistencia.DAOs.TelefonoDAO;
import persistencia.conexion.IConexionBD;

import persistencia.conexion.ConexionBD;

/**
 * Representa la ventana de gestión de teléfonos del usuario dentro del sistema.
 * <p>
 * Esta clase permite al usuario agregar nuevos números telefónicos, asignarles
 * una etiqueta descriptiva (Casa, Trabajo, etc.) y visualizar la lista actual
 * de teléfonos registrados.
 * </p>
 * Forma parte de la capa de presentación.
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

    private UsuarioDTO sesionActual;
    private ITelefonoBO telefonoBO;

    /**
     * Constructor de la clase FrmMisTelefonos. Configura la ventana principal e
     * inicializa sus componentes, estilos y eventos.
     */
    public FrmMisTelefonos(UsuarioDTO sesion) {
        this.sesionActual = sesion;

        inicializarBOs();

        setTitle("Mis teléfonos - Maye´s Pizzas");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializarComponentes();
        aplicarEstilos();
        agregarEventos();

        setVisible(true);

        cargarTelefonos();
    }

    
    private void inicializarBOs() {
        IConexionBD conexion = new ConexionBD();
        ITelefonoDAO telefonoDAO = new TelefonoDAO(conexion);
        telefonoBO = new TelefonoBO(telefonoDAO);
    }

    /**
     * Inicializa y posiciona todos los componentes gráficos dentro del panel
     * principal.
     */
    private void inicializarComponentes() {

        PnlPrincipal = new JPanel();
        PnlPrincipal.setLayout(null);
        add(PnlPrincipal);

        LblTitulo = new JLabel("MIS TELÉFONOS");
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        LblTitulo.setBounds(150, 30, 300, 40);
        PnlPrincipal.add(LblTitulo);

        LblNuevoTelefono = new JLabel("Nuevo teléfono *");
        LblNuevoTelefono.setBounds(100, 100, 200, 25);
        PnlPrincipal.add(LblNuevoTelefono);

        TxtNuevoTelefono = new JTextField();
        TxtNuevoTelefono.setBounds(100, 130, 200, 30);
        PnlPrincipal.add(TxtNuevoTelefono);

        LblEtiqueta = new JLabel("Etiqueta (Casa, Trabajo...)");
        LblEtiqueta.setBounds(320, 100, 200, 25);
        PnlPrincipal.add(LblEtiqueta);

        TxtEtiqueta = new JTextField();
        TxtEtiqueta.setBounds(320, 130, 180, 30);
        PnlPrincipal.add(TxtEtiqueta);

        BtnAgregar = new JButton("Agregar");
        BtnAgregar.setBounds(220, 180, 140, 35);
        PnlPrincipal.add(BtnAgregar);

        TxtAreaTelefonos = new JTextArea();
        TxtAreaTelefonos.setEditable(false);

        ScrollTelefonos = new JScrollPane(TxtAreaTelefonos);
        ScrollTelefonos.setBounds(100, 240, 400, 200);
        PnlPrincipal.add(ScrollTelefonos);

        BtnGuardar = new JButton("Guardar");
        BtnGuardar.setBounds(150, 480, 140, 35);
        PnlPrincipal.add(BtnGuardar);

        BtnRegresar = new JButton("Regresar");
        BtnRegresar.setBounds(310, 480, 140, 35);
        PnlPrincipal.add(BtnRegresar);
    }

    /**
     * Aplica estilos visuales personalizados a los botones y panel principal.
     */
    private void aplicarEstilos() {

        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnAgregar.setBackground(new Color(255, 140, 0));
        BtnAgregar.setForeground(Color.WHITE);

        BtnGuardar.setBackground(new Color(255, 140, 0));
        BtnGuardar.setForeground(Color.WHITE);

        BtnRegresar.setBackground(new Color(200, 0, 0));
        BtnRegresar.setForeground(Color.WHITE);
    }

    /**
     * Define los eventos asociados a los botones de la ventana.
     */
    private void agregarEventos() {

        BtnRegresar.addActionListener(e -> {
            new FrmMenuUsuario(sesionActual);
            dispose();
        });

        BtnAgregar.addActionListener(e -> {

            try {
                TelefonoDTO dto = new TelefonoDTO();
                dto.setIdCliente(sesionActual.getIdUsuario());
                dto.setNumero(TxtNuevoTelefono.getText().trim());
                dto.setEtiqueta(TxtEtiqueta.getText().trim());

                telefonoBO.agregarTelefono(dto);

                JOptionPane.showMessageDialog(this,
                        "Teléfono agregado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                TxtNuevoTelefono.setText("");
                TxtEtiqueta.setText("");

                cargarTelefonos();

            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        BtnGuardar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Cambios guardados",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    /**
     * Carga telefonos
     */
    private void cargarTelefonos() {

        try {
            TxtAreaTelefonos.setText("");

            List<TelefonoDTO> lista
                    = telefonoBO.obtenerTelefonosPorIdCliente(
                            sesionActual.getIdUsuario()
                    );

            if (lista.isEmpty()) {
                TxtAreaTelefonos.append("No tienes teléfonos registrados.\n");
                return;
            }

            for (TelefonoDTO t : lista) {
                TxtAreaTelefonos.append(
                        "Tel: " + t.getNumero()
                        + " - " + t.getEtiqueta()
                        + "\n-----------------------\n"
                );
            }

        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
