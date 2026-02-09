package Cibertec.edu.pe.PokeHome.service;

import java.util.List;
import java.util.Optional;

import Cibertec.edu.pe.PokeHome.model.Pokemon;

public interface PokemonService {

	List<Pokemon> listarTodos();

    Optional<Pokemon> buscarPorId(Integer id);

    Pokemon guardar(Pokemon pokemon);

    void eliminar(Pokemon pokemon);
    }

