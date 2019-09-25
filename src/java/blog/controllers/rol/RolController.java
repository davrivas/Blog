package blog.controllers.rol;

import blog.modelo.dao.RolFacade;
import blog.modelo.entidades.Rol;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value="rolController")
@RequestScoped
public class RolController {

    @EJB
    private RolFacade rf;
    
    private List<Rol> roles;
    
    public RolController() {
    }
    
    public List<Rol> getRoles() {
        if (this.roles == null || this.roles.isEmpty()) {
            this.roles = rf.findAll();
        }
        
        return this.roles;
    }

}
