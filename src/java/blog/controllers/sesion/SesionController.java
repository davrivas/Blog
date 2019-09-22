package blog.controllers.sesion;

import blog.constantes.Constantes;
import blog.controllers.script.ScriptController;
import blog.modelo.dao.UsuarioFacade;
import blog.modelo.entidades.Usuario;
import blog.utils.MessageUtils;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "sesionController")
@SessionScoped
public class SesionController implements Serializable {

    @EJB
    private UsuarioFacade uf;

    @Inject
    private ScriptController sc;

    private Usuario usuario;
    private String correo;
    private String clave;

    public SesionController() {
    }

    @PostConstruct
    public void init() {
        usuario = new Usuario();
    }

    public Usuario getUsuario() {
        //return usuario;
        return uf.find(1);
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String iniciarSesion() {
        try {
            this.usuario = uf.iniciarSesion(this.correo, this.clave);

            if (this.usuario == null) {
                sc.setScript(MessageUtils.mostrarMensajeError("Usuario no existe"));
                return "";
            }

            String nombreUsuario = this.usuario.getNombres() + " " + this.usuario.getApellidos();

            if (esAdministrador()) {
                sc.setScript(MessageUtils.mostrarMensajeExito("Bienvenido administrador " + nombreUsuario));
                return "/admin/index.xhtml?faces-redirect=true";
            }

            if (esVisitante()) {
                sc.setScript(MessageUtils.mostrarMensajeExito("Bienvenido " + nombreUsuario));
                return "";
            }

            sc.setScript(MessageUtils.mostrarMensajeError("Usuario no tiene roles asociados"));
            return "";
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            sc.setScript(MessageUtils.mostrarMensajeExcepcion(ex));
            return "";
        }
    }

    public void cerrarSesion() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        this.usuario = null;
        this.correo = null;
        this.clave = null;
        ec.invalidateSession();
        String path = ec.getRequestContextPath() + "/index.xhtml";
        ec.redirect(path);
    }

    public boolean esAdministrador() {
        return validarRol(Constantes.ADMINISTRADOR_ID);
    }

    public boolean esVisitante() {
        return validarRol(Constantes.VISITANTE_ID);
    }

    private boolean validarRol(int rolId) {
        if (this.usuario == null)
            return false;

        if (this.usuario.getRolList() == null || this.usuario.getRolList().isEmpty())
            return false;

        return this.usuario.getRolList().stream().anyMatch((rol) -> (rol.getId() == rolId));
    }

}
