/*
 * click nbfsnbhostsystemfilesystemtemplateslicenseslicense-defaulttxt to change this license
 * click nbfsnbhostsystemfilesystemtemplatesclassesclassjava to edit this template
 */
package frames;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import negocio.BOs.ClienteBO;
import negocio.BOs.IClienteBO;
import negocio.BOs.ITelefonoBO;
import negocio.BOs.IUsuarioBO;
import negocio.BOs.TelefonoBO;
import negocio.BOs.UsuarioBO;
import negocio.DTOs.ClienteDTO;
import negocio.DTOs.TelefonoDTO;
import negocio.DTOs.UsuarioDTO;
import negocio.Exception.NegocioException;
import persistencia.DAOs.ClienteDAO;
import persistencia.DAOs.DireccionDAO;
import persistencia.DAOs.IClienteDAO;
import persistencia.DAOs.IDireccionDAO;
import persistencia.DAOs.ITelefonoDAO;
import persistencia.DAOs.IUsuarioDAO;
import persistencia.DAOs.TelefonoDAO;
import persistencia.DAOs.UsuarioDAO;
import persistencia.Dominio.RolUsuario;
import persistencia.conexion.ConexionBD;

/**
 * pantalla que gestiona el registro de nuevos usuarios en el sistema
 * recolecta datos personales de contacto y credenciales de acceso
 * validando la integridad de la informacion antes de enviarla a la capa logica
 *
 * @author Dell PC
 */
public class FrmRegistro extends JFrame {

    private JPanel PnlPrincipal;
    private JLabel LblTitulo;

    private JLabel LblFechaNacimiento;
    private JTextField TxtFechaNacimiento;

    private JLabel LblContrasena;
    private JPasswordField PswContrasena;

    private JLabel LblConfirmarContrasena;
    private JPasswordField PswConfirmarContrasena;

    private JLabel LblTelefono;
    private JTextField TxtTelefono;

    private JButton BtnGuardar;
    private JButton BtnRegresar;
    private JLabel LblNombres;
    private JLabel LblApellidoPaterno;
    private JLabel LblApellidoMaterno;
    private JTextField TxtNombres;
    private JTextField TxtApellidoPaterno;
    private JTextField TxtApellidoMaterno;
    private JLabel LblCalle;
    private JLabel LblNumero;
    private JLabel LblColonia;
    private JTextField TxtCalle;
    private JTextField TxtNumero;
    private JTextField TxtColonia;

    private JLabel LblCorreo;
    private JTextField TxtCorreo;

    /**
     * constructor principal de la ventana de registro
     * define las dimensiones y comportamiento de la ventana y llama a los
     * metodos encargados de dibujar la interfaz grafica y asignar eventos
     */
    public FrmRegistro() {
        setTitle("Registro - Mayes Pizzas");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializarComponentes();
        aplicarEstilos();
        agregarEventos();

        setVisible(true);

    }

    /**
     * crea y posiciona todos los campos de texto etiquetas y botones
     * utilizando un diseno absoluto dentro de un panel con barra de desplazamiento
     * para asegurar que todos los datos solicitados sean visibles
     */
    private void inicializarComponentes() {

        PnlPrincipal = new JPanel();
        PnlPrincipal.setLayout(null);
        PnlPrincipal.setPreferredSize(new Dimension(580, 750));
        PnlPrincipal.setBackground(new Color(255, 248, 220));

        // barra de desplazamiento vertical
        JScrollPane scroll = new JScrollPane(PnlPrincipal);
        scroll.setBounds(0, 0, 600, 600);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scroll);

        LblTitulo = new JLabel("REGISTRO DE USUARIO");
        LblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        LblTitulo.setBounds(100, 30, 350, 40);
        PnlPrincipal.add(LblTitulo);

        // correo electronico
        LblCorreo = new JLabel("Correo electronico *");
        LblCorreo.setBounds(100, 80, 300, 25);
        PnlPrincipal.add(LblCorreo);
        TxtCorreo = new JTextField();
        TxtCorreo.setBounds(100, 105, 350, 30);
        PnlPrincipal.add(TxtCorreo);

