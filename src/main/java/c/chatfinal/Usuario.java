/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.chatfinal;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class Usuario implements Serializable {
    private String nick;
    private String ip;
    private Boolean estado;
    private ArrayList<Mensaje> MensajesRepresados;

    public Usuario(String nick, String ip, Boolean estado) {
        this.nick = nick;
        this.ip = ip;
        this.estado = estado;
        MensajesRepresados = new ArrayList<Mensaje>();
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public ArrayList<Mensaje> getMensajesRepresados() {
        return MensajesRepresados;
    }

    public void setMensajesRepresados(ArrayList<Mensaje> MensajesRepresados) {
        this.MensajesRepresados = MensajesRepresados;
    }
    
    
}
