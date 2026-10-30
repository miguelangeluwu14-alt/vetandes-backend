package pe.edu.upeu.vetandes.service.service;

import java.util.List;
import pe.edu.upeu.vetandes.dto.*;

/** Contrato de aplicación para MascotaService. */
public interface MascotaService {
    List<MascotaResponseDTO> listar(Long propietarioId);
    List<PropietarioResponseDTO> propietarios();
    MascotaResponseDTO obtener(Long id);
    MascotaResponseDTO crear(MascotaRequestDTO dto);
    MascotaResponseDTO actualizar(Long id,MascotaRequestDTO dto);
    void eliminar(Long id);
}
