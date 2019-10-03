package blog.converters;

import blog.modelo.dao.CategoriaPublicacionFacade;
import blog.modelo.entidades.CategoriaPublicacion;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "categoriaPublicacionConverter")
public class CategoriaPublicacionConverter implements Converter {

    private CategoriaPublicacionFacade cpf;

    public CategoriaPublicacionConverter() {
        cpf = CDI.current().select(CategoriaPublicacionFacade.class).get();
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Integer id = Integer.valueOf(value);
            return cpf.find(id);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
        if (obj != null) {
            return ((CategoriaPublicacion) obj).getId().toString();
        }
        return "";
    }
    
}
