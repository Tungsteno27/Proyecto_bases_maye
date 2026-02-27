/*
 * click nbfsnbhostsystemfilesystemtemplateslicenseslicense-defaulttxt to change this license
 * click nbfsnbhostsystemfilesystemtemplatesclassesclassjava to edit this template
 */
package frames;
import javax.swing.*;
import java.awt.*;
import negocio.DTOs.UsuarioDTO;

/**
 * pantalla que notifica al cliente que su pedido no pudo ser procesado
 * se muestra cuando el usuario alcanza el limite maximo de pedidos activos
 * bloqueando la creacion de nuevos pedidos hasta que se libere espacio
 *
 * @author Dell pc
 */
public class FrmProgramadoFallido extends JFrame {

    private JLabel LblTitulo;
    private JLabel LblMensaje;
    private JButton BtnInicio;
    private UsuarioDTO sesionActual;

    /**
     * constructor principal de la ventana de error de pedido
     * configura las dimensiones y el comportamiento basico de la ventana
     * asi como el color de fondo institucional
     * * @param sesion la sesion actual del cliente para poder regresar a su menu
     */
    public FrmProgramadoFallido(UsuarioDTO sesion) {
        this.sesionActual = sesion;

        setTitle("Pedido Fallido");
        setSize(500, 420);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(255, 248, 220));

        inicializarComponentes();

        setVisible(true);
    }

    /**
     * inicializa y posiciona las etiquetas de texto y el boton de la interfaz
     * muestra la justificacion del fallo al cliente y asigna la accion
     * correspondiente para devolverlo a su panel principal
     */
    private void inicializarComponentes() {

        // titulo de la advertencia
        LblTitulo = new JLabel("Pedido Fallido ❌");
        LblTitulo.setBounds(120, 60, 300, 40);
        LblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        LblTitulo.setForeground(new Color(200, 0, 0));
        add(LblTitulo);

        // mensaje explicativo de la regla de negocio limitante
        LblMensaje = new JLabel("Límite alcanzado."
                + "Debes esperar a que un pedido sea entregado "
                + "o cancelado para poder continuar.");
        LblMensaje.setBounds(60, 150, 380, 80);
        LblMensaje.setFont(new Font("Arial", Font.PLAIN, 16));
        add(LblMensaje);

        // boton de retorno
        BtnInicio = new JButton("Inicio");
        BtnInicio.setBounds(170, 270, 150, 45);
        BtnInicio.setBackground(new Color(200, 0, 0));
        BtnInicio.setForeground(Color.WHITE);
        add(BtnInicio);

        // evento que cierra esta alerta y devuelve al cliente a su menu
        BtnInicio.addActionListener(e -> {
            new FrmMenuUsuario(sesionActual);
            dispose();
        });
    }
}
