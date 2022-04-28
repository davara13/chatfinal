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
public class HiloServerNewUser extends Thread {
    private ServerSocket server;
    private Servidor ser;

    public HiloServerNewUser(ServerSocket server, Servidor ser) {
        this.server = server;
        this.ser = ser;
    }
    
    public void run(){
        while(true){
            Socket userS;
            try {
                userS = server.accept();
                ObjectInputStream flujo_entrada_newU = new ObjectInputStream(userS.getInputStream());
                Usuario new_user = (Usuario) flujo_entrada_newU.readObject();

                String nick_user = new_user.getNick();
                String user_ip = new_user.getIp();
                Boolean estado = new_user.getEstado();

                ser.notificacion("nuevo usuario: " + nick_user + "con ip: " + user_ip + "\n");
                
                ArrayList<Usuario> users = ser.getUsers();
                users.add(new Usuario(nick_user,user_ip,estado));
                
                for(Usuario u: users ){
                    Socket envio_dest = new Socket(u.getIp(), 9191);
                    
                    ObjectOutputStream flujo_salida = new ObjectOutputStream(envio_dest.getOutputStream());
                    flujo_salida.writeObject(users);
                    
                    envio_dest.close();
                }
                
                System.out.println("numero de usuarios: " + users.size());
                
                
                flujo_entrada_newU.close();
                userS.close();
            } catch (IOException ex) {
                Logger.getLogger(HiloServerNewUser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HiloServerNewUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
