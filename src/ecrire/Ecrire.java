package ecrire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Ecrire {

    public Ecrire() {
    }
    FileWriter write;
    FileReader read;

    public Ecrire(String dir, String[] input) throws Exception {
        String path = "Base\\"+dir+".txt";
        File file = new File(path);
        boolean test = false;
        if (!file.exists()) {
            file.createNewFile();
            test = true;
        }
        write = new FileWriter(file, true);
        read = new FileReader(path);
        BufferedReader r_donnee = new BufferedReader(read);
        BufferedWriter donnee = new BufferedWriter(write);
        String verif = r_donnee.readLine();
        if (test) {
            donnee.write(input[0]);
            donnee.write("\n");
            donnee.write(input[1]);
        } else {
            if (verif != null) {
                donnee.write("\n");
            }
            donnee.write(input[1]);
        }
        donnee.close();
    }
    
    /**** supprimer un fichier .txt ****/
    public void deleteFichier(String dir)throws Exception{
        String path = "Base\\"+dir+".txt";
        File file = new File(path);
        if (!file.exists()) {
            throw  new Exception("La table "+dir+" n'existe pas");
        }
        if(file.delete()){
            throw  new Exception("La table "+dir + " est supprimé");
        }else{
            throw  new Exception("Opération de suppression echouée");
        }
    }
    
    /**************** supprimer tout le fichier **************/
    public void deleteAll()throws Exception{
        try {
            File dir = new File("Base\\");
            File[] liste = dir.listFiles();
            for (File item : liste) {
                System.out.println("les fichier "+ item.isFile() + "  " +item.getName().endsWith(".txt"));
                if (item.isFile() && item.getName().endsWith(".txt")) {
                   item.delete();
                } 
            }
            
        } catch (Exception e) {
            throw  new Exception("il y a une erreur sur deleteAll");
        }
    }
}
