package pe.edu.upeu.vetandes.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
import pe.edu.upeu.vetandes.entity.*;

public interface MascotaRepository extends JpaRepository<Mascota,Long> {
 boolean existsByPropietarioIdAndNombreIgnoreCase(Long propietarioId,String nombre);
 boolean existsByPropietarioIdAndNombreIgnoreCaseAndIdNot(Long propietarioId,String nombre,Long id);
 List<Mascota> findByPropietarioIdOrderByNombreAsc(Long id);
 List<Mascota> findAllByOrderByNombreAsc();
}
