package Cibertec.edu.pe.PokeHome.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;



import Cibertec.edu.pe.PokeHome.model.Usuario;
import Cibertec.edu.pe.PokeHome.service.PokemonService;
import Cibertec.edu.pe.PokeHome.service.UsuarioService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AdminRestController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final PokemonService pokemonService;
    

    public AdminRestController(UsuarioService usuarioService, PasswordEncoder passwordEncoder, PokemonService pokemonService) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.pokemonService = pokemonService;
    }

    // Cambiado a ResponseEntity para mejor manejo en Angular
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/usuarios/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@RequestBody Map<String, Object> payload) {
        try {
            Integer id = (Integer) payload.get("id");
            String nuevaPassword = (String) payload.get("nuevaPassword");
            
            Usuario usuario = usuarioService.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            usuario.setPassword(passwordEncoder.encode(nuevaPassword));
            usuarioService.guardar(usuario);
            
            return ResponseEntity.ok(Map.of("message", "Contraseña actualizada con éxito"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Error al cambiar contraseña"));
        }
    }

    @PostMapping("/usuarios/cambiar-rol")
    public ResponseEntity<?> cambiarRol(@RequestBody Map<String, Object> payload) {
        try {
            Integer id = (Integer) payload.get("id");
            String nuevoRol = (String) payload.get("nuevoRol");

            Usuario usuario = usuarioService.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            usuario.setRol(nuevoRol);
            usuarioService.guardar(usuario);
            
            return ResponseEntity.ok(Map.of("message", "Rol actualizado a " + nuevoRol));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Error al cambiar el rol"));
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id, Principal principal) {
        Usuario usuarioAEliminar = usuarioService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Seguridad: El admin no puede borrarse a sí mismo desde el panel
        if (principal.getName().equals(usuarioAEliminar.getUsername())) {
            return ResponseEntity.badRequest().body(Map.of("message", "No puedes eliminar tu propia cuenta de administrador"));
        }

        usuarioService.eliminar(usuarioAEliminar);
        return ResponseEntity.ok(Map.of("message", "Usuario eliminado con éxito"));
    }
    
    @PostMapping("/usuarios/{usuarioId}/quitar-pokemon/{pokemonId}")
    @Transactional
    public ResponseEntity<?> quitarPokemonDeUsuario(@PathVariable Integer usuarioId, @PathVariable Integer pokemonId) {
        try {
            Usuario usuario = usuarioService.buscarPorId(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            Cibertec.edu.pe.PokeHome.model.Pokemon pokemon = pokemonService.buscarPorId(pokemonId)
                    .orElseThrow(() -> new RuntimeException("Pokémon no encontrado"));

            // Quitamos la relación
            usuario.getPokemons().remove(pokemon);
            usuarioService.guardar(usuario);

            return ResponseEntity.ok(Map.of("message", "Pokémon removido con éxito"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Error: " + e.getMessage()));
        }
    }
}