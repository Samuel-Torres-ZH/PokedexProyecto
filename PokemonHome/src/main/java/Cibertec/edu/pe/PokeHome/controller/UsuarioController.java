package Cibertec.edu.pe.PokeHome.controller;

import java.security.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import Cibertec.edu.pe.PokeHome.model.Pokemon;
import Cibertec.edu.pe.PokeHome.model.Usuario;
import Cibertec.edu.pe.PokeHome.service.PokemonService;
import Cibertec.edu.pe.PokeHome.service.UsuarioService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PokemonService pokemonService;

    @PersistenceContext
    private EntityManager entityManager;

    public UsuarioController(UsuarioService usuarioService, PokemonService pokemonService) {
        this.usuarioService = usuarioService;
        this.pokemonService = pokemonService;
    }

    // --- MOCHILA ---
    @GetMapping("/me/pokemons")
    @Transactional
    public ResponseEntity<List<Pokemon>> misPokemons(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).build();
        
        // 1. Buscamos al usuario
        Usuario usuario = usuarioService.buscarPorUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
        // 2. IMPORTANTE: Forzamos a Hibernate a que traiga los datos reales de la tabla intermedia
        // Esto "despierta" la lista de pokémons si estaba en caché
        usuario.getPokemons().size(); 
        
        return ResponseEntity.ok(usuario.getPokemons());
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String password = body.get("password");
            
            usuarioService.registrarUsuario(username, password);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario registrado con éxito");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // --- CAPTURAR ---
    @PostMapping("/batalla/capturar/{id}")
    @Transactional
    public ResponseEntity<Map<String, String>> capturar(@PathVariable Integer id, Principal principal) {
        if (principal == null) return ResponseEntity.status(401).build();

        Map<String, String> response = new HashMap<>();
        Usuario usuario = usuarioService.buscarPorUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pokemon pokemon = pokemonService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pokémon no encontrado"));

        // Evitar duplicados
        if (usuario.getPokemons().contains(pokemon)) {
            response.put("resultado", "YA_LO_TIENES");
            return ResponseEntity.ok(response);
        }

        if (Math.random() < 0.7) {
            usuario.capturarPokemon(pokemon); // Usa el método sincronizado
            usuarioService.guardar(usuario);   
            response.put("resultado", "VICTORIA");
        } else {
            response.put("resultado", "DERROTA");
        }
        return ResponseEntity.ok(response);
    }

    // --- QUITAR POKEMON ---
    @PostMapping("/me/quitar-pokemon/{pokemonId}")
    @Transactional
    public ResponseEntity<Void> quitarMiPokemon(@PathVariable Integer pokemonId, Principal principal) {
        if (principal == null) return ResponseEntity.status(401).build();

        Usuario usuario = usuarioService.buscarPorUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pokemon pokemon = pokemonService.buscarPorId(pokemonId)
                .orElseThrow(() -> new RuntimeException("Pokémon no encontrado"));

        usuario.liberarPokemon(pokemon);
        usuarioService.guardar(usuario);
        return ResponseEntity.ok().build();
    }
}