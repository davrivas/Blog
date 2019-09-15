package blog.controllers.sesion;

import blog.modelo.dao.UsuarioFacade;
import blog.modelo.entidades.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

@Named(value="sesionController")
@SessionScoped
public class SesionController implements Serializable{
    
    @EJB
    private UsuarioFacade uf;
    
    private Usuario usuario;

    public SesionController() {
    }
    
    @PostConstruct
    public void init() {
        usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
