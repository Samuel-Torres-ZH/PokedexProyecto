package Cibertec.edu.pe.PokeHome.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import Cibertec.edu.pe.PokeHome.model.Pokemon;

import Cibertec.edu.pe.PokeHome.repository.PokemonRepository;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository repo;
    

    public PokemonServiceImpl(PokemonRepository repo) {
        this.repo = repo;
        
    }
    @Override
    public List<Pokemon> listarTodos() {
        return repo.findAll();
    }

    @Override
    public Optional<Pokemon> buscarPorId(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Pokemon guardar(Pokemon pokemon) {
        return repo.save(pokemon);
    }

    @Override
    public void eliminar(Pokemon pokemon) {
        repo.delete(pokemon);
    }
}
