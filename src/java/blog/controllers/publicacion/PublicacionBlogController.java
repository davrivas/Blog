package blog.controllers.publicacion;

import blog.controllers.script.ScriptController;
import blog.controllers.sesion.SesionController;
import blog.modelo.dao.CategoriaPublicacionFacade;
import blog.modelo.dao.ComentarioFacade;
import blog.modelo.dao.PublicacionFacade;
import blog.modelo.entidades.CategoriaPublicacion;
import blog.modelo.entidades.Comentario;
import blog.modelo.entidades.Publicacion;
import blog.modelo.entidades.Usuario;
import blog.utils.MessageUtils;
import blog.utils.UsuarioUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@Named(value="publicacionBlogController")
@SessionScoped
public class PublicacionBlogController implements Serializable {

    @EJB
    private PublicacionFacade pf;
    
    @EJB
    private CategoriaPublicacionFacade cpf;
    
    @EJB
    private ComentarioFacade cf;
    
    @Inject
    private SesionController sc;
    
    @Inject
    private ScriptController scriptController;
    
    private List<Publicacion> publicaciones;
    private List<Publicacion> publicacionesPorTipo;
    
    private Publicacion publicacionSeleccionada;
    private CategoriaPublicacion categoriaPublicacionSeleccionada;
    private Comentario nuevoComentario;
    
    private String busqueda;
    
    public PublicacionBlogController() {
    }
    
    @PostConstruct
    public void init() {
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

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }    
    
    public String seleccionarPublicacion(Publicacion p) {
        this.publicacionSeleccionada = p;
        return "publicacion.xhtml?faces-redirect=true";
    }
    
    public String buscarPublicaciones() {
        // facade que busque
        return "";
    }
    
    public String agregarComentario() {
        try {
            this.nuevoComentario.setUsuarioId(this.sc.getUsuario());
            this.nuevoComentario.setPublicacionId(this.publicacionSeleccionada);
            this.nuevoComentario.setFechaPublicacion(new Date());
            this.nuevoComentario.setEstado((short)1);
            
            this.cf.create(this.nuevoComentario);
            
            this.publicacionSeleccionada.getComentarioList().add(this.nuevoComentario);
            this.nuevoComentario = new Comentario();
            this.scriptController.setScript(MessageUtils.mostrarMensajeExito("Comentario agregado satisfactoriamente"));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            this.scriptController.setScript(MessageUtils.mostrarMensajeExcepcion(ex));
        }
        
        return "";
    }
    
}
