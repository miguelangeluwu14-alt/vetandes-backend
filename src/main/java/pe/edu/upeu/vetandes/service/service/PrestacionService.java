package pe.edu.upeu.vetandes.service.service;

import java.util.List;
import pe.edu.upeu.vetandes.dto.*;

/** Contrato de aplicación para PrestacionService. */
public interface PrestacionService {
    List<PrestacionResponseDTO> listar(String especie);
    PrestacionResponseDTO obtener(Long id);
    PrestacionResponseDTO crear(PrestacionRequestDTO dto);
    PrestacionResponseDTO actualizar(Long id,PrestacionRequestDTO dto);
    PrestacionResponseDTO cambiarEstado(Long id,EstadoRequestDTO dto);
    void eliminar(Long id);
}
