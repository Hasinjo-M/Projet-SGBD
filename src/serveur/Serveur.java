package serveur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import input.Input;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import table.Table;

public class Serveur {
    /*** port ***/
    static final int port = 9990;

    public static void main(String[] args) throws Exception {
        ServerSocket server = null;
        Socket client = null;
        BufferedReader userInput = null;
        ObjectOutputStream ObjetOut = null;
        BufferedWriter ServeurOutput = null;
        
        Input traitement = new Input();
        Table tableRep = new Table();
        try {
            /**** serveur ****/
            server = new ServerSocket(port);
            
            client = server.accept();
          
            ObjetOut = new ObjectOutputStream(client.getOutputStream());
            ObjetOut.flush();

            userInput = new BufferedReader(new InputStreamReader(client.getInputStream())); 
            ServeurOutput = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            
            boolean tesExepetion = false;
            while (true) {
                String requet = userInput.readLine();
                if (requet.equals("exit")) {
                    break;
                }
                try {
                    tableRep = traitement.output(requet);   
                    ObjetOut.writeObject(tableRep);
                    ObjetOut.flush();
                } catch (Exception k) {   
                    String message = k.getMessage();
                    ObjetOut.writeObject(message);
                    ObjetOut.flush();
                }
                            
            }

        } catch (Exception e) {
            System.out.println(e.getMessage() + " eto " + e.getCause());
            e.printStackTrace();
            throw e;
        } finally {
            if(ServeurOutput!= null)
                ServeurOutput.close();
            if (ObjetOut != null)
                ObjetOut.close();
            if (client != null)
                client.close();
        }

    }
}