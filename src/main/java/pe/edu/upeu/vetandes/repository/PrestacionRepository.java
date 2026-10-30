package pe.edu.upeu.vetandes.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
import pe.edu.upeu.vetandes.entity.*;

public interface PrestacionRepository extends JpaRepository<Prestacion,Long> {
 boolean existsByCodigoIgnoreCase(String codigo);
 boolean existsByCodigoIgnoreCaseAndIdNot(String codigo,Long id);
 List<Prestacion> findAllByOrderByNombreAsc();
}
