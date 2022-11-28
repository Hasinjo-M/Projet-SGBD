package input;

import java.lang.reflect.Method;

import requet.Requet;
import table.Table;
import annotation.*;

public class Input {
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
        String refAnnotation = requet[0] + " " + requet[1];
            Method methodUtiliser = getMethod(refAnnotation);
            if (String.valueOf(methodUtiliser.getReturnType()).equals("void")
                    && methodUtiliser.getName().equals("createtable")) {
                methodUtiliser.invoke(rq, sql);
                throw new Exception("  Table creer ");
            } else if (String.valueOf(methodUtiliser.getReturnType()).equals("void")
                    && methodUtiliser.getName().equals("insert")) {
                methodUtiliser.invoke(rq, sql);
                throw new Exception("  insertion dans la table " + requet[2] + " terminer");
            } else { 
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
