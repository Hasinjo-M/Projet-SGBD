package input;

import java.lang.reflect.Method;

import requet.Requet;
import table.Table;
import annotation.*;
import exceptions.MessageConfirmation;
import exceptions.MessageErreur;

public class Input {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    String message;
    
    // Fonction qui return la method utiliser pour faire une requete
    public Method getMethod(String refAnnotation) throws MessageConfirmation {
        Requet rq = new Requet();
        Method[] allMethods = rq.getClass().getMethods();
        for (Method method : allMethods) {
            if (method.getAnnotation(NomFunction.class) != null) {
                if (method.getAnnotation(NomFunction.class).nomFunction().equals(refAnnotation)) {
                    return method;
                }
            }
        }
        
        throw new MessageConfirmation("erreur sur la syntaxe"); 
    }

    public Table output(String sql) throws Exception,MessageConfirmation,MessageErreur {
        String[] requet = sql.split(" ");
        Requet rq = new Requet();
        rq.setInput(this);
        String refAnnotation = new String();
        try {
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
        } catch (Exception e) {         
            throw  new MessageConfirmation("Erreur sur la syntaxe");
        }
            
            
            Method methodUtiliser = getMethod(refAnnotation);                   /// Methode utiliser pour fairele requete
            // creation d'une table
            if (String.valueOf(methodUtiliser.getReturnType()).equals("void")
                    && methodUtiliser.getName().equals("createtable")) {
                methodUtiliser.invoke(rq, sql);
                throw  new MessageConfirmation("  Table créé ");
            } 
            //insertion de donnée dans une table
            else if (String.valueOf(methodUtiliser.getReturnType()).equals("void")
                    && methodUtiliser.getName().equals("insert")) {
                methodUtiliser.invoke(rq, sql);
                throw  new MessageConfirmation("  insertion dans la table " + requet[2] + " terminer");
            }
            // Suppression d'une table
            else if (String.valueOf(methodUtiliser.getReturnType()).equals("void")
                    && methodUtiliser.getName().equals("deleteTab")) {
                try {
                    methodUtiliser.invoke(rq, sql);
                    throw  new MessageConfirmation("ok");
                }
                catch (Exception e) {
                    throw  new MessageConfirmation(this.getMessage());
                }   
            }// Suppression de tout les tables éxistant
            else if (String.valueOf(methodUtiliser.getReturnType()).equals("void")
                    && methodUtiliser.getName().equals("deleteAll")) {
                try {
                    methodUtiliser.invoke(rq);
                    throw  new MessageConfirmation(" Suppression de tout les tables  terminer");
                }  catch (Exception e) {
                    throw  new MessageConfirmation(e.getMessage());
                
                }   
            }
            //Requete qui return un Object Table
            else { 
                try {
                   Table reponse = (Table) methodUtiliser.invoke(rq, sql);
                   return reponse;
                } catch (Exception e) {
                   throw new Exception(this.getMessage());
                }
            }
       
    }
    
}
