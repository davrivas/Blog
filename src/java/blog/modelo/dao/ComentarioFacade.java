package blog.modelo.dao;

import blog.modelo.entidades.Comentario;
import blog.modelo.entidades.Publicacion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
    
    public List<Comentario> findAllByPublicacion(Publicacion publicacion) {
        List<Comentario> comentarios = null;
        
        try {
            TypedQuery<Comentario> tq = getEntityManager().createQuery("SELECT c "
                    + "FROM Comentario c "
                    + "WHERE c.publicacionId = :publicacion "
                    + "ORDER BY c.fechaPublicacion ASC", Comentario.class);
            tq.setParameter("publicacion", publicacion);
            comentarios = tq.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        
        return comentarios;
    }

}
