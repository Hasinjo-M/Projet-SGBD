
package traitement.requet;

import exceptions.MessageConfirmation;
import exceptions.MessageErreur;
import input.Input;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import table.Table;

public class TraitementRequet extends Thread{
    
    private void transfereMesage(ObjectOutputStream ObjetOut, String message ) throws IOException{
       ObjetOut.writeObject(message);
       ObjetOut.flush();
    }
    @Override
    public void run() {
        try {
            BufferedReader userInput = null;
            ObjectOutputStream ObjetOut = null;
            BufferedWriter ServeurOutput = null;   
            Input traitement = new Input();
            try {
                ObjetOut = new ObjectOutputStream(client.getOutputStream());
                ObjetOut.flush();
                userInput = new BufferedReader(new InputStreamReader(client.getInputStream())); 
                ServeurOutput = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
           
                while (true) {
                    String requet = userInput.readLine();
                    try {
                    /// transfere de l'objet par ObjectOutputStream vers Client
                        Table  tableRep = traitement.output(requet);   
                        ObjetOut.writeObject(tableRep);
                        ObjetOut.flush();
                    } catch (MessageConfirmation mc){
                    ///  transfere du message de confirmation 
                        transfereMesage(ObjetOut, mc.getMessage());
                    }catch (MessageErreur me){
                    ///  transfere du message de confirmation 
                        transfereMesage(ObjetOut, me.getMessage());
                    }
                    catch (Exception k) {   
                        transfereMesage(ObjetOut, k.getMessage());
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
            System.out.println(e.getMessage());
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
