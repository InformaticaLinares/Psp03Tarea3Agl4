/*
 * Hilo que transforma en mayusculas la cadena de caractes recibida
Termina cuando recibe el caracter *
 */
package t03tarea3chat;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juan_jesus_pedro
 */
public class HiloDelServidor extends Thread {

    BufferedReader entrada; //para flujo de lectura en el canal
    PrintWriter salida;  //para flujo de escritura en el canal
    Socket socket = null;

    //Constructor con el socket y los flujos de entrada y salida al canal
    public HiloDelServidor(Socket s) {
        try {
            socket = s;
            //flujos de entrada y salida
            entrada = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(HiloDelServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            String cadena = "";
            while (!cadena.trim().equals("*")) {
                try {
                    System.out.println("Socket:" + socket.toString());
                    cadena = entrada.readLine(); //leo la linea de la cadena
                    //convierto cadena a mayusculas y la escribo en el flujo de salida
                    salida.println(cadena.trim().toUpperCase());
                } catch (IOException ex) {
                    Logger.getLogger(HiloDelServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("FIN");
            entrada.close();
            salida.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(HiloDelServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
