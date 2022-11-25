package ecrire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Ecrire {
    FileWriter write;
    FileReader read;

    public Ecrire(String dir, String[] input) throws Exception {

        File file = new File(dir);
        boolean test = false;
        if (!file.exists()) {
            file.createNewFile();
            test = true;
        }
        write = new FileWriter(file, true);
        read = new FileReader(dir);
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
}
