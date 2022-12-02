package serveur;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import traitement.requet.TraitementRequet;

public class Serveur extends Thread{
    
    /*** port ***/
    static final int port = 9990;
    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            while(true){
                Socket client = server.accept();
                new TraitementRequet(client).start();
            }
        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void main(String[] args) throws Exception {
        new Serveur().start();
    }
}