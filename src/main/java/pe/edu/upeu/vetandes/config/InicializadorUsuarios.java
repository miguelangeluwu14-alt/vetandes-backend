package pe.edu.upeu.vetandes.config;

import org.springframework.context.annotation.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.*;
import pe.edu.upeu.vetandes.repository.UsuarioRepository;
import pe.edu.upeu.vetandes.entity.Usuario;

@Configuration
public class InicializadorUsuarios {
 private static final Logger log=LoggerFactory.getLogger(InicializadorUsuarios.class);
 @Bean CommandLineRunner cifrarUsuariosSemilla(UsuarioRepository usuarios,PasswordEncoder encoder){
  return args->{
   for(Usuario u:usuarios.findAll()) {
    if(u.getPassword()==null || u.getPassword().isBlank()) {
     u.setPassword(encoder.encode("Vet2026*"));usuarios.save(u);
     log.info("Contraseña de usuario semilla {} almacenada como BCrypt",u.getUsername());
    }
   }
  };
 }
}
