package t03tarea3chat;

import java.awt.Color;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.*;
import javax.swing.JFrame;

public class ServidorChat extends javax.swing.JFrame {

    static ServerSocket servidor;
    Socket cliente;
    HiloDelServidor hilo;
    static int NumConexiones = 0;
    static int NumActuales = 0;
    static final int MAX = 10;
    static ArrayList<Socket> almacenSockets = new ArrayList<>();

    public ServidorChat() {
        super("Servidor de Chat"); //título
        initComponents();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //Que no se cierre con la X
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblConexiones = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtServidor = new javax.swing.JTextArea();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 255));
        getContentPane().setLayout(null);

        lblConexiones.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblConexiones.setText("Número de conexiones activas: 0");
        getContentPane().add(lblConexiones);
        lblConexiones.setBounds(51, 33, 181, 15);

        txtServidor.setEditable(false);
        txtServidor.setColumns(20);
        txtServidor.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtServidor.setRows(5);
        jScrollPane1.setViewportView(txtServidor);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(51, 59, 535, 333);

        btnSalir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir);
        btnSalir.setBounds(503, 30, 80, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed

        try {
            servidor.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    public static void main(String args[]) throws IOException {

        ServidorChat server = new ServidorChat(); //pantalla del servidor
        server.setBounds(200, 200, 700, 500);
        server.setVisible(true);
        //inicializo el número de conexiones a 0
        lblConexiones.setText("Número de conexiones activas: " + 0);
        ejecutaServidor();  //servidor comienza su trabajo
    }

    private static void ejecutaServidor() {

        try {
            servidor = new ServerSocket(55000);
            System.out.println("Servidor en marcha");
        } catch (IOException ex) {
            Logger.getLogger(ServidorChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        //servidor en ejecución permanente mientras no pulse boton salir
        while (true) {
            //el servidor espera conexion cliente y crea socket cuando se conecta
            Socket socket = new Socket();
            try {
                socket = servidor.accept();//acepto conexion
            } catch (SocketException se) {
                break; // salgo bucle
            } catch (IOException ex) {
                Logger.getLogger(ServidorChat.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (NumConexiones < MAX) {//si no llego al maximo de conexiones permitidas                
                NumConexiones++; //aumento el contador de número de conexiones 
                almacenSockets.add(socket); //alamacenamos el socket en el ArrayList                
            }
            //lanzo el hilo que gestiona los mensajes del cliente que se ha conectado
            HiloDelServidor hilo = new HiloDelServidor(socket);
            hilo.start();

        }
        //compruebo servidor cerrado, si no fue cerrado primero
        if (!servidor.isClosed()) {
            lblConexiones.setForeground(Color.red);
            lblConexiones.setText("Nº conexiones establecidas: " + NumConexiones);
            try {
                servidor.close();
            } catch (IOException ex) {
                Logger.getLogger(ServidorChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalir;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lblConexiones;
    public static javax.swing.JTextArea txtServidor;
    // End of variables declaration//GEN-END:variables
}
