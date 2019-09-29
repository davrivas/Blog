package blog.controllers.publicacion;

import blog.controllers.categoriapublicacion.CategoriaPublicacionController;
import blog.controllers.script.ScriptController;
import blog.controllers.sesion.SesionController;
import blog.modelo.dao.ComentarioFacade;
import blog.modelo.dao.PublicacionFacade;
import blog.modelo.entidades.CategoriaPublicacion;
import blog.modelo.entidades.Comentario;
import blog.modelo.entidades.Publicacion;
import blog.modelo.entidades.Usuario;
import blog.utils.MessageUtils;
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
    private ComentarioFacade cf;
    
    @Inject
    private SesionController sc;
    
    @Inject
    private CategoriaPublicacionController cpc;
    
    @Inject
    private ScriptController scriptController;
    
    private List<Publicacion> publicaciones;
    private List<Publicacion> publicacionesBuscadas;
    private List<Publicacion> publicacionesPorAutor;
    private List<Publicacion> noticias;
    private List<Publicacion> eventos;
    private List<Publicacion> discusiones;
    private List<Comentario> comentariosPublicacion;
    
    private Publicacion publicacionSeleccionada;
    private Comentario nuevoComentario;
    private Comentario comentarioSeleccionado;
    private Usuario autorSeleccionado;
    
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

    public List<Publicacion> getPublicacionesBuscadas() {
        return this.publicacionesBuscadas;
    }

    public List<Publicacion> getPublicacionesPorAutor() {
        this.publicacionesPorAutor = pf.findAllPorAutor(this.autorSeleccionado);
        return this.publicacionesPorAutor;
    }

    public List<Publicacion> getNoticias() {
        this.noticias = pf.findAllPorTipo(cpc.getNoticia());
        return this.noticias;
    }

    public List<Publicacion> getEventos() {
        this.eventos = pf.findAllPorTipo(cpc.getEvento());
        return this.eventos;
    }

    public List<Publicacion> getDiscusiones() {
        this.discusiones = pf.findAllPorTipo(cpc.getDiscusion());
        return this.discusiones;
    }

    public List<Comentario> getComentariosPublicacion() {
        this.comentariosPublicacion = cf.findAllByPublicacion(this.publicacionSeleccionada);
        return this.comentariosPublicacion;
    }

    public Publicacion getPublicacionSeleccionada() {
        return publicacionSeleccionada;
    }

    public void setPublicacionSeleccionada(Publicacion publicacionSeleccionada) {
        this.publicacionSeleccionada = publicacionSeleccionada;
    }

    public Usuario getAutorSeleccionado() {
        return autorSeleccionado;
    }

    public Comentario getNuevoComentario() {
        return nuevoComentario;
    }

    public void setNuevoComentario(Comentario nuevoComentario) {
        this.nuevoComentario = nuevoComentario;
    }

    public Comentario getComentarioSeleccionado() {
        return comentarioSeleccionado;
    }

    public void setComentarioSeleccionado(Comentario comentarioSeleccionado) {
        this.comentarioSeleccionado = comentarioSeleccionado;
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
    
    public String seleccionarCategoria(CategoriaPublicacion c) {
        if (c.getEsNoticia()) {
            return "noticias.xhtml?faces-redirect=true";
        } else if (c.getEsEvento()) {
            return "eventos.xhtml?faces-redirect=true";
        } else if (c.getEsDiscusion()) {
            return "discusiones.xhtml?faces-redirect=true";
        }
        
        scriptController.setScript(MessageUtils.mostrarMensajeError("No se seleccionó categoría"));
        return "";
    }
    
    public String seleccionarAutor(Usuario u) {
        this.autorSeleccionado = u;
        return "publicaciones-por-autor.xhtml?faces-redirect=true";
    }
    
    public String buscarPublicaciones() {
        if (this.busqueda == null || this.busqueda.trim().isEmpty()) {
            this.busqueda = null;
            this.scriptController.setScript(MessageUtils.mostrarMensajeError("Debe digitar un término de búsqueda"));
            return "";
        }
        
        String[] palabras = this.busqueda.split(" ");
        this.publicacionesBuscadas = pf.findAllPorBusqueda(palabras);
        return "busqueda.xhtml?faces-redirect=true";
    }
    
    public String agregarComentario() {
        try {
            this.nuevoComentario.setUsuarioId(this.sc.getUsuario());
            this.nuevoComentario.setPublicacionId(this.publicacionSeleccionada);
            this.nuevoComentario.setFechaPublicacion(new Date());
            
            this.cf.create(this.nuevoComentario);
            
            this.nuevoComentario = new Comentario();
            this.scriptController.setScript(MessageUtils.mostrarMensajeExito("Comentario agregado satisfactoriamente"));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            this.scriptController.setScript(MessageUtils.mostrarMensajeExcepcion(ex));
        }
        
        return "";
    }
    
    public void seleccionarComentario(Comentario c) {
        this.comentarioSeleccionado = c;
    }
    
    public String eliminarComentario() {
        try {
            this.cf.remove(this.comentarioSeleccionado);
            
            this.scriptController.setScript(MessageUtils.mostrarMensajeExito("Comentario eliminado satisfactoriamente"));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            this.scriptController.setScript(MessageUtils.mostrarMensajeExcepcion(ex));
        }
        
        return "";
    }
}
