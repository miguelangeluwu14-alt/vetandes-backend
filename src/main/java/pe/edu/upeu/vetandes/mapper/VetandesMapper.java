package pe.edu.upeu.vetandes.mapper;

import org.springframework.stereotype.Component;
import java.util.*;
import pe.edu.upeu.vetandes.dto.*;
import pe.edu.upeu.vetandes.entity.*;

@Component
public class VetandesMapper {
 public PrestacionResponseDTO prestacion(Prestacion p) {
  return new PrestacionResponseDTO(p.getId(),p.getCodigo(),p.getNombre(),p.getPrecio(),p.getEspecieAplicable(),p.getActivo()==1);
 }
 public PropietarioResponseDTO propietario(Propietario p) {return new PropietarioResponseDTO(p.getId(),p.getDni(),p.getNombres(),p.getTelefono());}
 public MascotaResponseDTO mascota(Mascota m) {
  return new MascotaResponseDTO(m.getId(),m.getNombre(),m.getEspecie(),m.getRaza(),m.getFechaNacimiento(),m.getSexo(),m.getPropietario().getId(),m.getPropietario().getNombres());
 }
 public AtencionResponseDTO atencion(Atencion a,List<DetalleAtencion> lineas) {
  List<DetalleAtencionResponseDTO> items=lineas.stream().map(d->new DetalleAtencionResponseDTO(
    d.getPrestacion().getId(),d.getPrestacion().getNombre(),d.getCantidad(),d.getPrecioUnitario(),d.getSubtotal())).toList();
  return new AtencionResponseDTO(a.getId(),a.getNumero(),a.getFecha(),a.getMascota().getId(),a.getMascota().getNombre(),
    a.getMascota().getEspecie(),a.getPeso(),a.getDiagnostico(),a.getEstado(),a.getSubtotal(),a.getDescuento(),
    a.getTotal(),a.getUsuarioRegistro(),a.getMotivoAnulacion(),items);
 }
}
