/*
 * Hilo que transforma en mayusculas la cadena de caractes recibida
Termina cuando recibe el caracter *
 */
package t03tarea3chat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juan_jesus_pedro
 */
public class HiloDelServidor extends Thread {

     DataInputStream entrada; //para flujo entrada
    DataOutputStream salida;
    private static final String FIN = "ADIOS"; //marca de fin de conexion
    Socket socket = null;
   
    //constructor del hilo
    public HiloDelServidor(Socket sc) {
        socket = sc;
    }

//    BufferedReader entrada; //para flujo de lectura en el canal
//    PrintWriter salida;  //para flujo de escritura en el canal
//    Socket socket = null;
//    ArrayList a = new ArrayList();
//    ServidorChat sc= new ServidorChat();
//
//    //Constructor con el socket y los flujos de entrada y salida al canal
//    public HiloDelServidor(Socket s) {
//        try {
//            socket = s;
//            //flujos de entrada y salida
//            entrada = new BufferedReader(new InputStreamReader(
//                    socket.getInputStream()));
//            salida = new PrintWriter(socket.getOutputStream(), true);
//        } catch (IOException ex) {
//            Logger.getLogger(HiloDelServidor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    @Override
    public void run() {
   ServidorChat.lblConexiones.setText("Num Conexiones ahora: " + ServidorChat.NumConexiones);
        String texto = ServidorChat.jTextArea1.getText();
        crearFlujos();
        EnvioMensajes(texto);
        boolean leer = true;
        while (leer) {
            String cadena = "";
            try {
                cadena = entrada.readUTF(); //leo del flujo de entrada del canal

                if (cadena.equals(FIN)) { // el cliente se va = marca FIN                   
                    leer = false; //deje de leer
                    ServidorChat.NumConexiones--; //decrementa la conexiones
                    System.out.println("num "+ServidorChat.NumConexiones);
                    ServidorChat.lblConexiones.setText("Num Conexiones ahora: " + ServidorChat.NumConexiones);
                    cerrarConexion(); //cierra conexion
                } else {
                    //añado el texto del cliente al textarea del servidor
                    ServidorChat.jTextArea1.append(cadena + "\n");
                    texto = ServidorChat.jTextArea1.getText();
                    //envio contenido del texArea a todos los conectado
                    EnvioMensajes(texto);
                }

            } catch (IOException ex) {
                System.err.println("Error de lectura" + ex.toString());
                break;
            }
        }
        
    }
    /**
     * Obtener los flujos o canales de entrada y salida.
     */
    private void crearFlujos() {
        try {            //crea el flujo entrada 
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Error de entrada salida en el hilo" + ex.toString());
        }
    }
    
    
     private void cerrarConexion() {
        
        Iterator<Socket> it = ServidorChat.almacenSockets.iterator();
     //recorro arraylist para localizar elemento
        while (it.hasNext()){
            Socket ski = (Socket)it.next();
            if (ski==socket){
                it.remove();
            }
        }
        try { 
         
            salida.close();
            entrada.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(HiloDelServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
private void EnvioMensajes(String texto) {
        //recorrer ArrayList sockets para enviar los mensajes       
        System.out.println("Estoy en EnvioMensajes, numConexiones = " + ServidorChat.NumConexiones);
        for (Socket sock : ServidorChat.almacenSockets) {
            //para cada conexión socket creo flujo salida
            //escribo el texto en el flujo de salida
            try {
                DataOutputStream salida = new DataOutputStream(sock.getOutputStream());
                salida.writeUTF(texto);
            } catch (SocketException e) {
                System.err.println("Error al intentar escribir el el socket");
            } catch (IOException ex) {
                Logger.getLogger(HiloDelServidor.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error de escritura");
            }
        }
}
}
