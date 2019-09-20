package blog.controllers.publicacion;

import blog.modelo.dao.PublicacionFacade;
import blog.modelo.entidades.Publicacion;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

@Named(value="publicacionAdminController")
@SessionScoped
public class PublicacionAdminController implements Serializable{

    @EJB
    private PublicacionFacade pf;
    
    private List<Publicacion> publicaciones;
    
    private Publicacion nuevaPublicacion;
    private Publicacion publicacionSeleccionada;
    
    public PublicacionAdminController() {
    }
    
    @PostConstruct
    public void init() {
        this.nuevaPublicacion = new Publicacion();
    }
    
    public List<Publicacion> getPublicaciones() {
        this.publicaciones = pf.findAll();
        return this.publicaciones;
    }

    public Publicacion getNuevaPublicacion() {
        return nuevaPublicacion;
    }

    public void setNuevaPublicacion(Publicacion nuevaPublicacion) {
        this.nuevaPublicacion = nuevaPublicacion;
    }

    public Publicacion getPublicacionSeleccionada() {
        return publicacionSeleccionada;
    }

    public void setPublicacionSeleccionada(Publicacion publicacionSeleccionada) {
        this.publicacionSeleccionada = publicacionSeleccionada;
    }
    
    public String irACrearPublicacion() {
        return "nuevaPublicacion.xhtml?faces-redirect=true";
    }

}
