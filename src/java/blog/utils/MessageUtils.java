package blog.utils;

public class MessageUtils {

    enum TipoMensaje {
        Exito,
        Error
    }
    
    public static String mostrarMensajeExito(String text) {
        return mostrarMensaje(text, TipoMensaje.Exito);
    }
    
    public static String mostrarMensajeError(String text) {
        return mostrarMensaje(text, TipoMensaje.Error);
    }
    
    public static String mostrarMensajeExcepcion(Exception ex) {
        return mostrarMensajeError("Ocurrió un error: " + ex.getMessage());
    }

    private static String mostrarMensaje(String text, TipoMensaje tipoMensaje) {
        String type = "", title = "";
        
        switch (tipoMensaje) {
            case Exito:
                type = "success";
                title = "Exito";
                break;
            case Error:
                type = "error";
                title = "Error";
                break;
        }

        String mensaje = "Swal.fire({"
                + "type: '" + type + "', "
                + "title: '" + title + "', "
                + "text: \"" + text + "\", "
                + "footer: ''"
                + "})";
        
        return mensaje;
    }
}