        // nombres
        LblNombres = new JLabel("Nombres *");
        LblNombres.setBounds(100, 145, 300, 25);
        PnlPrincipal.add(LblNombres);
        TxtNombres = new JTextField();
        TxtNombres.setBounds(100, 170, 350, 30);
        PnlPrincipal.add(TxtNombres);

        // apellido paterno
        LblApellidoPaterno = new JLabel("Apellido paterno *");
        LblApellidoPaterno.setBounds(100, 210, 300, 25);
        PnlPrincipal.add(LblApellidoPaterno);
        TxtApellidoPaterno = new JTextField();
        TxtApellidoPaterno.setBounds(100, 235, 350, 30);
        PnlPrincipal.add(TxtApellidoPaterno);

        // apellido materno
        LblApellidoMaterno = new JLabel("Apellido materno");
        LblApellidoMaterno.setBounds(100, 275, 300, 25);
        PnlPrincipal.add(LblApellidoMaterno);
        TxtApellidoMaterno = new JTextField();
        TxtApellidoMaterno.setBounds(100, 300, 350, 30);
        PnlPrincipal.add(TxtApellidoMaterno);

        // fecha de nacimiento
        LblFechaNacimiento = new JLabel("Fecha de nacimiento * (dd/MM/yyyy)");
        LblFechaNacimiento.setBounds(100, 340, 300, 25);
        PnlPrincipal.add(LblFechaNacimiento);
        TxtFechaNacimiento = new JTextField();
        TxtFechaNacimiento.setBounds(100, 365, 350, 30);
        PnlPrincipal.add(TxtFechaNacimiento);

        // direccion dividida en calle numero y colonia
        LblCalle = new JLabel("Calle");
        LblCalle.setBounds(100, 405, 150, 25);
        PnlPrincipal.add(LblCalle);
        TxtCalle = new JTextField();
        TxtCalle.setBounds(100, 430, 160, 30);
        PnlPrincipal.add(TxtCalle);

        LblNumero = new JLabel("Numero");
        LblNumero.setBounds(270, 405, 80, 25);
        PnlPrincipal.add(LblNumero);
        TxtNumero = new JTextField();
        TxtNumero.setBounds(270, 430, 80, 30);
        PnlPrincipal.add(TxtNumero);

        LblColonia = new JLabel("Colonia");
        LblColonia.setBounds(360, 405, 100, 25);
        PnlPrincipal.add(LblColonia);
        TxtColonia = new JTextField();
        TxtColonia.setBounds(360, 430, 90, 30);
        PnlPrincipal.add(TxtColonia);

        // contrasena principal
        LblContrasena = new JLabel("Contrasena *");
        LblContrasena.setBounds(100, 470, 300, 25);
        PnlPrincipal.add(LblContrasena);
        PswContrasena = new JPasswordField();
        PswContrasena.setBounds(100, 495, 350, 30);
        PnlPrincipal.add(PswContrasena);

        // confirmacion de contrasena
        LblConfirmarContrasena = new JLabel("Confirmar contrasena *");
        LblConfirmarContrasena.setBounds(100, 535, 300, 25);
        PnlPrincipal.add(LblConfirmarContrasena);
        PswConfirmarContrasena = new JPasswordField();
        PswConfirmarContrasena.setBounds(100, 560, 350, 30);
        PnlPrincipal.add(PswConfirmarContrasena);

        // telefono de contacto
        LblTelefono = new JLabel("Telefono *");
        LblTelefono.setBounds(100, 600, 300, 25);
        PnlPrincipal.add(LblTelefono);
        TxtTelefono = new JTextField();
        TxtTelefono.setBounds(100, 625, 350, 30);
        PnlPrincipal.add(TxtTelefono);

        // botones de accion final
        BtnGuardar = new JButton("Guardar");
        BtnGuardar.setBounds(120, 675, 140, 35);
        PnlPrincipal.add(BtnGuardar);

