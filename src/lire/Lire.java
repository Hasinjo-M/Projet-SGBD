package lire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Lire {
    public ArrayList<String> nomColumn(String url) throws Exception {
        ArrayList<String> nomCol = new ArrayList<>();
        File file = new File(url);
        if (!file.exists()) {
            throw new Exception("Table " + url + " n'existe pas ");
        }

        FileReader read = new FileReader(file);
        BufferedReader buffer = new BufferedReader(read);
        String output = buffer.readLine();
        String coll = output;
        String[] allColm = coll.split(",");
        for (int i = 0; i < allColm.length; i++) {
            nomCol.add(allColm[i]);
        }
        return nomCol;

    }

    public ArrayList<ArrayList<String>> Data(String url) throws Exception {
        ArrayList<ArrayList<String>> reponse = new ArrayList<>();
        File file = new File(url);
        if (!file.exists()) {
            throw new Exception("Table " + url + " n'existe pas ");
        }
        FileReader read = new FileReader(file);
        BufferedReader buffer = new BufferedReader(read);
        String output = buffer.readLine();
        int test = 0;
        if (output == null)
            throw new Exception("Table " + url + " est vide");
        while (output != null) {
            if (test == 0 || test == 1) {
                test++;
            } else {
                ArrayList<String> dataCol = new ArrayList<>();
                String coll = output;
                String[] allColm = coll.split(",");
                for (int i = 0; i < allColm.length; i++) {
                    dataCol.add(allColm[i]);
                }
                reponse.add(dataCol);
            }
            output = buffer.readLine();
        }
        return reponse;

    }
}
