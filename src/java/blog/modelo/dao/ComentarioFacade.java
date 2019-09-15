package blog.modelo.dao;

import blog.modelo.entidades.Comentario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ComentarioFacade extends AbstractFacade<Comentario> {

    @PersistenceContext(unitName = "BlogPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComentarioFacade() {
        super(Comentario.class);
    }

}
