package pe.edu.upeu.vetandes.service.impl;

import pe.edu.upeu.vetandes.service.service.ReporteService;

import java.time.*;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.vetandes.repository.ReporteRepository;
import pe.edu.upeu.vetandes.dto.ReporteEspecieDTO;
import pe.edu.upeu.vetandes.exception.ReglaNegocioException;

@Service
public class ReporteServiceImpl implements ReporteService {
 private final ReporteRepository repo;
 public ReporteServiceImpl(ReporteRepository repo){this.repo=repo;}
 @Transactional(readOnly=true) public List<ReporteEspecieDTO> porEspecie(LocalDate desde,LocalDate hasta){
  if(desde!=null && hasta!=null && desde.isAfter(hasta)) throw ReglaNegocioException.invalido("Desde debe ser anterior o igual a hasta");
  LocalDateTime ini=(desde==null?LocalDate.of(1900,1,1):desde).atStartOfDay();
  LocalDateTime fin=(hasta==null?LocalDate.of(2100,1,1):hasta.plusDays(1)).atStartOfDay();
  return repo.atencionesPorEspecie(ini,fin);
 }
}
