package pe.edu.upeu.vetandes.config;

import org.springframework.context.annotation.*;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;

@Configuration
public class OpenApiConfig {
 @Bean public OpenAPI vetandesApi(){
  return new OpenAPI().info(new Info().title("VetAndes API").version("1.0.0").description("Caso VetAndes - Examen Parcial II, Version B"))
   .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
   .components(new Components().addSecuritySchemes("bearerAuth",new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
 }
}
