package blog.controllers.script;

import javax.inject.Named;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@Named(value="scriptController")
@ApplicationScoped
public class ScriptController implements Serializable{

    private String script;
    private String historyBack;
    
    public ScriptController() {
    }
    
    @PostConstruct
    public void init() {
        this.historyBack = "history.back();";
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
    
    public String getHistoryBack() {
        if (this.historyBack == null || this.historyBack.isEmpty())
            this.historyBack = "history.back();";
        
        return this.historyBack;
    }

}
