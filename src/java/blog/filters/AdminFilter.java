package blog.filters;

public class AdminFilter {
    // TODO: poner los filtros
}

/*

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "FilterAdministrador", urlPatterns = {"/administrador/*"})
public class FilterAdministrador implements Filter {

    private static final boolean debug = true;

    @Inject
    private SesionControlador sc;

    public FilterAdministrador() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest hsrequest = (HttpServletRequest) request;
        HttpServletResponse hsresponse = (HttpServletResponse) response;
        if (sc.getUsuario()!= null && sc.getUsuario().getRol().getIdRol() == 3) {
            chain.doFilter(request, response);
        } else {
            hsresponse.sendRedirect(hsrequest.getContextPath() + "/index.xhtml");
        }
    }

}*/
