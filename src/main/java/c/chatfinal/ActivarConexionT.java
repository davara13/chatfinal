/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.chatfinal;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author david
 */
public class ActivarConexionT extends WindowAdapter{
    
    private String user;
    private String ip;

    public ActivarConexionT(String user, String ip) {
        this.user = user;
        this.ip = ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public void windowOpened(WindowEvent e){
        try{
            Socket misocket =  new Socket("192.168.1.63", 2000);
            Usuario new_user = new Usuario(user, ip, true);
            
            ObjectOutputStream paquete_conexion =  new ObjectOutputStream(misocket.getOutputStream());
            paquete_conexion.writeObject(new_user);
            
            misocket.close();
            
        }catch(Exception ex){
            
        }
    }
}