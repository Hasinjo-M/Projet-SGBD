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

    public int nombre_max_colonne(int indice){
        ArrayList<ArrayList<String>> donne = this.getData();
        int max=0;
        if(donne.size()!=0){ 
            max= donne.get(0).get(indice).length();
        }
        for(int i=0; i< donne.size() ; i++){
            int nombre= donne.get(0).get(indice).length();
            if(nombre>max){
                max= nombre;
            }
        }
        return max;
    }
    public String add_par_esapce(String mot  , int max){
        String somme= mot;
        if(mot.length()<max){
            int nombre=mot.length();
            while(nombre<max){
                somme=somme+" ";
                nombre++;
            }
        }
        somme=somme+"|";
        return somme;
    }
    
     public void Affichage(){
        ArrayList<ArrayList<String>> donne = this.getData();
        ArrayList<String> nomcol = this.getNomColumn();
        String tire= new String();
        String tire1= new String();
        int[] taille_colonne= new int[nomcol.size()];
     
        
        String tete= new String();
        int k=0;
        
         for(int i=0 ; i< nomcol.size() ; i++){
            int nbr_caractere_max=0;
            if( this.nombre_max_colonne(i) < nomcol.get(i).length()){
                nbr_caractere_max = nomcol.get(i).length();
            } else{
                nbr_caractere_max= this.nombre_max_colonne(i);
            }
            taille_colonne[i]= nbr_caractere_max+5;
            for(int j=0 ; j<taille_colonne[i]+1 ; j++){
                tire1=tire1+"-";
            }
        }
        
        for(int i=0 ; i< nomcol.size()  ; i++){
            tete=tete+add_par_esapce(nomcol.get(i) , taille_colonne[i]);
        }
        
        System.out.println(tire1);
        System.out.println(tete);
        System.out.println(tire1);
         for( int i=0; i<donne.size() ; i++){
            String body= new String();
            for(int j=0 ; j< nomcol.size()  ; j++){
                body= body+add_par_esapce(String.valueOf( donne.get(i).get(j) ) , taille_colonne[j]);
            }
            System.out.println(body);
        }
        System.out.println(tire1);
    
        
    }
    
    public Table() {
    }

    public Table(String nomTable, ArrayList<String> nomColumn, ArrayList<ArrayList<String>> data) {
        this.nomTable = nomTable;
        this.nomColumn = nomColumn;
        Data = data;
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