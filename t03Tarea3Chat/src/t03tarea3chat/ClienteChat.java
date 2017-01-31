package t03tarea3chat;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class ClienteChat extends javax.swing.JFrame {

    ServerSocket servidor;
    Socket cliente;
    HiloDelServidor hilo;
    String host;
    boolean repetir = true;
    int puerto; //puerto de conexion
    String cadena; //para texto que leo del teclado
    String cadenaServer;
    BufferedReader teclado;
    Socket socket = null;
    //flujos de entrada y salida
    DataInputStream entrada;
    DataOutputStream salida;
    static String nombre;
    private static final String FIN = "*";

    public ClienteChat(Socket sc, String nom) {
        super("Cliente que se conecta: " + nom);
        initComponents();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        socket = sc;
        this.nombre = nom;
        try {
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
            String texto = nombre + " se une a la conversación";
            salida.writeUTF(texto);

        } catch (IOException ex) {
            System.err.println("Error de entrada/salida" + ex.toString());
            System.exit(0);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtCliente = new javax.swing.JTextArea();
        btnSalir = new javax.swing.JButton();
        txtMensajeCliente = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(51, 255, 204));
        getContentPane().setLayout(null);

        txtCliente.setEditable(false);
        txtCliente.setColumns(20);
        txtCliente.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtCliente.setRows(5);
        jScrollPane1.setViewportView(txtCliente);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(51, 61, 535, 302);

        btnSalir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir);
        btnSalir.setBounds(600, 60, 70, 23);
        getContentPane().add(txtMensajeCliente);
        txtMensajeCliente.setBounds(51, 21, 535, 20);

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEnviar);
        btnEnviar.setBounds(600, 20, 70, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed

        String texto = " ------- " + nombre + " sale del chat" + " ------- ";
        try {
            salida.writeUTF(texto); //escribo en el canal
            salida.writeUTF(FIN);

        } catch (IOException ex) {
            System.err.println("Error al desconectar el cliente" + ex.toString());
        }
        repetir = false;
        System.exit(0);

    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed

        String texto = nombre + ": " + txtMensajeCliente.getText();
        try {
            txtMensajeCliente.setText(""); //limpio area de entrada de texto
            salida.writeUTF(texto);
        } catch (IOException ex) {
            System.err.println("Error cliente al enviar el mensaje" + ex.toString());
        }
    }//GEN-LAST:event_btnEnviarActionPerformed

    public static void main(String args[]) throws IOException {
        String nick = JOptionPane.showInputDialog("Escribe tu nick");
        Socket socket = null;

        try {
            //socket conexión al server en el puerto especificado       
            socket = new Socket("localhost", 55000);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Imposible conectar con servidor"
                    + ex.getMessage(), "Mensaje de error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        if (!nick.trim().equals("")) {
            ClienteChat cliente = new ClienteChat(socket, nick);

            cliente.setVisible(true);
            cliente.setSize(700, 420);
            cliente.ejecutar();
        } else {
            System.out.println("Es obligatorio identificarse con un nick o nombre");
        }

    }

    public void ejecutar() {
        String texto = "";

        while (repetir) {
            try {
                texto = entrada.readUTF(); //leo mensajes de stream de entrada
                txtCliente.setText(texto);
            } catch (IOException ex) {//error si el servidor se cierra
                JOptionPane.showMessageDialog(null, "Imposible conectar con servidor");
                System.err.println(ex.toString() + JOptionPane.ERROR_MESSAGE);
                repetir = false; //sale del bucle
            }
        }
        try {

            socket.close();
            System.exit(0);
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtCliente;
    private javax.swing.JTextField txtMensajeCliente;
    // End of variables declaration//GEN-END:variables
}
