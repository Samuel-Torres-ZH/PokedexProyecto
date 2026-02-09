package Cibertec.edu.pe.PokeHome.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import Cibertec.edu.pe.PokeHome.model.Usuario;
import Cibertec.edu.pe.PokeHome.repository.UsuarioRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdmin(UsuarioRepository repo,
                                PasswordEncoder encoder) {
        return args -> {

            if (repo.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRol("ROLE_ADMIN");
                repo.save(admin);
            }
        };
    }
}
