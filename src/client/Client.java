package client;

import java.io.BufferedWriter;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import table.Table;

public class Client {
    static final String serverName = "localhost";
    static final int serverPort = 9999;

    public static void main(String[] args) throws Exception {
        Socket socClient = null;
        ObjectInputStream ObjectInput = null;
        OutputStreamWriter out = null;
        BufferedWriter userOutput = null;
        Scanner scan = null;
        Table reponse = new Table();
        try {
            socClient = new Socket(serverName, serverPort);
            System.out.println("Socket client: " + socClient);

            out = new OutputStreamWriter(socClient.getOutputStream());

            ObjectInput = new ObjectInputStream(socClient.getInputStream());

            userOutput = new BufferedWriter(out);
            scan = new Scanner(System.in);
            while (true) {
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
                    int[] tableauRecu = (int[]) objrecu;
                    // reponse = (Table) objrecu;
                    // reponse.afficheResultat();
                    System.out.println("Client recoit: " + Arrays.toString(tableauRecu));
                } catch (Exception e) {
                    e.printStackTrace();
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
            if (socClient != null)
                socClient.close();

        }

        // Socket socket = new Socket(serverName, serverPort);
        // System.out.println("Socket client: " + socket);

    }
}
