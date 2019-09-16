package blog.modelo.dao;

import blog.modelo.entidades.CategoriaPublicacion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CategoriaPublicacionFacade extends AbstractFacade<CategoriaPublicacion> {

    @PersistenceContext(unitName = "BlogPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaPublicacionFacade() {
        super(CategoriaPublicacion.class);
    }

}
