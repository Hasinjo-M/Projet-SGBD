
package traitement.requet;

import input.Input;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import table.Table;

public class TraitementRequet extends Thread{

    @Override
    public void run() {
        try {
            BufferedReader userInput = null;
            ObjectOutputStream ObjetOut = null;
            BufferedWriter ServeurOutput = null;   
            Input traitement = new Input();
            Table tableRep = new Table();
            try {
                ObjetOut = new ObjectOutputStream(client.getOutputStream());
                ObjetOut.flush();
                userInput = new BufferedReader(new InputStreamReader(client.getInputStream())); 
                ServeurOutput = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
           
                while (true) {
                    String requet = userInput.readLine();
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
               
            }catch (IOException e) {
                throw e;
            } finally {
                if(ServeurOutput!= null)
                    ServeurOutput.close();
                if (ObjetOut != null)
                    ObjetOut.close();
                if (client != null)
                    client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public TraitementRequet() {
        super();
    }

    public TraitementRequet(Socket client) {
        super();
        this.client = client;
    }
    private Socket client;
}
