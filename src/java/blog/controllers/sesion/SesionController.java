package blog.controllers.sesion;

import blog.constantes.Constantes;
import blog.controllers.script.ScriptController;
import blog.modelo.dao.UsuarioFacade;
import blog.modelo.entidades.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

@Named(value="sesionController")
@SessionScoped
public class SesionController implements Serializable{
    
    @EJB
    private UsuarioFacade uf;
    
    @Inject
    private ScriptController sc;
    
    private Usuario usuario;

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
    
    public String iniciarSesion() {
        String pagina = "";
        
        return pagina;
    }
    
    public void cerrarSesion() {
        
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
