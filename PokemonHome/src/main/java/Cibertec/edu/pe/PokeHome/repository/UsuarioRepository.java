package Cibertec.edu.pe.PokeHome.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import Cibertec.edu.pe.PokeHome.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
}
