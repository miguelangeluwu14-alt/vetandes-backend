package pe.edu.upeu.vetandes.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
import pe.edu.upeu.vetandes.entity.*;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
 Optional<Usuario> findByUsername(String username);
}
