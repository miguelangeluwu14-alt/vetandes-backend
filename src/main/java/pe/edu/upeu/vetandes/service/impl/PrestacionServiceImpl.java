package pe.edu.upeu.vetandes.service.impl;

import pe.edu.upeu.vetandes.service.service.PrestacionService;

import java.util.*;
import java.math.RoundingMode;
import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.vetandes.entity.*;
import pe.edu.upeu.vetandes.dto.*;
import pe.edu.upeu.vetandes.repository.*;
import pe.edu.upeu.vetandes.mapper.VetandesMapper;
import pe.edu.upeu.vetandes.exception.ReglaNegocioException;

@Service
public class PrestacionServiceImpl implements PrestacionService {
 private static final Logger log=LoggerFactory.getLogger(PrestacionService.class);
 private final PrestacionRepository repo;
 private final DetalleAtencionRepository detalles;
 private final VetandesMapper mapper;
 public PrestacionServiceImpl(PrestacionRepository repo,DetalleAtencionRepository detalles,VetandesMapper mapper) {
  this.repo=repo;this.detalles=detalles;this.mapper=mapper;
 }
 @Transactional(readOnly=true)
 public List<PrestacionResponseDTO> listar(String especie) {
  if(especie!=null && !Set.of("PERRO","GATO","OTRO").contains(especie))
   throw ReglaNegocioException.invalido("Especie inválida");
  return repo.findAllByOrderByNombreAsc().stream()
   .filter(p->especie==null || p.getEspecieAplicable().equals("TODAS") || p.getEspecieAplicable().equals(especie))
   .map(mapper::prestacion).toList();
 }
 @Transactional(readOnly=true)
 public PrestacionResponseDTO obtener(Long id){return mapper.prestacion(buscar(id));}
 private Prestacion buscar(Long id){return repo.findById(id).orElseThrow(()->ReglaNegocioException.noExiste("Prestación no encontrada"));}
 private void copiar(Prestacion p,PrestacionRequestDTO dto) {
  p.setCodigo(dto.codigo().trim().toUpperCase()); p.setNombre(dto.nombre().trim());
  p.setPrecio(dto.precio().setScale(2,RoundingMode.HALF_UP));p.setEspecieAplicable(dto.especieAplicable());p.setActivo(dto.activo()?1:0);
 }
 @Transactional public PrestacionResponseDTO crear(PrestacionRequestDTO dto){
  if(repo.existsByCodigoIgnoreCase(dto.codigo())) throw ReglaNegocioException.conflicto("El código de prestación ya existe");
  Prestacion p=new Prestacion();copiar(p,dto);repo.saveAndFlush(p);log.info("Prestación registrada id={}",p.getId());return mapper.prestacion(p);
 }
 @Transactional public PrestacionResponseDTO actualizar(Long id,PrestacionRequestDTO dto){
  Prestacion p=buscar(id);
  if(repo.existsByCodigoIgnoreCaseAndIdNot(dto.codigo(),id)) throw ReglaNegocioException.conflicto("El código de prestación ya existe");
  copiar(p,dto); repo.saveAndFlush(p);log.info("Prestación actualizada id={}",id);return mapper.prestacion(p);
 }
 @Transactional public PrestacionResponseDTO cambiarEstado(Long id,EstadoRequestDTO dto){
  Prestacion p=buscar(id);p.setActivo(dto.activo()?1:0);repo.saveAndFlush(p);
  log.info("Estado de prestación id={} activo={}",id,dto.activo());return mapper.prestacion(p);
 }
 @Transactional public void eliminar(Long id){
  Prestacion p=buscar(id);
  if(detalles.existsByPrestacionId(id)) throw ReglaNegocioException.conflicto("La prestación fue utilizada; use PATCH /prestaciones/{id}/estado para darla de baja");
  repo.delete(p);log.info("Prestación eliminada id={}",id);
 }
}
