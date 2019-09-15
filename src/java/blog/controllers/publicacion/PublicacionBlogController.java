package blog.controllers.publicacion;

import blog.modelo.dao.PublicacionFacade;
import blog.modelo.entidades.Publicacion;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;

@Named(value="publicacionBlogController")
@ApplicationScoped
public class PublicacionBlogController {

    @EJB
    private PublicacionFacade pf;
    
    private List<Publicacion> publicaciones;
    
    private Publicacion publicacionSeleccionada;
    
    public PublicacionBlogController() {
    }
    
    @PostConstruct
    public void init() {
        publicacionSeleccionada = new Publicacion();
    }

    public List<Publicacion> getPublicaciones() {
        return pf.findAll();
    }

    public Publicacion getPublicacionSeleccionada() {
        return publicacionSeleccionada;
    }

    public void setPublicacionSeleccionada(Publicacion publicacionSeleccionada) {
        this.publicacionSeleccionada = publicacionSeleccionada;
    }
    
    public void seleccionarPublicacion(Publicacion p) {
        this.publicacionSeleccionada = p;
    }

}
