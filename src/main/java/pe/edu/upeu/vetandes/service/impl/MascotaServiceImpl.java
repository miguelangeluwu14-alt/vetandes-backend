package pe.edu.upeu.vetandes.service.impl;

import pe.edu.upeu.vetandes.service.service.MascotaService;

import java.util.*;
import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.vetandes.entity.*;
import pe.edu.upeu.vetandes.dto.*;
import pe.edu.upeu.vetandes.repository.*;
import pe.edu.upeu.vetandes.mapper.VetandesMapper;
import pe.edu.upeu.vetandes.exception.ReglaNegocioException;

@Service
public class MascotaServiceImpl implements MascotaService {
 private static final Logger log=LoggerFactory.getLogger(MascotaService.class);
 private final MascotaRepository repo;
 private final PropietarioRepository propietarios;
 private final AtencionRepository atenciones;
 private final VetandesMapper mapper;
 public MascotaServiceImpl(MascotaRepository r,PropietarioRepository p,AtencionRepository a,VetandesMapper mapper){this.repo=r;this.propietarios=p;this.atenciones=a;this.mapper=mapper;}
 @Transactional(readOnly=true) public List<MascotaResponseDTO> listar(Long propietarioId){
  if(propietarioId!=null && !propietarios.existsById(propietarioId)) throw ReglaNegocioException.noExiste("Propietario no encontrado");
  return (propietarioId==null?repo.findAllByOrderByNombreAsc():repo.findByPropietarioIdOrderByNombreAsc(propietarioId)).stream().map(mapper::mascota).toList();
 }
 @Transactional(readOnly=true) public List<PropietarioResponseDTO> propietarios(){return propietarios.findAll().stream().map(mapper::propietario).toList();}
 private Mascota buscar(Long id){return repo.findById(id).orElseThrow(()->ReglaNegocioException.noExiste("Mascota no encontrada"));}
 @Transactional(readOnly=true) public MascotaResponseDTO obtener(Long id){return mapper.mascota(buscar(id));}
 private void copiar(Mascota m,MascotaRequestDTO dto){
  Propietario p=propietarios.findById(dto.propietarioId()).orElseThrow(()->ReglaNegocioException.noExiste("Propietario no encontrado"));
  m.setNombre(dto.nombre().trim());m.setEspecie(dto.especie());m.setRaza(dto.raza());m.setFechaNacimiento(dto.fechaNacimiento());m.setSexo(dto.sexo());m.setPropietario(p);
 }
 @Transactional public MascotaResponseDTO crear(MascotaRequestDTO dto){
  if(repo.existsByPropietarioIdAndNombreIgnoreCase(dto.propietarioId(),dto.nombre().trim()))
   throw ReglaNegocioException.conflicto("El propietario ya tiene una mascota con ese nombre");
  Mascota m=new Mascota();copiar(m,dto);repo.saveAndFlush(m);log.info("Mascota registrada id={}",m.getId());return mapper.mascota(m);
 }
 @Transactional public MascotaResponseDTO actualizar(Long id,MascotaRequestDTO dto){
  Mascota m=buscar(id);
  if(repo.existsByPropietarioIdAndNombreIgnoreCaseAndIdNot(dto.propietarioId(),dto.nombre().trim(),id))
   throw ReglaNegocioException.conflicto("El propietario ya tiene una mascota con ese nombre");
  copiar(m,dto);repo.saveAndFlush(m);log.info("Mascota actualizada id={}",id);return mapper.mascota(m);
 }
 @Transactional public void eliminar(Long id){
  Mascota m=buscar(id);
  if(atenciones.existsByMascotaId(id)) throw ReglaNegocioException.conflicto("No se puede eliminar una mascota que tiene atenciones");
  repo.delete(m);log.info("Mascota eliminada id={}",id);
 }
}
