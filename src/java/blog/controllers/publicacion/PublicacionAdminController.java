package blog.controllers.publicacion;

import blog.controllers.script.ScriptController;
import blog.controllers.sesion.SesionController;
import blog.modelo.dao.PublicacionFacade;
import blog.modelo.entidades.Publicacion;
import blog.utils.MessageUtils;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

@Named(value = "publicacionAdminController")
@SessionScoped
public class PublicacionAdminController implements Serializable {

    @EJB
    private PublicacionFacade pf;

    @Inject
    private SesionController sc;

    @Inject
    private ScriptController scriptController;

    private List<Publicacion> publicaciones;

    private Publicacion nuevaPublicacion;
    private Publicacion publicacionSeleccionada;

    public PublicacionAdminController() {
    }

    @PostConstruct
    public void init() {
        this.nuevaPublicacion = new Publicacion();
        this.publicacionSeleccionada = new Publicacion();
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

    public String agregarNuevaPublicacion() {
        try {
            this.nuevaPublicacion.setFechaPublicacion(new Date());
            this.nuevaPublicacion.setUsuarioId(sc.getUsuario());

            pf.create(this.nuevaPublicacion);

            this.nuevaPublicacion = new Publicacion();
            this.scriptController.setScript(MessageUtils.mostrarMensajeExito("Publicación agregada satisfactoriamente"));
            
            return "publicaciones.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            this.scriptController.setScript(MessageUtils.mostrarMensajeExcepcion(ex));
            return "";
        }
    }

    public String verEditarPublicacion(Publicacion p) {
        this.publicacionSeleccionada = p;
        return "editar-publicacion.xhtml?faces-redirect=true";
    }

    public String editarPublicacion() {
        try {
            pf.edit(this.publicacionSeleccionada);

            this.publicacionSeleccionada = new Publicacion();
            this.scriptController.setScript(MessageUtils.mostrarMensajeExito("Publicación editada satisfactoriamente"));
            
            return "publicaciones.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            this.scriptController.setScript(MessageUtils.mostrarMensajeExcepcion(ex));
            return "";
        }
    }
    
    public String verDetallePublicacion(Publicacion p) {
        this.publicacionSeleccionada = p;
        return "detalle-publicacion.xhtml?faces-redirect=true";
    }
    
    public String renderizarContenidoPublicación(String contenido) {
        // TODO: revisar esto
        System.out.println(contenido);
        contenido = contenido.replaceAll("\\  ", "\n");
        System.out.println(contenido);
        
        return contenido;
    }

}
