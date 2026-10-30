package pe.edu.upeu.vetandes.repository;

import java.util.*;
import java.time.*;
import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.vetandes.entity.Atencion;
import pe.edu.upeu.vetandes.dto.ReporteEspecieDTO;

public interface ReporteRepository extends Repository<Atencion,Long> {
 @Query("""
  SELECT new pe.edu.upeu.vetandes.dto.ReporteEspecieDTO(m.especie,COUNT(a.id),SUM(a.total))
  FROM Atencion a JOIN a.mascota m
  WHERE a.estado <> 'ANULADA' AND a.fecha >= :desde AND a.fecha < :hasta
  GROUP BY m.especie ORDER BY SUM(a.total) DESC
 """)
 List<ReporteEspecieDTO> atencionesPorEspecie(@Param("desde") LocalDateTime desde,@Param("hasta") LocalDateTime hasta);
}
