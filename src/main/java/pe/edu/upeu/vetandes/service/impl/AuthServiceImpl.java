package pe.edu.upeu.vetandes.service.impl;

import pe.edu.upeu.vetandes.service.service.AuthService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.*;
import pe.edu.upeu.vetandes.config.JwtService;
import pe.edu.upeu.vetandes.security.PermisosPorRol;
import pe.edu.upeu.vetandes.repository.UsuarioRepository;
import pe.edu.upeu.vetandes.entity.Usuario;
import pe.edu.upeu.vetandes.dto.*;
import pe.edu.upeu.vetandes.exception.ReglaNegocioException;

@Service
public class AuthServiceImpl implements AuthService {
 private final UsuarioRepository usuarios;private final PasswordEncoder encoder;
 private final PermisosPorRol permisos;private final JwtService jwt;
 public AuthServiceImpl(UsuarioRepository usuarios,PasswordEncoder encoder,PermisosPorRol permisos,JwtService jwt){
  this.usuarios=usuarios;this.encoder=encoder;this.permisos=permisos;this.jwt=jwt;
 }
 @Transactional(readOnly=true)
 public LoginResponseDTO login(LoginRequestDTO datos){
  Usuario u=usuarios.findByUsername(datos.username()).orElse(null);
  if(u==null || u.getActivo()!=1 || u.getPassword()==null || !encoder.matches(datos.password(),u.getPassword()))
   throw new ReglaNegocioException(HttpStatus.UNAUTHORIZED,"Usuario o contraseña incorrectos");
  UserDetails ud=User.withUsername(u.getUsername()).password(u.getPassword()).authorities(permisos.authorities(u)).build();
  return new LoginResponseDTO(jwt.generarToken(ud),"Bearer",jwt.expiracionSegundos(),u.getUsername(),u.getNombreCompleto(),permisos.roles(u),permisos.permisos(u));
 }
 @Transactional(readOnly=true) public UsuarioActualResponseDTO yo(String username){
  Usuario u=usuarios.findByUsername(username).orElseThrow(()->ReglaNegocioException.noExiste("Usuario no encontrado"));
  return new UsuarioActualResponseDTO(u.getUsername(),u.getNombreCompleto(),permisos.roles(u),permisos.permisos(u));
 }
}
