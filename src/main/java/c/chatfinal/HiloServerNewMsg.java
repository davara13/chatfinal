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
public class HiloServerNewMsg extends Thread {
    private ServerSocket server;
    private Servidor ser;

    public HiloServerNewMsg(ServerSocket server, Servidor ser) {
        this.server = server;
        this.ser = ser;
        System.out.println("mensaje!");
    }
    
    public void run(){
        while(true){
            Socket userS;
            try {
                Socket misocket = server.accept();
                //---- da
                ObjectInputStream flujo_entrada = new ObjectInputStream(misocket.getInputStream());
                Mensaje new_msg = (Mensaje)flujo_entrada.readObject();
                
                String nick_fuente = new_msg.getNick_fuente();
                String nick_destino = new_msg.getNick_destino();
                String msg = new_msg.getMsg();
                System.out.println(msg);
                System.out.println("nick_destino: " + nick_destino);

                ser.notificacion(nick_fuente + " : " + msg + " para: " + nick_destino + "\n");
                ArrayList<Usuario> users = ser.getUsers();
                String ip="";
                for(Usuario u:users){
                    if(u.getNick().equals(nick_destino)){
                        ip = u.getIp();
                        break;
                    }
                }
                System.out.println(ip);
                
                Socket envio_dest = new Socket(ip, 9090);

                ObjectOutputStream flujo_salida = new ObjectOutputStream(envio_dest.getOutputStream());
                flujo_salida.writeObject(new_msg);

                flujo_salida.close();
                envio_dest.close();
                misocket.close();
                
            } catch (IOException ex) {
                Logger.getLogger(HiloServerNewUser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HiloServerNewUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
