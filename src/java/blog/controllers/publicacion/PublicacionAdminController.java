package blog.controllers.publicacion;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@Named(value="publicacionAdminController")
@SessionScoped
public class PublicacionAdminController implements Serializable{

    public PublicacionAdminController() {
    }

}
