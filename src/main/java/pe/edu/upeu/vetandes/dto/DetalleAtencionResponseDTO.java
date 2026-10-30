package pe.edu.upeu.vetandes.dto;

import java.math.*;
import java.time.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonInclude;

public record DetalleAtencionResponseDTO(Long prestacionId,String prestacionNombre,Integer cantidad,BigDecimal precioUnitario,BigDecimal subtotal) {}
