package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import table.Table;

public class Client {
    /*** ServerName et port ***/
    static final String serverName = "localhost";
    static final int serverPort = 9990;

    public static void main(String[] args) throws Exception {
        Socket socClient = null;
        ObjectInputStream ObjectInput = null;
        OutputStreamWriter out = null;
        BufferedWriter userOutput = null;
        Scanner scan = null;
        try {
            socClient = new Socket(serverName, serverPort);
            System.out.println("Socket client: " + socClient);

            out = new OutputStreamWriter(socClient.getOutputStream());

            ObjectInput = new ObjectInputStream(socClient.getInputStream());

            userOutput = new BufferedWriter(out);
            scan = new Scanner(System.in);
            boolean testMessage = false;
            while (true) {
                if(testMessage==false){
                    String requet = scan.nextLine();
                    userOutput.write(requet);
                    userOutput.newLine();
                    userOutput.flush();
                    if (requet.equals("exit")) {
                        System.out.println("Deconnecté");
                        break;
                    }
                }else{
                    System.out.println("nety niditra tato ve");
                    userOutput.newLine();
                    userOutput.flush();
                    testMessage = false; 
                }
                try {
                    Object objrecu = ObjectInput.readObject();
                    if(objrecu instanceof Table table){
                        table.afficheResultat();
                    }else {
                        String message = String.valueOf(objrecu);
                        System.out.println(message);
                    }      
                } catch (Exception k) {
                    System.out.println("Erreur : "+k.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        } finally {

            if (userOutput != null)
                userOutput.close();
            if (scan != null)
                scan.close();
            if (out != null)
                out.close();
            if (ObjectInput != null)
                ObjectInput.close();
           
        }       
    }
}
//Object trans = tableRep;
                    //System.out.println(tableauAEmettre.toString());
                    //create table test ( id:int,Nom:String,Anniversaire:Date )
                    //ObjetOut.writeObject(tableRep);
                    //ObjetOut.flush();
                    //tableRep.afficheResultat();
                    //insert into testmety ( id,Nom,Anniverssaire ) values ( 2,Toavina,23/06/04 )
                    //select . id,Nom from testmety
                    //selects * from testmety