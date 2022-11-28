package table;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Table
 */
public class Table implements Serializable {
    String nomTable;
    ArrayList<String> nomColumn;
    ArrayList<ArrayList<String>> Data;

    public Table() {
    }

    public Table(String nomTable, ArrayList<String> nomColumn, ArrayList<ArrayList<String>> data) {
        this.nomTable = nomTable;
        this.nomColumn = nomColumn;
        Data = data;
    }

    public void afficheResultat() {
        System.out.println(" \t table " + this.getNomTable());
        System.out.println("");
        ArrayList<String> nomcol = this.getNomColumn();
        for (int i = 0; i < nomcol.size(); i++) {
            System.out.print(nomcol.get(i) + " \t");
        }

        System.out.println("");
        ArrayList<ArrayList<String>> data = this.getData();
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                System.out.print(data.get(i).get(j) + " \t");
            }
            System.out.println(" ");
        }
    }

    public String getNomTable() {
        return nomTable;
    }

    public void setNomTable(String nomTable) {
        this.nomTable = nomTable;
    }

    public ArrayList<String> getNomColumn() {
        return nomColumn;
    }

    public void setNomColumn(ArrayList<String> nomColumn) {
        this.nomColumn = nomColumn;
    }

    public ArrayList<ArrayList<String>> getData() {
        return Data;
    }

    public void setData(ArrayList<ArrayList<String>> data) {
        Data = data;
    }
}