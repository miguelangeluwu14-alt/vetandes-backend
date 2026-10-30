package pe.edu.upeu.vetandes.security;

import java.io.IOException;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
 @Override public void commence(HttpServletRequest req,HttpServletResponse res,AuthenticationException ex)throws IOException {
  RestErrorWriter.write(req,res,401,"Debe iniciar sesión para acceder al recurso");
 }
}
