package pe.edu.upeu.vetandes.security;

import java.io.IOException;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
 @Override public void handle(HttpServletRequest req,HttpServletResponse res,AccessDeniedException ex)throws IOException {
  RestErrorWriter.write(req,res,403,"No tiene permisos para realizar esta operación");
 }
}
