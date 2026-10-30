package pe.edu.upeu.vetandes.dto;

import java.math.*;
import java.time.*;
import java.util.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record PrestacionRequestDTO(
 @NotBlank @Pattern(regexp="PRS-[0-9]{3}",message="Debe tener formato PRS-000") String codigo,
 @NotBlank @Size(min=3,max=80) String nombre,
 @NotNull @DecimalMin(value="0.00",inclusive=false) BigDecimal precio,
 @NotBlank @Pattern(regexp="PERRO|GATO|TODAS") String especieAplicable,
 @NotNull Boolean activo) {}
