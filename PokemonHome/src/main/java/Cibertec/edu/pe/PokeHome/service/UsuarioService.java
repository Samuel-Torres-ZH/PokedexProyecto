package Cibertec.edu.pe.PokeHome.service;

import java.util.List;
import java.util.Optional;
import Cibertec.edu.pe.PokeHome.model.Usuario;

public interface UsuarioService {

    Optional<Usuario> buscarPorUsername(String username);
    List<Usuario> listarUsuarios();
    Usuario guardar(Usuario usuario);
    void registrarUsuario(String username, String password);
    Optional<Usuario> buscarPorId(Integer id);
    void eliminar(Usuario usuario);
    
}
