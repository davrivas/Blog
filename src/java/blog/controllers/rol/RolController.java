package blog.controllers.rol;

import blog.modelo.dao.RolFacade;
import blog.modelo.entidades.Rol;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named(value = "rolController")
@ApplicationScoped
public class RolController implements Serializable {

    @EJB
    private RolFacade rf;

    private List<Rol> roles;

    public RolController() {
    }

    @PostConstruct
    public void init() {
        this.roles = rf.findAll();
    }

    public List<Rol> getRoles() {
        return this.roles;
    }

}
