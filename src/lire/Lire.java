package lire;

import exceptions.MessageConfirmation;
import exceptions.MessageErreur;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Lire {
    /************ tout les nom de colonne *****/
    public ArrayList<String> nomColumn(String url) throws Exception {
        String path = "Base\\"+url+".txt";
        ArrayList<String> nomCol = new ArrayList<>();
        File file = new File(path);
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
    /**************** tout les donnes *******************/
    public ArrayList<ArrayList<String>> Data(String url) throws Exception,MessageConfirmation {
        ArrayList<ArrayList<String>> reponse = new ArrayList<>();
        File file = new File("Base\\"+url+".txt");
        if (!file.exists()) {
            throw new MessageConfirmation("Table " + url + " n'existe pas ");
        }
        FileReader read = new FileReader(file);
        BufferedReader buffer = new BufferedReader(read);
        String output = buffer.readLine();
        int test = 0;
        if (output == null)
            throw new MessageConfirmation("Table " + url + " est vide");
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
    
    public ArrayList<ArrayList<String>>nomtables() throws Exception,MessageErreur{
         try {
            ArrayList<ArrayList<String>> Reponse = new ArrayList<>();
            File dir = new File("Base\\");
            File[] liste = dir.listFiles();
            for (File item : liste) {
                if (item.isFile() && item.getName().endsWith(".txt")) {
                   String[] split = item.getName().split("\\.");
                   ArrayList<String>donne = new ArrayList<>();
                   donne.add(split[0]);
                   Reponse.add(donne);
                } 
            } 
            return Reponse;
        } catch (Exception e) {
            throw  new MessageErreur("erreur sur la fonction nomtables");
        }
    }
    
}
