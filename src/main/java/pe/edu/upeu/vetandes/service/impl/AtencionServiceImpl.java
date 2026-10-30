package pe.edu.upeu.vetandes.service.impl;

import pe.edu.upeu.vetandes.service.service.AtencionService;
import pe.edu.upeu.vetandes.service.CalculoAtencion;

import java.time.*;
import java.util.*;
import java.math.*;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.vetandes.dto.*;
import pe.edu.upeu.vetandes.entity.*;
import pe.edu.upeu.vetandes.mapper.VetandesMapper;
import pe.edu.upeu.vetandes.repository.*;
import pe.edu.upeu.vetandes.exception.ReglaNegocioException;

@Service
public class AtencionServiceImpl implements AtencionService {
 private static final Logger log=LoggerFactory.getLogger(AtencionService.class);
 private final AtencionRepository repo;
 private final DetalleAtencionRepository detalleRepo;
 private final MascotaRepository mascotas;
 private final PrestacionRepository prestaciones;
 private final VetandesMapper mapper;
 public AtencionServiceImpl(AtencionRepository r,DetalleAtencionRepository d,MascotaRepository m,PrestacionRepository p,VetandesMapper map){
  repo=r;detalleRepo=d;mascotas=m;prestaciones=p;mapper=map;
 }
 private Atencion buscar(Long id){return repo.findById(id).orElseThrow(()->ReglaNegocioException.noExiste("Atención no encontrada"));}
 @Transactional
 public AtencionResponseDTO registrar(AtencionRequestDTO dto,String username){
  Mascota mascota=mascotas.findById(dto.mascotaId()).orElseThrow(()->ReglaNegocioException.noExiste("Mascota no encontrada"));
  if(dto.detalles()==null || dto.detalles().isEmpty() || dto.detalles().size()>8) throw ReglaNegocioException.invalido("La atención debe tener entre 1 y 8 detalles");
  Set<Long> usadas=new HashSet<>();
  Atencion a=new Atencion();
  a.setNumero(String.format("AT-%06d",repo.siguienteNumero().longValue()));
  a.setFecha(LocalDateTime.now());a.setMascota(mascota);a.setPeso(dto.peso());a.setDiagnostico(dto.diagnostico());
  a.setEstado("REGISTRADA");a.setUsuarioRegistro(username);
  a.setSubtotal(BigDecimal.ZERO);a.setDescuento(BigDecimal.ZERO);a.setTotal(BigDecimal.ZERO);
  // Se guarda la cabecera y cada linea dentro de UNA transaccion: la falla del tercer detalle revierte todo.
  repo.saveAndFlush(a);
  BigDecimal subtotal=BigDecimal.ZERO;
  for(DetalleAtencionRequestDTO l:dto.detalles()) {
   if(l==null || l.prestacionId()==null || l.cantidad()==null || l.cantidad()<1 || l.cantidad()>3)
    throw ReglaNegocioException.invalido("La cantidad debe estar entre 1 y 3 y la prestación es obligatoria");
   if(!usadas.add(l.prestacionId())) throw ReglaNegocioException.invalido("No se puede repetir una prestación en la misma atención");
   Prestacion p=prestaciones.findById(l.prestacionId()).orElseThrow(()->ReglaNegocioException.noExiste("Prestación no encontrada"));
   if(p.getActivo()!=1) throw ReglaNegocioException.conflicto("La prestación "+p.getCodigo()+" está inactiva");
   if(!p.getEspecieAplicable().equals("TODAS") && !p.getEspecieAplicable().equals(mascota.getEspecie()))
    throw ReglaNegocioException.conflicto("La prestación "+p.getCodigo()+" no corresponde a la especie de la mascota");
   BigDecimal linea=CalculoAtencion.redondear(p.getPrecio().multiply(BigDecimal.valueOf(l.cantidad())));
   DetalleAtencion d=new DetalleAtencion();d.setAtencion(a);d.setPrestacion(p);d.setCantidad(l.cantidad());
   d.setPrecioUnitario(p.getPrecio());d.setSubtotal(linea);detalleRepo.saveAndFlush(d);
   subtotal=subtotal.add(linea);
  }
  subtotal=CalculoAtencion.redondear(subtotal);
  BigDecimal descuento=CalculoAtencion.descuento(subtotal,usadas.size());
  a.setSubtotal(subtotal);a.setDescuento(descuento);a.setTotal(subtotal.subtract(descuento));repo.saveAndFlush(a);
  log.info("Atención {} registrada con {} prestaciones y total {}",a.getNumero(),usadas.size(),a.getTotal());
  return mapper.atencion(a,detalleRepo.findByAtencionIdOrderByIdAsc(a.getId()));
 }
 @Transactional(readOnly=true) public AtencionResponseDTO obtener(Long id){Atencion a=buscar(id);return mapper.atencion(a,detalleRepo.findByAtencionIdOrderByIdAsc(id));}
 @Transactional(readOnly=true)
 public List<AtencionResponseDTO> listar(String mascota,String especie,String estado,LocalDate desde,LocalDate hasta) {
  if(especie!=null && !Set.of("PERRO","GATO","OTRO").contains(especie)) throw ReglaNegocioException.invalido("Especie inválida");
  if(estado!=null && !Set.of("REGISTRADA","ANULADA").contains(estado)) throw ReglaNegocioException.invalido("Estado inválido");
  if(desde!=null && hasta!=null && desde.isAfter(hasta)) throw ReglaNegocioException.invalido("Rango de fechas inválido");
  Specification<Atencion> spec=(root,query,cb)->{
   List<Predicate> p=new ArrayList<>();
   if(mascota!=null&&!mascota.isBlank()) p.add(cb.like(cb.lower(root.get("mascota").get("nombre")),"%"+mascota.trim().toLowerCase()+"%"));
   if(especie!=null) p.add(cb.equal(root.get("mascota").get("especie"),especie));
   if(estado!=null) p.add(cb.equal(root.get("estado"),estado));
   if(desde!=null) p.add(cb.greaterThanOrEqualTo(root.get("fecha"),desde.atStartOfDay()));
   if(hasta!=null) p.add(cb.lessThan(root.get("fecha"),hasta.plusDays(1).atStartOfDay()));
   return cb.and(p.toArray(Predicate[]::new));
  };
  return repo.findAll(spec,Sort.by(Sort.Direction.DESC,"fecha")).stream()
   .map(a->mapper.atencion(a,detalleRepo.findByAtencionIdOrderByIdAsc(a.getId()))).toList();
 }
 @Transactional public AtencionResponseDTO anular(Long id,AnularRequestDTO dto){
  Atencion a=buscar(id);
  if(!a.getEstado().equals("REGISTRADA")) throw ReglaNegocioException.conflicto("Solo se puede anular una atención REGISTRADA");
  if(a.getFecha().isBefore(LocalDateTime.now().minusHours(48))) throw ReglaNegocioException.conflicto("Han transcurrido más de 48 horas desde el registro");
  a.setEstado("ANULADA");a.setMotivoAnulacion(dto.motivo().trim());repo.saveAndFlush(a);
  log.info("Atención {} anulada",a.getNumero());return mapper.atencion(a,detalleRepo.findByAtencionIdOrderByIdAsc(id));
 }
}
