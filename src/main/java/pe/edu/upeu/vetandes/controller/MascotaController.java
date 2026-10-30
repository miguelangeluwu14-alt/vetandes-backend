package pe.edu.upeu.vetandes.controller;

import java.util.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.vetandes.dto.*;
import pe.edu.upeu.vetandes.service.service.MascotaService;

@RestController
@RequestMapping("/api/v1/mascotas")
public class MascotaController {
 private final MascotaService servicio;
 public MascotaController(MascotaService servicio){this.servicio=servicio;}
 @GetMapping public List<MascotaResponseDTO> listar(@RequestParam(required=false) Long propietarioId){return servicio.listar(propietarioId);}
 @GetMapping("/{id}") public MascotaResponseDTO obtener(@PathVariable Long id){return servicio.obtener(id);}
 @PostMapping public ResponseEntity<MascotaResponseDTO> crear(@Valid @RequestBody MascotaRequestDTO datos){
  return ResponseEntity.status(HttpStatus.CREATED).body(servicio.crear(datos));
 }
 @PutMapping("/{id}") public MascotaResponseDTO editar(@PathVariable Long id,@Valid @RequestBody MascotaRequestDTO datos){return servicio.actualizar(id,datos);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable Long id){servicio.eliminar(id);}
}
