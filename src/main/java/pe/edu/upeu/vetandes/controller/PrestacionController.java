package pe.edu.upeu.vetandes.controller;

import java.util.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.vetandes.dto.*;
import pe.edu.upeu.vetandes.service.service.PrestacionService;

@RestController
@RequestMapping("/api/v1/prestaciones")
public class PrestacionController {
 private final PrestacionService servicio;
 public PrestacionController(PrestacionService servicio){this.servicio=servicio;}
 @GetMapping public List<PrestacionResponseDTO> listar(@RequestParam(required=false) String especie){return servicio.listar(especie);}
 @GetMapping("/{id}") public PrestacionResponseDTO obtener(@PathVariable Long id){return servicio.obtener(id);}
 @PostMapping public ResponseEntity<PrestacionResponseDTO> crear(@Valid @RequestBody PrestacionRequestDTO datos){
  return ResponseEntity.status(HttpStatus.CREATED).body(servicio.crear(datos));
 }
 @PutMapping("/{id}") public PrestacionResponseDTO editar(@PathVariable Long id,@Valid @RequestBody PrestacionRequestDTO datos){return servicio.actualizar(id,datos);}
 @PatchMapping("/{id}/estado") public PrestacionResponseDTO estado(@PathVariable Long id,@Valid @RequestBody EstadoRequestDTO datos){return servicio.cambiarEstado(id,datos);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable Long id){servicio.eliminar(id);}
}
