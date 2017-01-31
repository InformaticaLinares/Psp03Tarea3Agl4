
package t03tarea3chat;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author juan_jesus_pedro
 */
public class HiloDelServidor extends Thread {

    DataInputStream entrada; //para flujo entrada
    DataOutputStream salida;
    private static final String FIN = "*"; //marca de fin de conexion
    Socket socket = null;

    //constructor del hilo
    public HiloDelServidor(Socket sc) {
        socket = sc;
    }

    @Override
    public void run() {
        //actualizamos el número de conexiones
        ServidorChat.lblConexiones.setText("Num Conexiones ahora: " + ServidorChat.NumConexiones);
        //recojo el texto del servidor     
        String texto = ServidorChat.txtServidor.getText();
        //invoco método para crear flujo de entrada y salida
        crearFlujos();
        //envío texto del servidor a todos los clientes
        EnvioMensajes(texto);
        boolean leer = true;
        
        //leo cadenas de texto, mientras no coincidan con la variable FIN
        while (leer) {
            String cadena = "";
            try {
                cadena = entrada.readUTF(); //leo del flujo de entrada del canal

                if (cadena.equals(FIN)) { // el cliente se va = marca FIN                   
                    leer = false; //deje de leer
                    ServidorChat.NumConexiones--; //decrementa la conexiones
                    System.out.println("num " + ServidorChat.NumConexiones);
                    ServidorChat.lblConexiones.setText("Num Conexiones ahora: " + ServidorChat.NumConexiones);
                    cerrarConexion(); //cierra conexion
                } else {
                    //añado el texto del cliente al textarea del servidor
                    ServidorChat.txtServidor.append(cadena + "\n");
                    texto = ServidorChat.txtServidor.getText();
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
     * Obtener los flujos o canales de entrada y salidaServidor.
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
        while (it.hasNext()) {
            Socket ski = (Socket) it.next();
            if (ski == socket) {
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
            //para cada conexión socket creo flujo salidaServidor
            //escribo el texto en el flujo de salidaServidor
            try {
                DataOutputStream salidaServidor = new DataOutputStream(sock.getOutputStream());
                salidaServidor.writeUTF(texto);
            } catch (SocketException e) {
                System.err.println("Error al intentar escribir el el socket");
            } catch (IOException ex) {
                Logger.getLogger(HiloDelServidor.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error de escritura");
            }
        }
    }
}
