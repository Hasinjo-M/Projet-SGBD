package requet;

import java.util.ArrayList;
import lire.*;
import ecrire.*;
import table.*;

import annotation.*;

public class Requet {
    public String donne(String[] colTypye, int index, int debut) {
        String rep = new String();
        for (int j = 0; j < colTypye.length; j++) {
            String[] typeCol = colTypye[j].split(":");
            for (int i = debut; i < typeCol.length; i = i + index) {
                if (j == 0 && j == 1) {
                    rep = typeCol[i] + ",";
                } else if (j == (colTypye.length - 1)) {
                    rep = rep + typeCol[i];
                } else
                    rep = rep + typeCol[i] + ",";
            }
        }
        return rep;
    }
    
    /******************* fonction sur show tables ***************/
    @NomFunction(nomFunction = "les tables")
    public Table showtable(String sql)throws Exception{
        Table reponse = new Table();
        reponse.setNomTable("Les tables");
        ArrayList<String> nomcol = new ArrayList<>();
        nomcol.add("Nom de table");
        reponse.setNomColumn(nomcol);
        reponse.setData(new Lire().nomtables());
       return reponse;
    }
    /********************** fonction supprime un table *******/
    @NomFunction(nomFunction = "supprime")
    public void deleteTab(String sql) throws Exception{
        try {
            String[] sp = sql.split(" ");
            Ecrire ecrire = new Ecrire();
            ecrire.deleteFichier(sp[1]);
        } catch (Exception e) {
            System.out.println("requet.Requet.deleteTab() "+e.getMessage());
            throw  e;
        }
    }
    /********************** fonction qui supprime touts le table *****/
    @NomFunction(nomFunction = "supprime all")
    public void deleteAll()throws Exception{
        try {
            Ecrire ecrire = new Ecrire();
            ecrire.deleteAll();
        } catch (Exception e) {
        }
    }
    
    /**********  creation table *******************************/
    @NomFunction(nomFunction = "create table")
    public void createtable(String sql) throws Exception {
        String[] requet = sql.split(" ");
        String[] colTypye = requet[4].split(",");
        String[] add = new String[2];
        add[0] = donne(colTypye, 2, 0);
        add[1] = donne(colTypye, 1, 1);
        try {
            Ecrire ecrire = new Ecrire(requet[2], add);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Probleme sur la creation d'une table");
        }
    }
    

