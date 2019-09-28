package blog.utils;

import blog.constantes.Constantes;
import blog.modelo.entidades.Usuario;

public class UsuarioUtils {
    public static boolean esAdministrador(Usuario usuario) {
        return validarRol(usuario, Constantes.ADMINISTRADOR_ID);
    }

    public static boolean esVisitante(Usuario usuario) {
        return validarRol(usuario, Constantes.VISITANTE_ID);
    }

    private static boolean validarRol(Usuario usuario, int rolId) {
        if (usuario == null)
            return false;
        
        if (usuario.getRolId() == null)
            return false;

        return usuario.getRolId().getId() == rolId;
    }
    
    public static String obtenerNombreCompleto(Usuario usuario) {
        return usuario.getNombres() + " " + usuario.getApellidos();
    }
    
    public static boolean idEsIgual(Usuario u1, Usuario u2) {
        if (u1 == null || u2 == null)
            return false;
        
        return u1.getId().equals(u2.getId());
    }
}