        BtnRegresar = new JButton("Regresar");
        BtnRegresar.setBounds(300, 675, 140, 35);
        PnlPrincipal.add(BtnRegresar);

    }

    /**
     * aplica la paleta de colores institucional a los elementos clave
     */
    private void aplicarEstilos() {

        PnlPrincipal.setBackground(new Color(255, 248, 220));

        BtnGuardar.setBackground(new Color(255, 140, 0));
        BtnGuardar.setForeground(Color.WHITE);

        BtnRegresar.setBackground(new Color(200, 0, 0));
        BtnRegresar.setForeground(Color.WHITE);
    }

    /**
     * agrupa los eventos de los botones maneja el proceso completo de registro
     * desde la recoleccion visual la validacion de campos vacios y formatos
     * hasta la instanciacion y envio de dtos a la capa de negocio
     */
    private void agregarEventos() {

        // navegacion de retroceso
        BtnRegresar.addActionListener(e -> {
            new FrmPantallaBienvenida();
            dispose();
        });

        BtnGuardar.addActionListener(e -> {

            String nombres = TxtNombres.getText().trim();
            String apellidoPaterno = TxtApellidoPaterno.getText().trim();
            String apellidoMaterno = TxtApellidoMaterno.getText().trim();
            String fechaNacimiento = TxtFechaNacimiento.getText().trim();
            String calle = TxtCalle.getText().trim();
            String numero = TxtNumero.getText().trim();
            String colonia = TxtColonia.getText().trim();
            String telefono = TxtTelefono.getText().trim();
            String contrasena = new String(PswContrasena.getPassword());
            String confirmar = new String(PswConfirmarContrasena.getPassword());
            String correo = TxtCorreo.getText().trim();
            
            // validacion para asegurar que ningun campo obligatorio quede en blanco
            if (nombres.isEmpty() || apellidoPaterno.isEmpty() || fechaNacimiento.isEmpty()
                    || telefono.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty() || correo.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "debe completar todos los campos obligatorios",
                        "error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // validacion de coincidencia de seguridad
            if (!contrasena.equals(confirmar)) {
                JOptionPane.showMessageDialog(this,
                        "las contrasenas no coinciden",
                        "error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // conversion de formato de texto a objeto localdate para la base de datos
            LocalDate fechaParseada;
            try {
                fechaParseada = LocalDate.parse(fechaNacimiento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "formato de fecha incorrecto use dd/MM/yyyy",
                        "error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // construccion del objeto usuario para credenciales
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setCorreoElectronico(correo);
            usuarioDTO.setPassword(contrasena);
            usuarioDTO.setRol(RolUsuario.CLIENTE);

            // construccion del objeto telefono para metadatos de contacto
            TelefonoDTO telDTO = new TelefonoDTO();
            telDTO.setNumero(telefono);
            telDTO.setEtiqueta("casa");

            // ensamblaje final del objeto cliente maestro
            ClienteDTO dto = new ClienteDTO();
            dto.setNombres(nombres);
            dto.setApellidoPaterno(apellidoPaterno);
            
            // se utiliza operador ternario para manejar campos opcionales asignando null si estan vacios
            dto.setApellidoMaterno(apellidoMaterno.isEmpty() ? null : apellidoMaterno);
            dto.setFechaNacimiento(fechaParseada);
            dto.setCalle(calle.isEmpty() ? null : calle);
            dto.setNumero(numero.isEmpty() ? null : numero);
            dto.setColonia(colonia.isEmpty() ? null : colonia);
            dto.setUsuario(usuarioDTO);
            dto.setTelefonos(Arrays.asList(telDTO));
            
            // inyeccion de dependencias manual y ejecucion del registro en la capa de negocio
            try {
                ConexionBD conexionBD = new ConexionBD();
                IUsuarioDAO usuarioDAO = new UsuarioDAO(conexionBD);
                IClienteDAO clienteDAO = new ClienteDAO(conexionBD);
                IDireccionDAO direccionDAO = new DireccionDAO(conexionBD);
                ITelefonoDAO telefonoDAO = new TelefonoDAO(conexionBD);
                ITelefonoBO telefonoBO = new TelefonoBO(telefonoDAO);
                IUsuarioBO usuarioBO = new UsuarioBO(usuarioDAO);
                IClienteBO clienteBO = new ClienteBO(clienteDAO, direccionDAO, usuarioBO, telefonoBO);

                clienteBO.registrarCliente(dto);

                JOptionPane.showMessageDialog(this,
                        "usuario registrado correctamente\nahora debe iniciar sesion",
                        "registro exitoso",
                        JOptionPane.INFORMATION_MESSAGE);

                new FrmLogin();
                dispose();

            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
