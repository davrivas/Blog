package blog.modelo.dao;

import blog.modelo.entidades.Publicacion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
    
    @Override
    public List<Publicacion> findAll() {
        List<Publicacion> publicaciones = null;
        
        try {
            TypedQuery<Publicacion> tq = getEntityManager().createQuery("SELECT p "
                    + "FROM Publicacion p "
                    + "ORDER BY p.fechaPublicacion DESC", Publicacion.class);
            publicaciones = tq.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        
        return publicaciones;
    }

}
