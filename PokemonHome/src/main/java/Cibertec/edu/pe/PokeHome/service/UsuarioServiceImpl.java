package Cibertec.edu.pe.PokeHome.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import Cibertec.edu.pe.PokeHome.model.Usuario;
import Cibertec.edu.pe.PokeHome.repository.PokemonRepository;
import Cibertec.edu.pe.PokeHome.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final PokemonRepository pokemonRepo;

    public UsuarioServiceImpl(UsuarioRepository repo, PasswordEncoder passwordEncoder,PokemonRepository pokemonRepo) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.pokemonRepo = pokemonRepo;
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return repo.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        // Solo encriptar si es una contraseña nueva
        if (usuario.getPassword() != null && !usuario.getPassword().startsWith("$2a$")) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return repo.save(usuario);
    }
    @Override
    public void registrarUsuario(String username, String password) {
        // 1. Validar si existe
        if (repo.findByUsername(username).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        // 2. Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRol("ROLE_USER");

        // 3. LOGICA DEL POKÉMON INICIAL (Pikachu ID 25)
        // Buscamos en la tabla 'pokemon' y lo añadimos a la lista del usuario
        pokemonRepo.findById(25).ifPresent(starter -> {
            usuario.getPokemons().add(starter);
        });

        // 4. Guardar en la base de datos
        repo.save(usuario);
    }
    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        return repo.findById(id);
    }
    @Override
    public void eliminar(Usuario usuario) {
        
        if (usuario.getPokemons() != null) {
            usuario.getPokemons().clear();
        }

        
        repo.delete(usuario);
    }

}
