package pe.edu.upeu.vetandes.service.service;

import java.util.List;
import java.time.LocalDate;
import pe.edu.upeu.vetandes.dto.*;

/** Contrato de aplicación para AtencionService. */
public interface AtencionService {
    AtencionResponseDTO registrar(AtencionRequestDTO dto,String username);
    AtencionResponseDTO obtener(Long id);
    List<AtencionResponseDTO> listar(String mascota,String especie,String estado,LocalDate desde,LocalDate hasta);
    AtencionResponseDTO anular(Long id,AnularRequestDTO dto);
}
