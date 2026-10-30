package pe.edu.upeu.vetandes.controller;

import java.time.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import pe.edu.upeu.vetandes.dto.ReporteEspecieDTO;
import pe.edu.upeu.vetandes.service.service.ReporteService;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {
 private final ReporteService servicio;
 public ReporteController(ReporteService servicio){this.servicio=servicio;}
 @GetMapping("/atenciones-por-especie")
 @PreAuthorize("hasAuthority('REPORTE_VER')")
 public List<ReporteEspecieDTO> porEspecie(@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate desde,
 @RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate hasta){
  return servicio.porEspecie(desde,hasta);
 }
}
