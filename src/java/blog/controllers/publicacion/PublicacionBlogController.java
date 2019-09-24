package blog.controllers.publicacion;

import blog.modelo.dao.CategoriaPublicacionFacade;
import blog.modelo.dao.ComentarioFacade;
import blog.modelo.dao.PublicacionFacade;
import blog.modelo.entidades.CategoriaPublicacion;
import blog.modelo.entidades.Comentario;
import blog.modelo.entidades.Publicacion;
import blog.modelo.entidades.Usuario;
import blog.utils.UsuarioUtils;
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
    
    @EJB
    private CategoriaPublicacionFacade cpf;
    
    @EJB
    private ComentarioFacade cf;
    
    private List<Publicacion> publicaciones;
    private List<Publicacion> publicacionesPorTipo;
    
    private Publicacion publicacionSeleccionada;
    private CategoriaPublicacion categoriaPublicacionSeleccionada;
    private Comentario nuevoComentario;
    
    public PublicacionBlogController() {
    }
    
    @PostConstruct
    public void init() {
        this.publicacionSeleccionada = new Publicacion();
        this.categoriaPublicacionSeleccionada = new CategoriaPublicacion();
        this.nuevoComentario = new Comentario();
    }

    public List<Publicacion> getPublicaciones() {
        this.publicaciones = pf.findAllActivas();
        return this.publicaciones;
    }
    
    public List<Publicacion> getPublicacionesPorTipo() {
        if (this.categoriaPublicacionSeleccionada != null) {
            this.publicacionesPorTipo = pf.findAllPorTipo(this.categoriaPublicacionSeleccionada);
        } else {
            this.publicacionesPorTipo = pf.findAllActivas();
        }
        
        return this.publicacionesPorTipo;
    }

    public Publicacion getPublicacionSeleccionada() {
        return publicacionSeleccionada;
    }

    public void setPublicacionSeleccionada(Publicacion publicacionSeleccionada) {
        this.publicacionSeleccionada = publicacionSeleccionada;
    }

    public CategoriaPublicacion getCategoriaPublicacionSeleccionada() {
        return categoriaPublicacionSeleccionada;
    }

    public void setCategoriaPublicacionSeleccionada(CategoriaPublicacion categoriaPublicacionSeleccionada) {
        this.categoriaPublicacionSeleccionada = categoriaPublicacionSeleccionada;
    }

    public Comentario getNuevoComentario() {
        return nuevoComentario;
    }

    public void setNuevoComentario(Comentario nuevoComentario) {
        this.nuevoComentario = nuevoComentario;
    }
    
    public String seleccionarPublicacion(Publicacion p) {
        this.publicacionSeleccionada = p;
        return "publicacion.xhtml?faces-redirect=true";
    }
    
    public String mostrarComentarios(List<Comentario> comentarios) {
        if (comentarios == null || comentarios.isEmpty())
            return "<i class=\"fa fa-comment\"></i> Sin comentarios";
        
        return comentarios.size() + " <i class=\"fa fa-comment\"></i> comentarios";
    }
    
    public boolean esAdministrador(Usuario u) {
        return UsuarioUtils.esAdministrador(u);
    }
}
