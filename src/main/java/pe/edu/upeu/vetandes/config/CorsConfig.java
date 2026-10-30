package pe.edu.upeu.vetandes.config;

import java.util.*;
import org.springframework.context.annotation.*;
import org.springframework.web.cors.*;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
 @Bean public CorsConfigurationSource corsConfigurationSource(){
  CorsConfiguration c=new CorsConfiguration();
  c.setAllowedOrigins(List.of("http://localhost:4200"));
  c.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
  c.setAllowedHeaders(List.of("Authorization","Content-Type"));
  c.setExposedHeaders(List.of("Authorization"));
  UrlBasedCorsConfigurationSource src=new UrlBasedCorsConfigurationSource();src.registerCorsConfiguration("/**",c);return src;
 }
}
