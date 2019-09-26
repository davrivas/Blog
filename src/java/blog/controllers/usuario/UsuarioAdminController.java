package blog.controllers.usuario;

import blog.controllers.script.ScriptController;
import blog.modelo.dao.UsuarioFacade;
import blog.modelo.entidades.Usuario;
import blog.utils.MessageUtils;
import blog.utils.UsuarioUtils;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

@Named(value = "usuarioAdminController")
@SessionScoped
public class UsuarioAdminController implements Serializable {

    @EJB
    private UsuarioFacade uf;

    @Inject
    private ScriptController scriptController;

    private List<Usuario> usuarios;

    private Usuario nuevoUsuario;
    private Usuario usuarioSeleccionado;

    private String confirmacionClave;

    public UsuarioAdminController() {
    }

    @PostConstruct
    public void init() {
        this.nuevoUsuario = new Usuario();
    }

    public List<Usuario> getUsuarios() {
        this.usuarios = uf.findAll();
        return this.usuarios;
    }

    public Usuario getNuevoUsuario() {
        return nuevoUsuario;
    }

    public void setNuevoUsuario(Usuario nuevoUsuario) {
        this.nuevoUsuario = nuevoUsuario;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public String getConfirmacionClave() {
        return confirmacionClave;
    }

    public void setConfirmacionClave(String confirmacionClave) {
        this.confirmacionClave = confirmacionClave;
    }

    public String agregarNuevoUsuario() {
        if (!this.nuevoUsuario.getClave().equals(this.confirmacionClave)) {
            this.nuevoUsuario.setClave(null);
            this.confirmacionClave = null;
            this.scriptController.setScript(MessageUtils.mostrarMensajeError("Las contraseñas no coinciden"));
            return "";
        }

        try {
            this.uf.create(this.nuevoUsuario);

            this.nuevoUsuario = new Usuario();
            this.confirmacionClave = null;
            this.scriptController.setScript(MessageUtils.mostrarMensajeExito("Usuario creado satisfactoriamente"));

            return "usuarios.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            this.scriptController.setScript(MessageUtils.mostrarMensajeExcepcion(ex));
            return "";
        }
    }

    public String verEditarUsuario(Usuario u) {
        this.usuarioSeleccionado = u;
        return "editar-usuario.xhtml?faces-redirect=true";
    }

    public String editarUsuario() {
        try {
            this.uf.edit(this.usuarioSeleccionado);

            this.usuarioSeleccionado = new Usuario();
            this.scriptController.setScript(MessageUtils.mostrarMensajeExito("Usuario editado satisfactoriamente"));

            return "usuarios.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            this.scriptController.setScript(MessageUtils.mostrarMensajeExcepcion(ex));
            return "";
        }
    }

    public void seleccionarUsuario(Usuario u) {
        this.usuarioSeleccionado = u;
    }

    public String eliminarUsuario() {
        try {
            this.uf.remove(this.usuarioSeleccionado);

            this.usuarioSeleccionado = new Usuario();
            this.scriptController.setScript(MessageUtils.mostrarMensajeExito("Usuario eliminado satisfactoriamente"));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            this.scriptController.setScript(MessageUtils.mostrarMensajeExcepcion(ex));
        }

        return "";
    }

    public String getRolColor(Usuario usuario) {
        if (usuario == null || usuario.getRolId() == null) {
            return "";
        }

        String color = "color: ";

        if (usuario.esAdministrador()) {
            color += "green;";
        } else if (usuario.esVisitante()) {
            color += "purple;";
        } else {
            color = "";
        }

        return color;
    }

}
