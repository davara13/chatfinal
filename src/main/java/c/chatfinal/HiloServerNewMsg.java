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
                
                int i = obtenerI(users,nick_destino);
                
                if(users.get(i).getEstado()){
                    enviarMensaje(new_msg,users.get(i).getIp());
                    misocket.close();
                }else{
                    ArrayList<Mensaje> MensajesRepresados = users.get(i).getMensajesRepresados();
                    MensajesRepresados.add(new_msg);
                    users.get(i).setMensajesRepresados(MensajesRepresados);
                }
                
                if(ser.getOffLineUsers().size()>0){
                    enviarMensajesRespresados(ser.getOffLineUsers(), ser.getUsers());
                }
                
            } catch (IOException ex) {
                Logger.getLogger(HiloServerNewUser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HiloServerNewUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void enviarMensaje(Mensaje msg, String ip){
        Socket envio_dest;
        try {
            envio_dest = new Socket(ip, 9090);
            ObjectOutputStream flujo_salida = new ObjectOutputStream(envio_dest.getOutputStream());
            flujo_salida.writeObject(msg);

            flujo_salida.close();
            envio_dest.close();
            
        } catch (IOException ex) {
            Logger.getLogger(HiloServerNewMsg.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void enviarMensajesRespresados(ArrayList<Usuario> usO, ArrayList<Usuario> us){
        for(int i = 0; i<usO.size(); i++){
            for(int j=0; j<us.size();i++){
                if(usO.get(i).getNick().equals(us.get(j).getNick())){
                    if(usO.get(i).getEstado() != us.get(j).getEstado()){
                        ArrayList<Mensaje> msgs = usO.get(i).getMensajesRepresados();
                        for(int k = 0; k <msgs.size(); k++ ){
                            enviarMensaje(msgs.get(k),us.get(obtenerI(us,msgs.get(k).getNick_destino())).getIp());
                        }
                    }
                }
            }
        }
    }
    
    public int obtenerI(ArrayList<Usuario> users, String nd) {
        int i;
        for (i = 0; i < users.size(); i++) {
            if (users.get(i).getNick().equals(nd)) {
                break;
            }
        }
        
        return i;
    }
    
}
