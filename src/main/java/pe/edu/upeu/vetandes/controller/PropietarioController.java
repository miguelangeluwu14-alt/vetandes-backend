package pe.edu.upeu.vetandes.controller;

import java.util.*;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.vetandes.dto.*;
import pe.edu.upeu.vetandes.service.service.MascotaService;

@RestController
@RequestMapping("/api/v1/propietarios")
public class PropietarioController {
 private final MascotaService servicio;
 public PropietarioController(MascotaService servicio){this.servicio=servicio;}
 @GetMapping public List<PropietarioResponseDTO> listar(){return servicio.propietarios();}
}
