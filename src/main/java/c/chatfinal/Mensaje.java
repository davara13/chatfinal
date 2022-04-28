/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.chatfinal;

import java.io.Serializable;

/**
 *
 * @author ana
 */
public class Mensaje implements Serializable {
    
    private String nick_fuente;
    private String nick_destino;
    private String msg;
    
    private final char[] alfa = new char[38];

    public Mensaje(String nick_fuente, String nick_destino, String msg) {
        this.nick_fuente = nick_fuente;
        this.nick_destino = nick_destino;
        this.msg = msg;
        llenarAlfabeto();
    }

    Mensaje() {
        this.nick_fuente = "";
        this.nick_destino = "";
        this.msg = "";
        llenarAlfabeto();
    }

    public String getNick_fuente() {
        return nick_fuente;
    }

    public void setNick_fuente(String nick_fuente) {
        this.nick_fuente = nick_fuente;
    }

    public String getNick_destino() {
        return nick_destino;
    }

    public void setNick_destino(String nick_destino) {
        this.nick_destino = nick_destino;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public void encriptar() {
        char[] encrip = this.getMsg().toCharArray();
        String enc = "";

        for (int i = 0; i < encrip.length; i++) {
            int n = encrip[i];
            if (n >= 97 && n <= 110) {
                enc = enc + alfa[n - 94];
            } else if ((int) '�' == n) {
                enc = enc + alfa[17];
            } else if (n >= 111 && n <= 122) {
                enc = enc + alfa[n - 93];
            } else if (n >= 48 && n <= 57) {
                enc = enc + alfa[(n - 18) % 38];
            } else if ((int) '-' == n) {
                enc = enc + alfa[2];
            } else if ((int) ' ' == n) {
                enc = enc + ' ';
            }
        }
        
        this.setMsg(enc);
    }
    
    public void desencriptar(){
        char[] encrip = this.getMsg().toCharArray();
        String enc = "";

        for (int i = 0; i < encrip.length; i++) {
            int n = encrip[i];
            if (n >= 97 && n <= 110) {
                enc = enc + alfa[(n - 62) % 38];
            } else if ((int) '�' == n) {
                enc = enc + alfa[11];
            } else if (n >= 111 && n <= 122) {
                enc = enc + alfa[n - 99];
            } else if (n >= 48 && n <= 57) {
                enc = enc + alfa[n - 24];
            } else if ((int) '-' == n) {
                enc = enc + alfa[34];
            } else if ((int) ' ' == n) {
                enc = enc + ' ';
            }
        }
        
        this.setMsg(enc);
    }
    
    private void llenarAlfabeto(){
        
       for(int i = 0; i < 27; i++){
           if(Character.compare((char)(i+97),'n')==0){
               alfa[i] = (char)(i+97);
               alfa[i+1] = '�';
               i = i+2;
            }if(Character.compare((char)(i+97),'n')>0){
               alfa[i] = (char)(i+96);
            }else{
               alfa[i] = (char)(i+97);
           }
       }
       
       for(int i = 27; i < 38; i++){
           alfa[i] = (char)(21+i);
       }
       
       alfa[37] = '-';
    }
    
    
}
