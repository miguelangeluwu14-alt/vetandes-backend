package pe.edu.upeu.vetandes.dto;

import java.math.*;
import java.time.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonInclude;

public record MascotaResponseDTO(Long id,String nombre,String especie,String raza,LocalDate fechaNacimiento,String sexo,Long propietarioId,String propietarioNombre) {}
