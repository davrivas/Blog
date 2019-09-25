package blog.converters;

import blog.modelo.dao.RolFacade;
import blog.modelo.entidades.Rol;
import javax.ejb.EJB;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Rol.class)
public class RolConverter implements Converter<Rol> {

    @EJB
    private RolFacade rf;

    public RolConverter() {
        rf = CDI.current().select(RolFacade.class).get();
    }
    
    @Override
    public Rol getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Integer id = Integer.valueOf(value);
            return rf.find(id);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Rol obj) {
        if (obj != null) {
            return obj.getId().toString();
        }
        return "";
    }
    
}
