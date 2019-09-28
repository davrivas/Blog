package blog.controllers.categoriapublicacion;

import blog.constantes.Constantes;
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
    
    private CategoriaPublicacion noticia;
    private CategoriaPublicacion evento;
    private CategoriaPublicacion discusion;

    public CategoriaPublicacionController() {
    }
    
    @PostConstruct
    public void init() {
        this.categorias = cpf.findAll();
        this.noticia = this.categorias.get(Constantes.NOTICIA_ID - 1);
        this.evento = this.categorias.get(Constantes.EVENTO_ID - 1);
        this.discusion = this.categorias.get(Constantes.DISCUSION_ID - 1);
    }

    public List<CategoriaPublicacion> getCategorias() {        
        return categorias;
    }

    public CategoriaPublicacion getNoticia() {
        return noticia;
    }

    public CategoriaPublicacion getEvento() {
        return evento;
    }

    public CategoriaPublicacion getDiscusion() {
        return discusion;
    }

}
