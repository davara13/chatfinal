/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.chatfinal;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class CambiarEstado extends WindowAdapter{
    private String user;
    private String ip;

    public CambiarEstado(String user, String ip) {
        this.user = user;
        this.ip = ip;
    }
    
    @Override
    public void windowClosing(WindowEvent e){
        try{
            Socket misocket =  new Socket("192.168.1.63", 2000);
            Usuario new_user = new Usuario(user, ip, false);
            
            ObjectOutputStream paquete_conexion =  new ObjectOutputStream(misocket.getOutputStream());
            paquete_conexion.writeObject(new_user);
            
            misocket.close();
            
            System.out.println("Usuario " + user + " se desconectó");
            
        }catch(Exception ex){
            
        }
        
    }
    
}
