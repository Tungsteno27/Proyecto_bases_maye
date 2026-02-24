/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frames;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import negocio.BOs.ClienteBO;
import negocio.BOs.IClienteBO;
import negocio.BOs.ITelefonoBO;
import negocio.BOs.IUsuarioBO;
import negocio.BOs.TelefonoBO;
import negocio.BOs.UsuarioBO;
import negocio.DTOs.ClienteDTO;
import negocio.DTOs.UsuarioDTO;
import persistencia.DAOs.ClienteDAO;
import persistencia.DAOs.DireccionDAO;
import persistencia.DAOs.TelefonoDAO;
import persistencia.DAOs.UsuarioDAO;
import persistencia.conexion.ConexionBD;

/**
 *
 * @author Noelia E.N.
 */

public class FrmMiPerfil extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;

    private JTextField TxtNombres, TxtApellidoPaterno, TxtApellidoMaterno;
    private JTextField TxtFechaNacimiento;
    private JTextField TxtCalle, TxtNumero, TxtColonia;
    private JPasswordField PswContrasena, PswConfirmarContrasena;
    private JTextField TxtTelefono;

    private JButton BtnGuardar, BtnRegresar;
    
    private UsuarioDTO sesionActual;
    private ClienteDTO clienteActual; 
    private IClienteBO clienteBO;
    
    /**
     * 
     * @param usuarioLogueado 
     */
    public FrmMiPerfil(UsuarioDTO usuarioLogueado) {
        this.sesionActual = usuarioLogueado;

        setTitle("Mi perfil - Maye´s Pizzas");
        setSize(550, 750); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // c me olvidaba inicializar los BOs que oso bruh
        ConexionBD conexion = new ConexionBD();
        IUsuarioBO usuarioBO = new UsuarioBO(new UsuarioDAO(conexion));
        ITelefonoBO telefonoBO = new TelefonoBO(new TelefonoDAO(conexion));
        this.clienteBO = new ClienteBO(new ClienteDAO(conexion), new DireccionDAO(conexion), usuarioBO, telefonoBO); 

        inicializarComponentes();
        aplicarEstilos();
        agregarEventos();

        cargarDatosReales();
        setVisible(true);
    }
    
    /**
     * 
     */
    private void inicializarComponentes() {
        PnlPrincipal = new JPanel();
        PnlPrincipal.setLayout(null);
        add(PnlPrincipal);

        LblTitulo = new JLabel("MI PERFIL");
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        LblTitulo.setBounds(100, 20, 350, 40);
        PnlPrincipal.add(LblTitulo);

        JLabel LblNombres = new JLabel("Nombres *");
        LblNombres.setBounds(100, 70, 300, 25);
        PnlPrincipal.add(LblNombres);
        TxtNombres = new JTextField();
        TxtNombres.setBounds(100, 95, 350, 30);
        PnlPrincipal.add(TxtNombres);

        JLabel LblApellidos = new JLabel("Apellidos (Paterno y Materno) *");
        LblApellidos.setBounds(100, 130, 300, 25);
        PnlPrincipal.add(LblApellidos);
        TxtApellidoPaterno = new JTextField();
        TxtApellidoPaterno.setBounds(100, 155, 170, 30);
        PnlPrincipal.add(TxtApellidoPaterno);
        TxtApellidoMaterno = new JTextField();
        TxtApellidoMaterno.setBounds(280, 155, 170, 30);
        PnlPrincipal.add(TxtApellidoMaterno);

        JLabel LblFechaNacimiento = new JLabel("Fecha de nacimiento * (dd/MM/yyyy)");
        LblFechaNacimiento.setBounds(100, 190, 300, 25);
        PnlPrincipal.add(LblFechaNacimiento);
        TxtFechaNacimiento = new JTextField();
        TxtFechaNacimiento.setBounds(100, 215, 350, 30);
        PnlPrincipal.add(TxtFechaNacimiento);

        JLabel LblDomicilio = new JLabel("Domicilio (Calle, Núm, Colonia) *");
        LblDomicilio.setBounds(100, 250, 300, 25);
        PnlPrincipal.add(LblDomicilio);
        TxtCalle = new JTextField();
        TxtCalle.setBounds(100, 275, 170, 30);
        PnlPrincipal.add(TxtCalle);
        TxtNumero = new JTextField();
        TxtNumero.setBounds(280, 275, 60, 30);
        PnlPrincipal.add(TxtNumero);
        TxtColonia = new JTextField();
        TxtColonia.setBounds(350, 275, 100, 30);
        PnlPrincipal.add(TxtColonia);

        JLabel LblTelefono = new JLabel("Teléfono *");
        LblTelefono.setBounds(100, 310, 300, 25);
        PnlPrincipal.add(LblTelefono);
        TxtTelefono = new JTextField();
        TxtTelefono.setBounds(100, 335, 350, 30);
        PnlPrincipal.add(TxtTelefono);

        JLabel LblContrasena = new JLabel("Nueva Contraseña (Dejar en blanco si no cambia)");
        LblContrasena.setBounds(100, 370, 350, 25);
        PnlPrincipal.add(LblContrasena);
        PswContrasena = new JPasswordField();
        PswContrasena.setBounds(100, 395, 350, 30);
        PnlPrincipal.add(PswContrasena);

        JLabel LblConfirmar = new JLabel("Confirmar Nueva Contraseña");
        LblConfirmar.setBounds(100, 430, 300, 25);
        PnlPrincipal.add(LblConfirmar);
        PswConfirmarContrasena = new JPasswordField();
        PswConfirmarContrasena.setBounds(100, 455, 350, 30);
        PnlPrincipal.add(PswConfirmarContrasena);

        BtnGuardar = new JButton("Actualizar Datos");
        BtnGuardar.setBounds(120, 520, 140, 35);
        PnlPrincipal.add(BtnGuardar);

        BtnRegresar = new JButton("Regresar");
        BtnRegresar.setBounds(300, 520, 140, 35);
        PnlPrincipal.add(BtnRegresar);
    }

    /**
     * 
     */
    private void aplicarEstilos() {
        PnlPrincipal.setBackground(new Color(255, 248, 220));
        BtnGuardar.setBackground(new Color(255, 140, 0));
        BtnGuardar.setForeground(Color.WHITE);
        BtnRegresar.setBackground(new Color(200, 0, 0));
        BtnRegresar.setForeground(Color.WHITE);
    }
    
    /**
     * 
     */
    private void agregarEventos() {
        BtnRegresar.addActionListener(e -> {
            new FrmMenuUsuario(sesionActual);
            dispose();
        });

        BtnGuardar.addActionListener(e -> {
            BtnGuardar.setEnabled(false);
            
            try { // aqui tenia el otro problema de names
                if (clienteActual.getUsuario() == null) {
                    clienteActual.setUsuario(sesionActual);
                }

                clienteActual.setNombres(TxtNombres.getText().trim());
                clienteActual.setApellidoPaterno(TxtApellidoPaterno.getText().trim());
                clienteActual.setApellidoMaterno(TxtApellidoMaterno.getText().trim());
                clienteActual.setCalle(TxtCalle.getText().trim());
                clienteActual.setNumero(TxtNumero.getText().trim());
                clienteActual.setColonia(TxtColonia.getText().trim());
                
                LocalDate fecha = LocalDate.parse(TxtFechaNacimiento.getText().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                clienteActual.setFechaNacimiento(fecha);

                String contrasena = new String(PswContrasena.getPassword());
                String confirmar = new String(PswConfirmarContrasena.getPassword());

                if (!contrasena.isEmpty()) {
                    if (!contrasena.equals(confirmar)) {
                        JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
                        BtnGuardar.setEnabled(true);
                        return;
                    }
                    clienteActual.getUsuario().setPassword(contrasena);
                }

                clienteBO.actualizarInformacion(clienteActual); 

                JOptionPane.showMessageDialog(this, "Datos actualizados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception ex) {
                // Si explota, mostramos exactamente por qué para que no sea un error silencioso
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                BtnGuardar.setEnabled(true);
            }
        });
    }
    private void cargarDatosReales() {
        try {
            clienteActual = clienteBO.obtenerClientePorIdUsuario(sesionActual.getIdUsuario());
            
            if (clienteActual != null) {
                TxtNombres.setText(clienteActual.getNombres());
                TxtApellidoPaterno.setText(clienteActual.getApellidoPaterno());
                TxtApellidoMaterno.setText(clienteActual.getApellidoMaterno() != null ? clienteActual.getApellidoMaterno() : "");
                if (clienteActual.getFechaNacimiento() != null) {
                    TxtFechaNacimiento.setText(clienteActual.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
                
                TxtCalle.setText(clienteActual.getCalle() != null ? clienteActual.getCalle() : "");
                TxtNumero.setText(clienteActual.getNumero() != null ? clienteActual.getNumero() : "");
                TxtColonia.setText(clienteActual.getColonia() != null ? clienteActual.getColonia() : "");
                
                if(clienteActual.getTelefonos() != null && !clienteActual.getTelefonos().isEmpty()) {
                    TxtTelefono.setText(clienteActual.getTelefonos().get(0).getNumero());
                }
            }  
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar su información: " + ex.getMessage());
        }
    }
}
