package blog.controllers.script;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@Named(value="scriptController")
@SessionScoped
public class ScriptController implements Serializable{

    private String script;
    
    public ScriptController() {
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
    
    public String mostrarScript() {
        String nuevoScript = String.copyValueOf(this.script.toCharArray());
        this.script = null;
        
        return nuevoScript;
    }

}
