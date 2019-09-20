package blog.converters;

import blog.modelo.dao.CategoriaPublicacionFacade;
import blog.modelo.entidades.CategoriaPublicacion;
import javax.ejb.EJB;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = CategoriaPublicacion.class)
public class CategoriaPublicacionConverter implements Converter<CategoriaPublicacion> {

    @EJB
    private CategoriaPublicacionFacade cpf;

    public CategoriaPublicacionConverter() {
        cpf = CDI.current().select(CategoriaPublicacionFacade.class).get();
    }
    
    @Override
    public CategoriaPublicacion getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Integer id = Integer.valueOf(value);
            return cpf.find(id);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, CategoriaPublicacion obj) {
        if (obj != null) {
            return obj.getId().toString();
        }
        return "";
    }
    
}
