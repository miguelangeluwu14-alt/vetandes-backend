package pe.edu.upeu.vetandes.dto;

import java.math.*;
import java.time.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonInclude;

public record PrestacionResponseDTO(Long id,String codigo,String nombre,BigDecimal precio,String especieAplicable,boolean activo) {}
