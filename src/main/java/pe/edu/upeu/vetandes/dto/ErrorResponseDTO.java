package pe.edu.upeu.vetandes.dto;

import java.math.*;
import java.time.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponseDTO(LocalDateTime timestamp,int status,String error,String mensaje,String ruta,Map<String,String> campos) {}
