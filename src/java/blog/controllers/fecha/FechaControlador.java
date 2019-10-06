package blog.controllers.fecha;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;

@Named(value="fechaControlador")
@ApplicationScoped
public class FechaControlador {
    
    private TimeZone zonaHoraria;

    public FechaControlador() {
    }
    
    @PostConstruct
    public void init() {
        this.zonaHoraria = TimeZone.getTimeZone("GMT-5:00");
    }
    
    public TimeZone getZonaHoraria() {
        return this.zonaHoraria;
    }

}
