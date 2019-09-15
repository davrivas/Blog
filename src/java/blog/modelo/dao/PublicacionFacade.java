package blog.modelo.dao;

import blog.modelo.entidades.Publicacion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PublicacionFacade extends AbstractFacade<Publicacion> {

    @PersistenceContext(unitName = "BlogPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PublicacionFacade() {
        super(Publicacion.class);
    }

}
