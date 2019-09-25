package blog.modelo.dao;

import blog.modelo.entidades.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "BlogPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    @Override
    public List<Usuario> findAll() {
        List<Usuario> usuarios = null;
        
        try {
            TypedQuery<Usuario> tq = getEntityManager().createQuery("SELECT u "
                    + "FROM Usuario u "
                    + "ORDER BY u.apellidos", Usuario.class);
            usuarios = tq.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        
        return usuarios;
    }

    public Usuario iniciarSesion(String correo, String clave) {
        Usuario usuario = null;
        
        try {
            TypedQuery<Usuario> tq = getEntityManager().createQuery("SELECT u "
                    + "FROM Usuario u "
                    + "WHERE u.correo = :correo and u.clave = :clave", Usuario.class);
            tq.setParameter("correo", correo);
            tq.setParameter("clave", clave);
            usuario = tq.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        
        return usuario;
    }
}
