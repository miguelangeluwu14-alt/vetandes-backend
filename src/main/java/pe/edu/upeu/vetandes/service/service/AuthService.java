package pe.edu.upeu.vetandes.service.service;

import pe.edu.upeu.vetandes.dto.*;

/** Contrato de aplicación para AuthService. */
public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO datos);
    UsuarioActualResponseDTO yo(String username);
}
