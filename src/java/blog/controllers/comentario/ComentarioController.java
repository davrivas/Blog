package blog.controllers.comentario;

import blog.modelo.entidades.Comentario;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value="comentarioController")
@RequestScoped
public class ComentarioController {

    public ComentarioController() {
    }
    
    public int getCantidadComentarios(List<Comentario> comentarios) {
        if (comentarios == null || comentarios.isEmpty())
            return 0;
        
        return comentarios.size();
    }
    
    public String mostrarComentarios(List<Comentario> comentarios) {
        if (comentarios == null || comentarios.isEmpty())
            return "<i class=\"fa fa-comment\"></i> Sin comentarios";
        
        String mensaje = comentarios.size() + " <i class=\"fa fa-comment\"></i> comentario";
        
        return comentarios.size() > 1 ? mensaje + "s" : mensaje;
    }

}
