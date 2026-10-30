package pe.edu.upeu.vetandes.dto;

import java.math.*;
import java.time.*;
import java.util.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record AtencionRequestDTO(
 @NotNull Long mascotaId,
 @NotNull @DecimalMin(value="0.0",inclusive=false) BigDecimal peso,
 @Size(max=300) String diagnostico,
 @NotNull @Size(min=1,max=8) List<@Valid DetalleAtencionRequestDTO> detalles) {}
