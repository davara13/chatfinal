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
                
                boolean existe = false;
                ArrayList<Usuario> users = ser.getUsers();
                int i;
                
                for(i=0; i < users.size() ; i++){
                    if(users.get(i).getNick().equals(nick_user)){
                        existe = true;
                        break;
                    }
                }

                if (estado == false) {
                    ser.notificacion("usuario: " + nick_user + " se desconectó \n");
                    if (existe) {
                        users.get(i).setEstado(estado);
                        ser.getUsers().get(i).setEstado(estado);
                        ser.agregarOffline(new_user);
                    }
                } else {
                    if(existe){
                        ser.notificacion("Usuario: " + nick_user + " se conectó con ip: " + user_ip + "\n");
                        users.get(i).setIp(user_ip);
                        ser.getUsers().get(i).setIp(user_ip);
                        users.get(i).setEstado(estado);
                        ser.getUsers().get(i).setEstado(estado);
                    }else{
                        ser.notificacion("Nuevo Usuario: " + nick_user + " se conectó con ip: " + user_ip + "\n");
                        users.add(new Usuario(nick_user, user_ip, estado));
                    }
                }
                
                ArrayList<Usuario> usersActivos = new ArrayList<Usuario>();
                for(Usuario u: users ){
                    if(u.getEstado()){
                        usersActivos.add(u);
                    } 
                }
                
                for(Usuario u: users ){
                    Socket envio_dest = new Socket(u.getIp(), 9191);
                    
                    ObjectOutputStream flujo_salida = new ObjectOutputStream(envio_dest.getOutputStream());
                    flujo_salida.writeObject(usersActivos);
                    
                    envio_dest.close();
                }
                
                System.out.println("numero de usuarios: " + users.size());
                
                for (int k = 0; k < users.size(); k++) {
                    System.out.println("usuario: " + users.get(k).getNick() + "  tiene estado: " + users.get(k).getEstado());
                }
                
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
