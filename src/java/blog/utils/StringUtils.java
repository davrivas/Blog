package blog.utils;

public class StringUtils {
    public static boolean esNuloOEspacioEnBlanco(String texto) {
        if (texto == null)
            return true;
        
        if (texto.trim().isEmpty())
            return true;
        
        return false;
    }
}
