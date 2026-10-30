package pe.edu.upeu.vetandes.dto;

import java.math.*;
import java.time.*;
import java.util.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record AnularRequestDTO(
 @NotBlank @Size(min=10,max=200) String motivo) {}
