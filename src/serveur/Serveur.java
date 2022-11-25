package serveur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import input.Input;
import table.Table;

public class Serveur {
    static final int port = 9999;

    public static void main(String[] args) throws Exception {
        ServerSocket server = null;
        Socket client = null;
        BufferedReader userInput = null;
        ObjectOutputStream ObjetOut = null;
        Input traitement = new Input();
        Table tableRep = new Table();
        try {
            server = new ServerSocket(9999);
            client = server.accept();
            int[] tableauAEmettre = { 7, 8, 9 };

            ObjetOut = new ObjectOutputStream(client.getOutputStream());
            ObjetOut.flush();

            userInput = new BufferedReader(new InputStreamReader(client.getInputStream())); // System.in
            while (true) {
                String requet = userInput.readLine();
                System.out.println(requet);
                if (requet.equals("exit")) {
                    break;
                }
                try {
                    tableRep = traitement.output(requet);
                    Object trans = tableRep;
                    ObjetOut.writeObject(tableauAEmettre);
                    ObjetOut.flush();
                    tableRep.afficheResultat();
                } catch (Exception e) {
                    e.printStackTrace();
                    // System.out.println(e.getMessage() + " etooo");
                    break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (userInput != null)
                userInput.close();
            if (ObjetOut != null)
                ObjetOut.close();
            if (client != null)
                client.close();
        }

    }
}