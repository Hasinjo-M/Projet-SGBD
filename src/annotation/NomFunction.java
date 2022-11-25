package annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface NomFunction {
    public String nomFunction() default "";
}