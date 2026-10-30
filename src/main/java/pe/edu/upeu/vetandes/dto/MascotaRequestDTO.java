package pe.edu.upeu.vetandes.dto;

import java.math.*;
import java.time.*;
import java.util.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record MascotaRequestDTO(
 @NotBlank @Size(min=2,max=40) String nombre,
 @NotBlank @Pattern(regexp="PERRO|GATO|OTRO") String especie,
 @Size(max=40) String raza,
 @NotNull @PastOrPresent(message="La fecha de nacimiento no puede ser futura") LocalDate fechaNacimiento,
 @NotBlank @Pattern(regexp="M|H") String sexo,
 @NotNull Long propietarioId) {}
