package pe.edu.upeu.vetandes.config;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import io.jsonwebtoken.*;
import pe.edu.upeu.vetandes.security.*;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
 private final JwtService jwt;
 private final UsuarioDetailsService usuarioDetails;
 public JwtAuthenticationFilter(JwtService jwt,UsuarioDetailsService details){this.jwt=jwt;this.usuarioDetails=details;}
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)
 throws ServletException,IOException {
  String encabezado=req.getHeader("Authorization");
  if(encabezado==null || !encabezado.startsWith("Bearer ")) {chain.doFilter(req,res);return;}
  try {
   String username=jwt.username(encabezado.substring(7));
   UserDetails user=usuarioDetails.loadUserByUsername(username);
   if(!user.isEnabled()) {RestErrorWriter.write(req,res,401,"Usuario inactivo");return;}
   UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
   SecurityContextHolder.getContext().setAuthentication(auth);
   chain.doFilter(req,res);
  } catch(ExpiredJwtException ex){SecurityContextHolder.clearContext();RestErrorWriter.write(req,res,401,"El token ha expirado. Inicie sesión nuevamente");}
    catch(JwtException|IllegalArgumentException|UsernameNotFoundException ex){SecurityContextHolder.clearContext();RestErrorWriter.write(req,res,401,"El token no es válido");}
 }
}
