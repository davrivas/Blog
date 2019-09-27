package blog.controllers.publicacion;

import blog.constantes.Constantes;
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
    private List<Publicacion> publicacionesBuscadas;
    private List<Publicacion> publicacionesPorAutor;
    private List<Publicacion> noticias;
    private List<Publicacion> eventos;
    private List<Publicacion> discusiones;
    
    private Publicacion publicacionSeleccionada;
    private Comentario nuevoComentario;
    private Usuario autorSeleccionado;
    private CategoriaPublicacion noticia;
    private CategoriaPublicacion evento;
    private CategoriaPublicacion discusion;
    
    private String busqueda;
    
    public PublicacionBlogController() {
    }
    
    @PostConstruct
    public void init() {
        this.nuevoComentario = new Comentario();
        this.publicacionesBuscadas = pf.findAllPorBusqueda(null);
        this.publicacionesPorAutor = pf.findAllPorAutor(new Usuario());
        this.noticia = cpf.find(Constantes.NOTICIA_ID);
        this.evento = cpf.find(Constantes.EVENTO_ID);
        this.discusion = cpf.find(Constantes.DISCUSION_ID);
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
        this.noticias = pf.findAllPorTipo(noticia);
        return this.noticias;
    }

    public List<Publicacion> getEventos() {
        this.eventos = pf.findAllPorTipo(evento);
        return this.eventos;
    }

    public List<Publicacion> getDiscusiones() {
        this.discusiones = pf.findAllPorTipo(discusion);
        return this.discusiones;
    }    

    public Publicacion getPublicacionSeleccionada() {
        return publicacionSeleccionada;
    }

    public void setPublicacionSeleccionada(Publicacion publicacionSeleccionada) {
        this.publicacionSeleccionada = publicacionSeleccionada;
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
        // TODO : crear página
        return "publicaciones-por-autor.xhtml?faces-redirect=true";
    }
    
    public String buscarPublicaciones() {
        if (this.busqueda == null || this.busqueda.trim().isEmpty()) {
            this.scriptController.setScript(MessageUtils.mostrarMensajeError("Debe digitar un término de búsqueda"));
            return "";
        }
        
        // TODO: completar
        //this.publicacionesBuscadas = pf.findAllPorBusqueda(this.busqueda);
        //return "busqueda.xhtml?faces-redirect=true";
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
