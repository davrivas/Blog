package blog.controllers.sesion;

import blog.constantes.Constantes;
import blog.controllers.script.ScriptController;
import blog.modelo.dao.RolFacade;
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

    @EJB
    private RolFacade rf;

    @Inject
    private ScriptController sc;

    private Usuario usuario;
    private String correo;
    private String clave;

    private Usuario nuevoUsuario;
    private String confirmacionCorreo;
    private String confirmacionClave;

    public SesionController() {
    }

    @PostConstruct
    public void init() {
        this.nuevoUsuario = new Usuario();
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

    public String getConfirmacionCorreo() {
        return confirmacionCorreo;
    }

    public void setConfirmacionCorreo(String confirmacionCorreo) {
        this.confirmacionCorreo = confirmacionCorreo;
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
                this.sc.setScript(MessageUtils.mostrarMensajeError("Usuario no existe o las credenciales están mal escritas"));
                return "";
            }

            this.correo = null;

            switch (this.usuario.getRolId().getId()) {
                case Constantes.ADMINISTRADOR_ID:
                    this.sc.setScript(MessageUtils.mostrarMensajeExito("Bienvenido administrador " + this.usuario.getNombreCompleto()));
                    return "/admin/index.xhtml?faces-redirect=true";
                case Constantes.VISITANTE_ID:
                    this.sc.setScript(MessageUtils.mostrarMensajeExito("Bienvenido " + this.usuario.getNombreCompleto()));
                    return "index.xhtml?faces-redirect=true";
                default:
                    throw new Exception("El usuario no tiene rol asignado.");
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            this.sc.setScript(MessageUtils.mostrarMensajeExcepcion(ex));
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

    public String registrarse() {
        String mensaje = "";
        boolean esValido = true;

        if (!this.nuevoUsuario.getCorreo().equals(this.confirmacionCorreo)) {
            esValido = false;
            mensaje += "Las correos no coinciden. ";
        }
        
        if (!this.nuevoUsuario.getClave().equals(this.confirmacionClave)) {
            if (esValido) {
                esValido = false;
            }
            
            this.nuevoUsuario.setClave(null);
            this.confirmacionClave = null;
            mensaje += "Las contraseñas no coinciden";
        }

        if (!esValido) {
            this.sc.setScript(MessageUtils.mostrarMensajeError(mensaje));
            return "";
        }
        
        if (uf.findByCorreo(this.confirmacionCorreo) != null) {
            this.sc.setScript(MessageUtils.mostrarMensajeError("Ya existe una dirección de correo " + this.confirmacionCorreo + ", intente con otra."));
            return "";
        }

        try {
            this.nuevoUsuario.setRolId(rf.find(Constantes.VISITANTE_ID));

            this.uf.create(this.nuevoUsuario);

            this.correo = String.copyValueOf(this.confirmacionCorreo.toCharArray());
            this.clave = String.copyValueOf(this.confirmacionClave.toCharArray());
            
            this.nuevoUsuario = new Usuario();
            this.confirmacionCorreo = null;
            this.confirmacionClave = null;

            return iniciarSesion();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            sc.setScript(MessageUtils.mostrarMensajeExcepcion(ex));
            return "";
        }
    }

}
