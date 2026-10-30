package pe.edu.upeu.vetandes.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
import pe.edu.upeu.vetandes.entity.*;

public interface AtencionRepository extends JpaRepository<Atencion,Long>,JpaSpecificationExecutor<Atencion> {
 boolean existsByMascotaId(Long mascotaId);
 @Query(value="SELECT atencion_numero_seq.NEXTVAL FROM dual",nativeQuery=true)
 Number siguienteNumero();
}
