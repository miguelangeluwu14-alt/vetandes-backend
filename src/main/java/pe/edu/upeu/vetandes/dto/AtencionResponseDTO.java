package pe.edu.upeu.vetandes.dto;

import java.math.*;
import java.time.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonInclude;

public record AtencionResponseDTO(Long id,String numero,LocalDateTime fecha,Long mascotaId,String mascotaNombre,String especie,BigDecimal peso,String diagnostico,String estado,BigDecimal subtotal,BigDecimal descuento,BigDecimal total,String usuarioRegistro,String motivoAnulacion,List<DetalleAtencionResponseDTO> detalles) {}
