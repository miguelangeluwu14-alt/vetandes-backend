package pe.edu.upeu.vetandes.security;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.*;
import pe.edu.upeu.vetandes.repository.UsuarioRepository;
import pe.edu.upeu.vetandes.entity.Usuario;

@Service
public class UsuarioDetailsService implements UserDetailsService {
 private final UsuarioRepository repo;
 private final PermisosPorRol permisos;
 public UsuarioDetailsService(UsuarioRepository repo,PermisosPorRol permisos){this.repo=repo;this.permisos=permisos;}
 @Override @Transactional(readOnly=true)
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
  Usuario u=repo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Usuario inexistente"));
  return User.withUsername(u.getUsername()).password(u.getPassword()==null?"":u.getPassword())
   .disabled(u.getActivo()!=1).authorities(permisos.authorities(u)).build();
 }
}
