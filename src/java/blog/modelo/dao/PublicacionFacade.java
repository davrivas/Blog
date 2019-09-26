package blog.modelo.dao;

import blog.modelo.entidades.CategoriaPublicacion;
import blog.modelo.entidades.Publicacion;
import blog.modelo.entidades.Usuario;
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
    
    public List<Publicacion> findAllActivas() {
        List<Publicacion> publicaciones = null;
        
        try {
            TypedQuery<Publicacion> tq = getEntityManager().createQuery("SELECT p "
                    + "FROM Publicacion p "
                    + "WHERE p.estado = 1 "
                    + "ORDER BY p.fechaPublicacion DESC", Publicacion.class);
            publicaciones = tq.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        
        return publicaciones;
    }
    
    public List<Publicacion> findAllPorTipo(CategoriaPublicacion categoria) {
        List<Publicacion> publicaciones = null;
        
        try {
            TypedQuery<Publicacion> tq = getEntityManager().createQuery("SELECT p "
                    + "FROM Publicacion p "
                    + "WHERE p.categoriaPublicacionId = :categoria AND p.estado = 1 "
                    + "ORDER BY p.fechaPublicacion DESC", Publicacion.class);
            tq.setParameter("categoria", categoria);
            publicaciones = tq.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        
        return publicaciones;
    }
    
    public List<Publicacion> findAllPorAutor(Usuario autor) {
        List<Publicacion> publicaciones = null;
        
        try {
            TypedQuery<Publicacion> tq = getEntityManager().createQuery("SELECT p "
                    + "FROM Publicacion p "
                    + "WHERE p.usuarioId = :autor AND p.estado = 1 "
                    + "ORDER BY p.fechaPublicacion DESC", Publicacion.class);
            tq.setParameter("autor", autor);
            publicaciones = tq.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        
        return publicaciones;
    }

}
