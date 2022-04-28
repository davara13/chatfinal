/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.chatfinal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class HiloClienteNewUser extends Thread {
    private ServerSocket server;
    private Cliente cli;

    public HiloClienteNewUser(ServerSocket server, Cliente ser) {
        this.server = server;
        this.cli = ser;
    }
    
    public void  run(){
        while(true){
            Socket userC;
            try {
                userC = server.accept();
                ObjectInputStream flujo_entrada_newU = new ObjectInputStream(userC.getInputStream());
                ArrayList<Usuario> new_user = (ArrayList) flujo_entrada_newU.readObject();

                cli.actualizarUsuarios(new_user);
                
                userC.close();
                
                
            } catch (IOException ex) {
                Logger.getLogger(HiloServerNewUser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HiloServerNewUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
