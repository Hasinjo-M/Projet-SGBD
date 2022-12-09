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
    static String serverName = "localhost";
    static int serverPort = 9091;

    public static void main(String[] args) throws Exception {
        Socket socClient = null;
        ObjectInputStream ObjectInput = null;
        OutputStreamWriter out = null;
        BufferedWriter userOutput = null;
        Scanner scan = null;
        try {
            scan = new Scanner(System.in);

            /// Ip du serveur
            System.out.println("Ip du Serveur : ");
            serverName = scan.nextLine();
            /// Port
            System.out.println("Port du Serveur : ");
            serverPort = Integer.parseInt(scan.nextLine());
            /// Socket Client (Socket)
            socClient = new Socket(serverName, serverPort);

            System.out.println("Vous ^etes connecté sur le serveur :" + socClient.getLocalAddress() + " Port : "
                    + socClient.getPort());

            out = new OutputStreamWriter(socClient.getOutputStream());

            ObjectInput = new ObjectInputStream(socClient.getInputStream());

            userOutput = new BufferedWriter(out);

            boolean testMessage = false;

            while (true) {
                System.out.print("Sql>");
                String requet = scan.nextLine();
                userOutput.write(requet);
                userOutput.newLine();
                userOutput.flush();
                if (requet.equals("exit")) {
                    System.out.println("Deconnecté");
                    break;
                }

                try {
                    Object objrecu = ObjectInput.readObject();
                    if (objrecu instanceof Table table) {
                        table.Affichage();
                    } else {
                        String message = String.valueOf(objrecu);
                        System.out.println(message);
                    }
                } catch (Exception k) {
                    System.out.println("Erreur : " + k.getMessage());
                }
            }

        } catch (Exception e) {
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
