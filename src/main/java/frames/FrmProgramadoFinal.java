/*
 * click nbfsnbhostsystemfilesystemtemplateslicenseslicense-defaulttxt to change this license
 * click nbfsnbhostsystemfilesystemtemplatesclassesclassjava to edit this template
 */
package frames;

import javax.swing.*;
import java.awt.*;
import negocio.DTOs.UsuarioDTO;

/**
 * pantalla que muestra la confirmacion de un pedido programado exitoso
 * informa al cliente el numero de folio generado y el estado inicial
 * del pedido recien creado en la base de datos
 *
 * @author Dell PC
 */
public class FrmProgramadoFinal extends JFrame {

    private JLabel LblTitulo, LblNumeroPedido, LblEstado;
    private JButton BtnInicio;
    private String numeroPedidoReal;

    private UsuarioDTO sesionActual; // mochila con los datos del usuario

    /**
     * constructor de la ventana de finalizacion de pedido
     * recibe el identificador del pedido generado y los datos de la sesion
     * para mostrarlos en pantalla y permitir el retorno al menu principal
     * * @param idPedidoReal el numero de folio asignado al pedido
     * * @param sesion la sesion actual del usuario que realizo la compra
     */
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

    /**
     * inicializa y posiciona las etiquetas de texto y el boton de inicio
     * muestra el mensaje de exito concatenando el folio real del pedido
     * y asigna el evento para regresar a la pantalla de opciones del usuario
     */
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

        // evento para cerrar la confirmacion y volver al menu del cliente
        BtnInicio.addActionListener(e -> {
            new FrmMenuUsuario(sesionActual); 
            dispose();
        });
    }
}
