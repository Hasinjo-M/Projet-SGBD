package input;

import java.lang.reflect.Method;

import requet.Requet;
import table.Table;
import annotation.*;

public class Input {
    
    // Fonction qui return la method utiliser pour faire une requete
    public Method getMethod(String refAnnotation) throws Exception {
        Requet rq = new Requet();
        Method[] allMethods = rq.getClass().getMethods();
        for (Method method : allMethods) {
            if (method.getAnnotation(NomFunction.class) != null) {
                if (method.getAnnotation(NomFunction.class).nomFunction().equals(refAnnotation)) {
                    return method;
                }
            }
        }
        throw new Exception("erreur sur la syntaxe"); 
    }

    public Table output(String sql) throws Exception {
        String[] requet = sql.split(" ");
        Requet rq = new Requet();
        String refAnnotation = new String();
        if(requet[0].equals("supprime")){
            if(requet[1].equals("all")){      
                refAnnotation = requet[0] + " " + requet[1];
            }
            else{
                refAnnotation = requet[0]; 
            } 
        }
        else{
            refAnnotation = requet[0] + " " + requet[1];
        }
         
            Method methodUtiliser = getMethod(refAnnotation);
            
            // creation d'une table
            if (String.valueOf(methodUtiliser.getReturnType()).equals("void")
                    && methodUtiliser.getName().equals("createtable")) {
                methodUtiliser.invoke(rq, sql);
                throw new Exception("  Table créé ");
            } 
            //insertion de donnée dans une table
            else if (String.valueOf(methodUtiliser.getReturnType()).equals("void")
                    && methodUtiliser.getName().equals("insert")) {
                methodUtiliser.invoke(rq, sql);
                throw new Exception("  insertion dans la table " + requet[2] + " terminer");
            }
            // Suppression d'une table
            else if (String.valueOf(methodUtiliser.getReturnType()).equals("void")
                    && methodUtiliser.getName().equals("deleteTab")) {
                try {
                    methodUtiliser.invoke(rq, sql);
                    throw new Exception(" Suppression de la table "+ requet[0] + " terminer" );
                } catch (Exception e) {
                    throw  e;
                }   
            }// Suppression de tout les tables éxistant
            else if (String.valueOf(methodUtiliser.getReturnType()).equals("void")
                    && methodUtiliser.getName().equals("deleteAll")) {
                try {
                    methodUtiliser.invoke(rq);
                    throw new Exception(" Suppression de les tables  terminer" );
                } catch (Exception e) {
                    throw  e;
                }   
            }
            //Requete qui return un Object Table
            else { 
                try {
                    System.out.println("method utiliser  "+methodUtiliser.getName());
                    Table reponse = (Table) methodUtiliser.invoke(rq, sql);
                    //Table reponse = rq.projection(sql);
                    return reponse;
                } catch (Exception e) {
                   throw e;
                }
            }
       
    }
    
}
