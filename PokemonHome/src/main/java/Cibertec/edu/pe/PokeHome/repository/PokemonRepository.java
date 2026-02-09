package Cibertec.edu.pe.PokeHome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Cibertec.edu.pe.PokeHome.model.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
}
