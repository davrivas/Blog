package blog.utils;

import blog.constantes.Constantes;
import blog.modelo.entidades.CategoriaPublicacion;

public class CategoriaPublicacionUtils {
    public static boolean esNoticia(CategoriaPublicacion categoria) {
        return validarCategoria(categoria, Constantes.NOTICIA_ID);
    }
    
    public static boolean esEvento(CategoriaPublicacion categoria) {
        return validarCategoria(categoria, Constantes.EVENTO_ID);
    }
    
    public static boolean esDiscusion(CategoriaPublicacion categoria) {
        return validarCategoria(categoria, Constantes.DISCUSION_ID);
    }
    
    private static boolean validarCategoria(CategoriaPublicacion categoria, int categoriaId) {
        if (categoria == null)
            return false;
        
        return categoria.getId() == categoriaId;
    }
}
