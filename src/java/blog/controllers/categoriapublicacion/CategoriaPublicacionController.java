package blog.controllers.categoriapublicacion;

import blog.modelo.dao.CategoriaPublicacionFacade;
import blog.modelo.entidades.CategoriaPublicacion;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named(value = "categoriaPublicacionController")
@ApplicationScoped
public class CategoriaPublicacionController implements Serializable {

    @EJB
    private CategoriaPublicacionFacade cpf;

    private List<CategoriaPublicacion> categorias;

    public CategoriaPublicacionController() {
    }
    
    @PostConstruct
    public void init() {
        this.categorias = cpf.findAll();
    }

    public List<CategoriaPublicacion> getCategorias() {        
        return this.categorias;
    }

}
