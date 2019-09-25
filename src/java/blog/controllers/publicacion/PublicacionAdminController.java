package blog.controllers.publicacion;

import blog.controllers.script.ScriptController;
import blog.controllers.sesion.SesionController;
import blog.modelo.dao.PublicacionFacade;
import blog.modelo.entidades.Publicacion;
import blog.utils.MessageUtils;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "publicacionAdminController")
@SessionScoped
public class PublicacionAdminController implements Serializable {

    @EJB
    private PublicacionFacade pf;

    @Inject
    private SesionController sc;

    @Inject
    private PublicacionBlogController pbc;

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

    public String verDetallePublicacion(Publicacion p) throws IOException {
        this.pbc.setPublicacionSeleccionada(p);

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        String path = ec.getRequestContextPath() + "/publicacion.xhtml";

        scriptController.setScript("window.open('" + path + "', '_newtab')");

        return "";
    }

    public void seleccionarPublicacion(Publicacion p) {
        this.publicacionSeleccionada = p;
    }

    public String eliminarPublicacion() {
        try {
            pf.remove(this.publicacionSeleccionada);
            
            this.publicacionSeleccionada = new Publicacion();
            this.scriptController.setScript(MessageUtils.mostrarMensajeExito("Publicación eliminada satisfactoriamente"));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            this.scriptController.setScript(MessageUtils.mostrarMensajeExcepcion(ex));
        }

        return "";
    }

}
