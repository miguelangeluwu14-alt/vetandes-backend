package pe.edu.upeu.vetandes.dto;

import java.math.*;
import java.time.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonInclude;

public record PropietarioResponseDTO(Long id,String dni,String nombres,String telefono) {}
