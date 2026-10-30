package pe.edu.upeu.vetandes.service.service;

import java.util.List;
import java.time.LocalDate;
import pe.edu.upeu.vetandes.dto.ReporteEspecieDTO;

/** Contrato de aplicación para ReporteService. */
public interface ReporteService {
    List<ReporteEspecieDTO> porEspecie(LocalDate desde,LocalDate hasta);
}
