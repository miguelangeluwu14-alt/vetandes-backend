package pe.edu.upeu.vetandes.controller;

import java.util.*;
import java.time.LocalDate;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import pe.edu.upeu.vetandes.dto.*;
import pe.edu.upeu.vetandes.service.service.AtencionService;

@RestController
@RequestMapping("/api/v1/atenciones")
public class AtencionController {
 private final AtencionService servicio;
 public AtencionController(AtencionService servicio){this.servicio=servicio;}
 @PostMapping public ResponseEntity<AtencionResponseDTO> crear(@Valid @RequestBody AtencionRequestDTO datos,Authentication auth){
  return ResponseEntity.status(HttpStatus.CREATED).body(servicio.registrar(datos,auth.getName()));
 }
 @GetMapping public List<AtencionResponseDTO> listar(@RequestParam(required=false) String mascota,
 @RequestParam(required=false) String especie,@RequestParam(required=false) String estado,
 @RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate desde,
 @RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate hasta){
  return servicio.listar(mascota,especie,estado,desde,hasta);
 }
 @GetMapping("/{id}") public AtencionResponseDTO obtener(@PathVariable Long id){return servicio.obtener(id);}
 @PatchMapping("/{id}/anular") public AtencionResponseDTO anular(@PathVariable Long id,@Valid @RequestBody AnularRequestDTO datos){
  return servicio.anular(id,datos);
 }
}