    @NomFunction(nomFunction = "insert into")
    public void insert(String sql) {
        String[] requet = sql.split(" ");
        String[] input = new String[2];
        for (int i = 0; i < requet.length; i++) {
            if (i == 4) {
                input[0] = requet[i];
            } else if (i == 8) {
                input[1] = requet[i];
            }
        }
        try {
            Ecrire ecrire = new Ecrire(requet[2], input);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @NomFunction(nomFunction = "selects *")
    public Table select(String sql) throws Exception {
        try {
            Lire lire = new Lire();
            String[] requet = sql.split(" ");
            String table = requet[3];
            ArrayList<String> nomColumn = lire.nomColumn(table);
            if (requet.length != 4) {
                int idCondition = -1;
                boolean testcolumn = false;
                for (int i = 0; i < nomColumn.size(); i++) {
                    if (nomColumn.get(i).equals(requet[5])) {
                        idCondition = i;
                        testcolumn = true;
                    }
                }
                if (!testcolumn) {
                    throw new Exception("erreur de nom de column");
                }

                ArrayList<ArrayList<String>> donne = lire.Data(table);
                ArrayList<ArrayList<String>> reponseData = new ArrayList<>();
                for (int i = 0; i < donne.size(); i++) {
                    for (int j = 0; j < donne.get(i).size(); j++) {
                        if (j == idCondition) {
                            if (donne.get(i).get(j).equals(requet[7])) {
                                reponseData.add(donne.get(i));
                            }
                        }
                    }
                }
                Table reponse = new Table(table, nomColumn, reponseData);
                return reponse;
            } else if (requet.length == 4) {
                ArrayList<ArrayList<String>> donne = lire.Data(table);
                Table reponse = new Table(table, nomColumn, donne);
                return reponse;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public Table projection(Table table, String sql) throws Exception {
        String[] inputCols = sql.split(",");
        try {
            ArrayList<String> nomColumns = table.getNomColumn();
            ArrayList<String> nomColumnReponse = new ArrayList<>();
            ArrayList<Integer> idcol = new ArrayList<>();

            for (String inputCol : inputCols) {
                for (int i = 0; i < nomColumns.size(); i++) {
                    if (inputCol.equals(nomColumns.get(i))) {
                        nomColumnReponse.add(nomColumns.get(i));
                        idcol.add(i);
                    }
                }
            }
            ArrayList<ArrayList<String>> donne = table.getData();
            ArrayList<ArrayList<String>> reponseData = new ArrayList<>();
            for (int i = 0; i < donne.size(); i++) {

                ArrayList<String> ValueColumnReponse = new ArrayList<>();
                for (int j = 0; j < donne.get(i).size(); j++) {
                    for (Integer idc : idcol) {
                        if (idc.equals(j)) {
                            ValueColumnReponse.add(donne.get(i).get(j));
                        }
                    }
                }
                if (ValueColumnReponse.size() != 0) {
                    reponseData.add(ValueColumnReponse);
                }

            }
            return new Table(table.getNomTable(), nomColumnReponse, reponseData);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @NomFunction(nomFunction = "select .")
    public Table projection(String sql) throws Exception {
        String[] requet = sql.split(" ");
        try {
            Lire lire = new Lire();
            String table = requet[4];
            ArrayList<String> nomColumns = lire.nomColumn(table);
            ArrayList<String> nomColumnReponse = new ArrayList<>();
            String[] inputCols = requet[2].split(",");
            ArrayList<Integer> idcol = new ArrayList<>();

            for (String inputCol : inputCols) {
                for (int i = 0; i < nomColumns.size(); i++) {
                    if (inputCol.equals(nomColumns.get(i))) {
                        nomColumnReponse.add(nomColumns.get(i));
                        idcol.add(i);
                    }
                }
            }
            ArrayList<ArrayList<String>> donne = lire.Data(table);
            ArrayList<ArrayList<String>> reponseData = new ArrayList<>();
            for (int i = 0; i < donne.size(); i++) {

                ArrayList<String> ValueColumnReponse = new ArrayList<>();
                for (int j = 0; j < donne.get(i).size(); j++) {
                    for (Integer idc : idcol) {
                        if (idc.equals(j)) {
                            ValueColumnReponse.add(donne.get(i).get(j));
                        }
                    }
                }
                if (ValueColumnReponse.size() != 0) {
                    reponseData.add(ValueColumnReponse);
                }

            }
            return new Table(table, nomColumnReponse, reponseData);
        } catch (Exception e) {
            throw  e;
        }
    }

    public Table produitCartesienne(Table R1, Table R2) throws Exception {
        try {
            ArrayList<String> nomColumnsTable1 = R1.getNomColumn();
            ArrayList<String> nomColumnsTable2 = R2.getNomColumn();
            ArrayList<String> nomColumnReponse = new ArrayList<>();
            for (String string : nomColumnsTable1) {
                nomColumnReponse.add(string);
            }
            for (String string : nomColumnsTable2) {
                nomColumnReponse.add(string);
            }
            ArrayList<ArrayList<String>> reponseData = new ArrayList<>();
            ArrayList<ArrayList<String>> donneTable1 = R1.getData();
            ArrayList<ArrayList<String>> donneTable2 = R2.getData();
            for (ArrayList<String> table1 : donneTable1) {

                for (ArrayList<String> table2 : donneTable2) {
                    ArrayList<String> donne = new ArrayList<>();
                    for (String data1 : table1) {
                        donne.add(data1);
                    }
                    for (String data2 : table2) {
                        donne.add(data2);
                    }
                    reponseData.add(donne);
                }
            }
            return new Table(("Produit Cartesienne " + R1.getNomTable() + " et " + R2.getNomTable()), nomColumnReponse,
                    reponseData);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @NomFunction(nomFunction = "select cartesienne")
    public Table produitCartesienne(String Sql) throws Exception {
        String[] requet = Sql.split(" ");
        String[] tables = requet[3].split(",");
        try {
            Table table1 = select("select * from " + tables[0]);
            Table table2 = select("select * from " + tables[1]);
            return produitCartesienne(table1, table2);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean testExistance(ArrayList<String> donne, ArrayList<ArrayList<String>> tabdonne) {
        int test = 0;
        for (ArrayList<String> data : tabdonne) {
            for (String detailleData : data) {
                for (String detailleDonne : donne) {
                    if (detailleData.equals(detailleDonne)) {
                        test++;
                        break;
                    }
                }
            }
            if (test == donne.size())
                return true;
            else 
                test = 0;
        }
        return false;
    }

    // test existance d'un string
    public boolean testExistance(String value, ArrayList<String> data, int index) {
        for (int i = 0; i < data.size(); i++) {
            if (i == index && value.equals(data.get(i))) {
                return true;
            }
        }
        return false;
    }

    @NomFunction(nomFunction = "select Union")
    public Table Union(String sql) throws Exception {
        String[] requet = sql.split(" ");
        String[] tables = new String[2];
        tables[0] = requet[2];
        tables[1] = requet[4];
        try {
            Lire lire = new Lire();
            ArrayList<String> nomColumnsTable1 = lire.nomColumn(tables[0]);
            ArrayList<String> nomColumnsTable2 = lire.nomColumn(tables[1]);
            ArrayList<String> nomColumnsReponse = new ArrayList<>();
            if (nomColumnsTable1.size() != nomColumnsTable2.size()) {
                throw new Exception("les deux tables sont incompatible pour l'operation UNION");
            } else {
                for (String table1 : nomColumnsTable1) {
                    for (String table2 : nomColumnsTable2) {
                        if (table1.equals(table2)) {
                            nomColumnsReponse.add(table1);
                            break;
                        }
                    }
                }
                if (nomColumnsReponse.size() != nomColumnsTable1.size())
                    throw new Exception("les deux tables sont incompatible pour l'operation UNION");
                else {
                    ArrayList<ArrayList<String>> reponseData = new ArrayList<>();
                    ArrayList<ArrayList<String>> donneTable1 = lire.Data(tables[0]);
                    ArrayList<ArrayList<String>> donneTable2 = lire.Data(tables[1]);
                    for (ArrayList<String> table1 : donneTable1) {
                        reponseData.add(table1);
                    }
                    for (ArrayList<String> table2 : donneTable2) {
                        if (!testExistance(table2, donneTable1)) {
                            reponseData.add(table2);
                        }
                    }
                    return new Table(("Union de la table " + tables[0] + " et " + tables[1]), nomColumnsReponse,
                            reponseData);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Table difference(Table R1, Table R2) throws Exception {
        try {
            ArrayList<String> nomColumnsTable1 = R1.getNomColumn();
            ArrayList<String> nomColumnsTable2 = R2.getNomColumn();
            ArrayList<String> nomColumnsReponse = new ArrayList<>();
            if (nomColumnsTable1.size() != nomColumnsTable2.size()) {
                throw new Exception("les deux tables sont incompatible pour l'operation UNION");
            }
            for (String table1 : nomColumnsTable1) {
                for (String table2 : nomColumnsTable2) {
                    if (table1.equals(table2)) {
                        nomColumnsReponse.add(table1);
                        break;
                    }
                }
            }
            if (nomColumnsReponse.size() != nomColumnsTable1.size())
                throw new Exception("les deux tables sont incompatible pour l'operation UNION");
            else {
                ArrayList<ArrayList<String>> reponseData = new ArrayList<>();
                ArrayList<ArrayList<String>> donneTable1 = R1.getData();
                ArrayList<ArrayList<String>> donneTable2 = R2.getData();
                for (ArrayList<String> table1 : donneTable1) {
                    if (!testExistance(table1, donneTable2)) {
                        reponseData.add(table1);
                    }
                }
                
                return new Table(("Difference de la table   " + R1.getNomTable() + " et " + R2.getNomTable()),
                        nomColumnsReponse,
                        reponseData);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @NomFunction(nomFunction = "select difference")
    public Table difference(String sql) throws Exception {
        String[] requet = sql.split(" ");
        String[] tables = new String[2];
        tables[0] = requet[2];
        tables[1] = requet[4];
        try {
            Table table1 = select("select * from " + tables[0]);
            Table table2 = select("select * from " + tables[1]);
            return difference(table1, table2);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @NomFunction(nomFunction = "select Intersection")
    public Table intersection(String sql) throws Exception {
        String[] requet = sql.split(" ");
        String[] tables = new String[2];
        tables[0] = requet[2];
        tables[1] = requet[4];
        try {
            Lire lire = new Lire();
            ArrayList<String> nomColumnsTable1 = lire.nomColumn(tables[0]);
            ArrayList<String> nomColumnsTable2 = lire.nomColumn(tables[1]);
            ArrayList<String> nomColumnsReponse = new ArrayList<>();
            if (nomColumnsTable1.size() != nomColumnsTable2.size()) {
                throw new Exception("les deux tables sont incompatible pour l'operation UNION");
            }
            for (String table1 : nomColumnsTable1) {
                for (String table2 : nomColumnsTable2) {
                    if (table1.equals(table2)) {
                        nomColumnsReponse.add(table1);
                        break;
                    }
                }
            }
            if (nomColumnsReponse.size() != nomColumnsTable1.size())
                throw new Exception("les deux tables sont incompatible pour l'operation UNION");
            else {
                ArrayList<ArrayList<String>> reponseData = new ArrayList<>();
                ArrayList<ArrayList<String>> donneTable1 = lire.Data(tables[0]);
                ArrayList<ArrayList<String>> donneTable2 = lire.Data(tables[1]);
                for (ArrayList<String> table1 : donneTable1) {
                    if (testExistance(table1, donneTable2)) {
                        reponseData.add(table1);
                    }
                }
                return new Table(("Intersection de la table   " + tables[0] + " et " + tables[1]), nomColumnsReponse,
                        reponseData);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /****  ****/
    public Table supprime(Table T) {
        try {
            ArrayList<ArrayList<String>> donneTable1 = T.getData();
            ArrayList<ArrayList<String>> reponse = new ArrayList<>();
            for (ArrayList<String> arrayList : donneTable1) {
                if (!testExistance(arrayList, reponse)) {
                    reponse.add(arrayList);
                }
            }
            T.setData(reponse);
            return T;
        } catch (Exception err) {
            throw err;
        }
    }

    /****  ****/
    @NomFunction(nomFunction = "select division")
    public Table division(String sql) throws Exception {
        String[] requet = sql.split(" ");
        int i = 0;
        try {
            Table table1 = projection("select . " + requet[4] + " from " + requet[6]);
            Table S = projection("select . " + requet[11] + " from " + requet[13]);
            Table T1C = projection("select . " + requet[16] + " from " + requet[6]);
            Table T2C = projection(difference(produitCartesienne(S, T1C), table1), requet[16]);
            return supprime(difference(T1C, T2C));
        } catch (Exception e) {
            // e.printStackTrace();
            throw e;
        }
    }

    @NomFunction(nomFunction = "select *")
    public Table jointureNaturelle(String sql) throws Exception {
        String[] requet = sql.split(" ");
        String[] tables = requet[3].split(",");
        try {
            Table table1 = select("select * from " + tables[0]);
            Table table2 = select("select * from " + tables[1]);
            ArrayList<String> nomColumnsTable1 = table1.getNomColumn();
            ArrayList<String> nomColumnsTable2 = table2.getNomColumn();
            ArrayList<String> nomColumnsReponse = new ArrayList<>();
            int idcolTable1 = -1, id = 0;
            int idcolTable2 = -1;
            String[] joinTable1 = requet[5].split(":");
            String[] joinTable2 = requet[7].split(":");
            for (String nomCol : nomColumnsTable1) {
                if (nomCol.equals(joinTable1[1]))
                    idcolTable1 = id;
                nomColumnsReponse.add(nomCol);
                id++;
            }
            id = 0;
            for (String nomCol : nomColumnsTable2) {
                boolean test = false;
                for (String nomColRep : nomColumnsReponse) {
                    if (nomCol.equals(nomColRep)) {
                        if (nomCol.equals(joinTable2[1]))
                            idcolTable2 = id;
                        test = true;
                    }
                }
                if (test == false) {
                    nomColumnsReponse.add(nomCol);
                }
                id++;
            }
            ArrayList<ArrayList<String>> reponseData = new ArrayList<>();
            ArrayList<ArrayList<String>> donneTable1 = table1.getData();
            ArrayList<ArrayList<String>> donneTable2 = table2.getData();
            ArrayList<String> detailleData = new ArrayList<>();
            for (ArrayList<String> ChaqueDonne : donneTable1) {
                String value = new String();
                for (int j = 0; j < ChaqueDonne.size(); j++) {
                    if (j == idcolTable1) { // Verificaion de idCol
                        value = ChaqueDonne.get(j); // valeur de l'id Col
                        for (ArrayList<String> chaqueDonne2 : donneTable2) {
                            if (testExistance(value, chaqueDonne2, idcolTable2)) { // test si vaule et dans un l'autre
                                                                                   // relation
                                for (String detaille1 : ChaqueDonne) {
                                    detailleData.add(detaille1);
                                }
                                for (int k = 0; k < chaqueDonne2.size(); k++) {
                                    if (k != idcolTable2)
                                        detailleData.add(chaqueDonne2.get(k));
                                    else
                                        continue;
                                }
                                ArrayList<String> rep = new ArrayList<>();
                                for (String each : detailleData) {
                                    rep.add(each);
                                }
                                detailleData.clear();
                                reponseData.add(rep);
                            }
                        }
                    }
                }
            }

            return new Table("Jointure Naturelle table " + tables[0] + " et " + tables[1], nomColumnsReponse,
                    reponseData);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
