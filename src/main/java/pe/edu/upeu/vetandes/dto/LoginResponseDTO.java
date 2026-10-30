package pe.edu.upeu.vetandes.dto;

import java.math.*;
import java.time.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonInclude;

public record LoginResponseDTO(String token,String tipo,Long expiraEnSegundos,String username,String nombreCompleto,List<String> roles,List<String> permisos) {}
