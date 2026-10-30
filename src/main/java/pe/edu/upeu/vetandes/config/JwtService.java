package pe.edu.upeu.vetandes.config;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
 private final SecretKey clave;
 private final long minutos;
 public JwtService(@Value("${app.jwt.secret}") String secreto,@Value("${app.jwt.expiracion-minutos:60}") long minutos){
  if(secreto==null || secreto.isBlank() || secreto.equals("GENERAR_SECRETO_ALEATORIO")) throw new IllegalArgumentException("Configure JWT_SECRET_BASE64 con 48 bytes aleatorios en Base64");
  byte[] bytes=Decoders.BASE64.decode(secreto);
  if(bytes.length<48) throw new IllegalArgumentException("JWT necesita como mínimo 48 bytes para HS384");
  this.clave=Keys.hmacShaKeyFor(bytes);this.minutos=minutos;
 }
 public long expiracionSegundos(){return minutos*60;}
 public String generarToken(UserDetails u) {
  Instant ahora=Instant.now();
  return Jwts.builder().subject(u.getUsername())
   .claim("authorities",u.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
   .issuedAt(Date.from(ahora)).expiration(Date.from(ahora.plus(minutos,ChronoUnit.MINUTES)))
   .signWith(clave,Jwts.SIG.HS384).compact();
 }
 public String username(String token){return Jwts.parser().verifyWith(clave).build().parseSignedClaims(token).getPayload().getSubject();}
}
