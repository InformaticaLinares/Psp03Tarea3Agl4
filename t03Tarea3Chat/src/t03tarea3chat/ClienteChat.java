/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t03tarea3chat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author pedro
 */
public class ClienteChat extends javax.swing.JFrame {

    ServerSocket servidor;
    Socket cliente;
    HiloDelServidor hilo;
    String host;
     boolean repetir = true;
    int puerto; //puerto de conexion
    String cadena; //para texto que leo del teclado
    String cadenaServer;
//    BufferedReader entrada;
//    PrintWriter salida;
    BufferedReader teclado;
    Socket socket = null;
    //flujos de entrada y salida
    DataInputStream entrada;
    DataOutputStream salida;
    static String nombre;
 

    public JTextArea getjTextArea1() {
        return jTextArea1;
    }

    public void setjTextArea1(JTextArea jTextArea1) {
        this.jTextArea1 = jTextArea1;
    }

    /**
     * Creates new form ServidorChat
     */
    public ClienteChat(Socket sc, String nom) {
        socket = sc;
        this.nombre = nom;
         try {
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
            String texto = nombre+ " se une a la conversación";
            salida.writeUTF(texto);

        } catch (IOException ex) {
            System.err.println("Error de entrada/salida" + ex.toString());
            System.exit(0);
        }
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 255, 204));
        getContentPane().setLayout(null);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(51, 61, 535, 302);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(631, 61, 63, 23);
        getContentPane().add(jTextField1);
        jTextField1.setBounds(51, 21, 535, 20);

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEnviar);
        btnEnviar.setBounds(631, 20, 63, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       
         //   System.out.println("He pulsado desconectar");
            String texto = " ------- " + nombre + " sale del chat"+" ------- ";
            try {
                salida.writeUTF(texto); //escribo en el canal
                salida.writeUTF("ADIOS");              

            } catch (IOException ex) {
                System.err.println("Error al desconectar el cliente" + ex.toString());
            }
          repetir = false;
            System.exit(0);
           
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
//        this.cadena = this.jTextField1.getText();
//        this.cadena = ""+this.cadena;
//        // this.jTextArea1.setText(this.cadena);
//        this.salida.println(""+this.cadena);
String texto = nombre + ": " + jTextField1.getText();
            try {
                jTextField1.setText(""); //limpio area de entrada de texto
                salida.writeUTF(texto);
            } catch (IOException ex) {
                System.err.println("Error cliente al enviar el mensaje" + ex.toString());
            }
    }//GEN-LAST:event_btnEnviarActionPerformed

    /**
     * @param args the command line arguments
     */
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
            cliente.ejecutar();
        } else {
            System.out.println("Es obligatorio identificarse con un nick o nombre");
        }
        
//        ClienteChat clientechat = new ClienteChat();
//        clientechat.setVisible(true);
//        clientechat.host = "localhost"; //host local
//        clientechat.puerto = 55000; //puerto de conexion
//        clientechat.cadena = ""; //para texto que leo del teclado
//        clientechat.cadenaServer = ""; //texto que recibo del servidor
//        try {
//            clientechat.cliente = new Socket(clientechat.host, clientechat.puerto); //socket en el puerto
//            //flujos de entrada y salida al servidor
//            clientechat.entrada = new BufferedReader(new InputStreamReader(
//                    clientechat.cliente.getInputStream()));
//            clientechat.salida = new PrintWriter(clientechat.cliente.getOutputStream(), true);
//            //Flujo para la entrada estandar
//            clientechat.teclado = new BufferedReader(new InputStreamReader(
//                    System.in));
//            clientechat.jTextArea1.setText("Cliente entra al chat: ");
//            clientechat.cadena = clientechat.jTextField1.getText();
//            
//            clientechat.jTextArea1.setText("El texto recibido del servidor es: " + clientechat.cadenaServer + "\n");
//           
//             while (true) {
//                 clientechat.jTextArea1.setText("El texto recibido del servidor es: " + clientechat.cadenaServer + "\n");
//           
//                clientechat.cadenaServer = clientechat.entrada.readLine(); //leo del flujo de entrada 
//                
//                if ("*".equals(clientechat.cadena)) {
//                    break;
//                }
//                clientechat.cadena = clientechat.jTextField1.getText();
//            }
//
////cierro flujos y socket
//            clientechat.entrada.close();
//            clientechat.salida.close();
//            clientechat.teclado.close();
//            clientechat.cliente.close();
//        } catch (IOException ex) {
//            Logger.getLogger(ClienteChat.class.getName()).log(Level.SEVERE, null, ex);
//        }

        /* Create and display the form */
    }
    
    public void ejecutar() {
        String texto = "";

        while (repetir) {
            try {
                if (entrada.available()>0) {
                    texto = entrada.readUTF(); //leo mensajes de stream de entrada
                }
                jTextArea1.setText(texto);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
