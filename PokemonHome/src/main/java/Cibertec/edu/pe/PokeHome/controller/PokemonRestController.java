package Cibertec.edu.pe.PokeHome.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Cibertec.edu.pe.PokeHome.model.Pokemon;
import Cibertec.edu.pe.PokeHome.service.PokemonService;

// AGREGAR allowCredentials = "true" SIEMPRE
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api/pokemons")
public class PokemonRestController {

    private final PokemonService pokemonService;

    public PokemonRestController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    // GET - Listar todos (Lo usan Admin y Usuarios)
    @GetMapping
    public List<Pokemon> listar() {
        return pokemonService.listarTodos();
    }

    @GetMapping("/{id}")
    public Pokemon obtener(@PathVariable Integer id) {
        return pokemonService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pokemon no encontrado"));
    }

    // POST - Crear (Solo debería usarlo el Admin)
    @PostMapping
    public Pokemon crear(@RequestBody Pokemon pokemon) {
        return pokemonService.guardar(pokemon);
    }

    @PutMapping("/{id}")
    public Pokemon actualizar(@PathVariable Integer id, @RequestBody Pokemon pokemon) {
        Pokemon existente = pokemonService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pokemon no encontrado"));

        existente.setNombre(pokemon.getNombre());
        existente.setTipo(pokemon.getTipo());
        // Agrega aquí más campos si tu modelo tiene imagen, ataque, etc.

        return pokemonService.guardar(existente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return pokemonService.buscarPorId(id)
                .map(p -> {
                    pokemonService.eliminar(p);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}