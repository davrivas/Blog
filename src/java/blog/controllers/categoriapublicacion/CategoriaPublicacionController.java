package blog.controllers.categoriapublicacion;

import blog.modelo.dao.CategoriaPublicacionFacade;
import blog.modelo.entidades.CategoriaPublicacion;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "categoriaPublicacionController")
@RequestScoped
public class CategoriaPublicacionController {

    @EJB
    private CategoriaPublicacionFacade cpf;

    private List<CategoriaPublicacion> categorias;

    public CategoriaPublicacionController() {
    }

    public List<CategoriaPublicacion> getCategorias() {
        if (this.categorias == null || this.categorias.isEmpty()) {
            this.categorias = cpf.findAll();
        }
        
        return this.categorias;
    }

}
