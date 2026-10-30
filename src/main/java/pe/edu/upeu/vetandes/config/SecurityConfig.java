
package pe.edu.upeu.vetandes.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import pe.edu.upeu.vetandes.security.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

 @Bean
 PasswordEncoder passwordEncoder() {
  return new BCryptPasswordEncoder();
 }

 @Bean
 SecurityFilterChain securityFilterChain(
         HttpSecurity http,
         JwtService jwtService,
         UsuarioDetailsService details,
         RestAuthenticationEntryPoint entry,
         RestAccessDeniedHandler denied,
         @Qualifier("corsConfigurationSource")
         CorsConfigurationSource cors
 ) throws Exception {

  http
          .csrf(AbstractHttpConfigurer::disable)

          .cors(c -> c.configurationSource(cors))

          .sessionManagement(s ->
                  s.sessionCreationPolicy(
                          SessionCreationPolicy.STATELESS
                  )
          )

          .exceptionHandling(e -> e
                  .authenticationEntryPoint(entry)
                  .accessDeniedHandler(denied)
          )

          .authorizeHttpRequests(a -> a

                  // Rutas públicas
                  .requestMatchers(
                          HttpMethod.OPTIONS, "/**"
                  ).permitAll()

                  .requestMatchers(
                          "/api/v1/auth/login",
                          "/swagger-ui.html",
                          "/swagger-ui/**",
                          "/v3/api-docs/**",
                          "/v3/api-docs"
                  ).permitAll()

                  // Usuario autenticado
                  .requestMatchers(
                          HttpMethod.GET,
                          "/api/v1/auth/me"
                  ).authenticated()

                  // Propietarios
                  .requestMatchers(
                          HttpMethod.GET,
                          "/api/v1/propietarios"
                  ).hasAuthority("MASCOTA_LEER")

                  // Prestaciones
                  .requestMatchers(
                          HttpMethod.GET,
                          "/api/v1/prestaciones",
                          "/api/v1/prestaciones/**"
                  ).hasAuthority("PRESTACION_LEER")

                  .requestMatchers(
                          "/api/v1/prestaciones/**",
                          "/api/v1/prestaciones"
                  ).hasAuthority("PRESTACION_ESCRIBIR")

                  // Mascotas
                  .requestMatchers(
                          HttpMethod.GET,
                          "/api/v1/mascotas",
                          "/api/v1/mascotas/**"
                  ).hasAuthority("MASCOTA_LEER")

                  .requestMatchers(
                          "/api/v1/mascotas/**",
                          "/api/v1/mascotas"
                  ).hasAuthority("MASCOTA_ESCRIBIR")

                  // Consultar atenciones
                  .requestMatchers(
                          HttpMethod.GET,
                          "/api/v1/atenciones",
                          "/api/v1/atenciones/*"
                  ).hasAuthority("ATENCION_LEER")

                  // Registrar atenciones
                  .requestMatchers(
                          HttpMethod.POST,
                          "/api/v1/atenciones"
                  ).hasAuthority("ATENCION_REGISTRAR")

                  // Anular atenciones
                  .requestMatchers(
                          HttpMethod.PATCH,
                          "/api/v1/atenciones/*/anular"
                  ).hasAuthority("ATENCION_ANULAR")

                  // Reportes
                  .requestMatchers(
                          "/api/v1/reportes/**"
                  ).hasAuthority("REPORTE_VER")

                  // Otras rutas requieren autenticación
                  .anyRequest().authenticated()
          )

          .addFilterBefore(
                  new JwtAuthenticationFilter(
                          jwtService,
                          details
                  ),
                  UsernamePasswordAuthenticationFilter.class
          );

  return http.build();
 }
}
