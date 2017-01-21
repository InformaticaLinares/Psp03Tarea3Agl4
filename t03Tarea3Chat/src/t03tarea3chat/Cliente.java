/*
 *Envia al servidor una cadena de caracteres y muestra el resultado que el envia
terminamos cuando del servidor recibimos el caracter *.
 */
package t03tarea3chat;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juan_pedro_jesus
 */
public class Cliente {

    public static void main(String[] args) {
        String host = "localhost"; //host local
        int puerto = 55000; //puerto de conexion
        String cadena = ""; //para texto que leo del teclado
        String cadenaServer = ""; //texto que recibo del servidor
        try {
            Socket cliente = new Socket(host, puerto); //socket en el puerto
            //flujos de entrada y salida al servidor
            BufferedReader entrada = new BufferedReader(new InputStreamReader(
                    cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
            //Flujo para la entrada estandar
            BufferedReader teclado = new BufferedReader(new InputStreamReader(
                    System.in));
            System.out.println("Escribe un texto: ");
            cadena = teclado.readLine();
            while (true) {
                salida.println(cadena); //escribo en el flujo de salida
                cadenaServer = entrada.readLine(); //leeo del flujo de entrada 
                System.out.println("El texto recibido del servidor es: " + cadenaServer);
                if ("*".equals(cadena)) break;
                System.out.println("Escribe un texto: ");
                cadena = teclado.readLine();                
            }
            
//cierro flujos y socket
            entrada.close();
            salida.close();
            teclado.close();
            cliente.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
