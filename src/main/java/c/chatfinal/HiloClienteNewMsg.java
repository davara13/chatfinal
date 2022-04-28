/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.chatfinal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class HiloClienteNewMsg  extends Thread {
    private ServerSocket server;
    private Cliente cli;

    public HiloClienteNewMsg(ServerSocket server, Cliente ser) {
        this.server = server;
        this.cli = ser;
    }
    
    public void  run(){
        while (true) {
            Socket cliente;
            try {
                cliente = server.accept();
                ObjectInputStream flujoEntrada = new ObjectInputStream(cliente.getInputStream());
                Mensaje new_msg = (Mensaje) flujoEntrada.readObject();
                new_msg.desencriptar();
                cli.mostrarMensaje(new_msg.getNick_fuente() + ": " + new_msg.getMsg() + "\n");
            } catch (IOException ex) {
                Logger.getLogger(HiloClienteNewMsg.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HiloClienteNewMsg.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
