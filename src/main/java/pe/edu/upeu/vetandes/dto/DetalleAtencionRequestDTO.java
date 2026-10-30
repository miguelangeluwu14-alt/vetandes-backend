package pe.edu.upeu.vetandes.dto;

import java.math.*;
import java.time.*;
import java.util.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record DetalleAtencionRequestDTO(
 @NotNull Long prestacionId, @NotNull @Min(1) @Max(3) Integer cantidad) {}
