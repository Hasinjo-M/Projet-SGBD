package serveur;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import traitement.requet.TraitementRequet;

public class Serveur extends Thread{
    
    /*** port ***/
    static int port = 9091;
    @Override
    public void run() {
        try {
            Scanner scan = null;
            scan = new Scanner(System.in);
            System.out.println("Port du Serveur : ");
            port = Integer.parseInt(scan.nextLine()) ;
            ServerSocket server = new ServerSocket(port);
            System.out.println("Serveur :" + server.getLocalSocketAddress());
            while(true){
                Socket client = server.accept();
                System.out.println("Client : "+client.getLocalAddress() + " connecté ");
                new TraitementRequet(client).start();
                
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) throws Exception {
        new Serveur().start();
    }
}