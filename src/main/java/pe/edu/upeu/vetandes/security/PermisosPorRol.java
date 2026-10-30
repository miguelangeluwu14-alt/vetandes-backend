package pe.edu.upeu.vetandes.security;

import java.util.*;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pe.edu.upeu.vetandes.entity.*;

@Component
public class PermisosPorRol {
 private static final Map<String,Set<Permiso>> MAP = Map.of(
  "ADMIN",EnumSet.allOf(Permiso.class),
  "RECEPCIONISTA",EnumSet.of(Permiso.PRESTACION_LEER,Permiso.MASCOTA_LEER,Permiso.MASCOTA_ESCRIBIR,Permiso.ATENCION_LEER,Permiso.ATENCION_REGISTRAR),
  "VETERINARIO",EnumSet.of(Permiso.PRESTACION_LEER,Permiso.PRESTACION_ESCRIBIR,Permiso.MASCOTA_LEER,Permiso.ATENCION_LEER,Permiso.ATENCION_ANULAR,Permiso.REPORTE_VER)
 );
 public List<String> roles(Usuario usuario){return usuario.getRoles().stream().map(Rol::getNombre).sorted().toList();}
 public List<String> permisos(Usuario usuario){
  Set<Permiso> result=EnumSet.noneOf(Permiso.class);
  usuario.getRoles().forEach(r->result.addAll(MAP.getOrDefault(r.getNombre(),Set.of())));
  return result.stream().map(Enum::name).sorted().toList();
 }
 public List<GrantedAuthority> authorities(Usuario usuario){
  List<GrantedAuthority> auth=new ArrayList<>();
  for(String rol:roles(usuario)) auth.add(new SimpleGrantedAuthority("ROLE_"+rol));
  for(String permiso:permisos(usuario)) auth.add(new SimpleGrantedAuthority(permiso));
  return auth;
 }
}
