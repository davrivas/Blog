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

        if (usuario.getRolList() == null || usuario.getRolList().isEmpty())
            return false;

        return usuario.getRolList().stream().anyMatch((rol) -> (rol.getId() == rolId));
    }
}
