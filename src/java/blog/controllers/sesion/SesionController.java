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
    
    private Usuario nuevoUsuario;
    private String confirmacionClave;

    public SesionController() {
    }

    @PostConstruct
    public void init() {
        //this.usuario = this.uf.find(1); // Admin
        //this.usuario = this.uf.find(2); // Usuario
    }

    public Usuario getUsuario() {
        return usuario;
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

    public Usuario getNuevoUsuario() {
        return nuevoUsuario;
    }

    public void setNuevoUsuario(Usuario nuevoUsuario) {
        this.nuevoUsuario = nuevoUsuario;
    }

    public String getConfirmacionClave() {
        return confirmacionClave;
    }

    public void setConfirmacionClave(String confirmacionClave) {
        this.confirmacionClave = confirmacionClave;
    }

    public String iniciarSesion() {
        try {
            this.usuario = uf.iniciarSesion(this.correo, this.clave);
            this.clave = null;

            if (this.usuario == null) {
                sc.setScript(MessageUtils.mostrarMensajeError("Usuario no existe o las credenciales están mal escritas"));
                return "";
            }

            this.correo = null;
            
            switch (this.usuario.getRolId().getId()) {
                case Constantes.ADMINISTRADOR_ID:
                    sc.setScript(MessageUtils.mostrarMensajeExito("Bienvenido administrador " + this.usuario.getNombreCompleto()));
                    return "/admin/index.xhtml?faces-redirect=true";
                case Constantes.VISITANTE_ID:
                    sc.setScript(MessageUtils.mostrarMensajeExito("Bienvenido " + this.usuario.getNombreCompleto()));
                    return "index.xhtml?faces-redirect=true";
                default:
                    throw new Exception("El usuario no tiene rol asignado.");
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            sc.setScript(MessageUtils.mostrarMensajeExcepcion(ex));
            return "";
        }
    }

    public void cerrarSesion() throws IOException {
        this.usuario = null;
        
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        String path = ec.getRequestContextPath() + "/index.xhtml";
        
        ec.invalidateSession();        
        ec.redirect(path);
    }

}
