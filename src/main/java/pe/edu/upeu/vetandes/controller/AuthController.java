package pe.edu.upeu.vetandes.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import pe.edu.upeu.vetandes.dto.*;
import pe.edu.upeu.vetandes.service.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
 private final AuthService servicio;
 public AuthController(AuthService servicio){this.servicio=servicio;}
 @PostMapping("/login") public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO request){return servicio.login(request);}
 @GetMapping("/me") public UsuarioActualResponseDTO yo(Authentication auth){return servicio.yo(auth.getName());}
}
