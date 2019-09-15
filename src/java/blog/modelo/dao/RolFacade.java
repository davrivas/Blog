package blog.modelo.dao;

import blog.modelo.entidades.Rol;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RolFacade extends AbstractFacade<Rol> {

    @PersistenceContext(unitName = "BlogPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolFacade() {
        super(Rol.class);
    }

}
