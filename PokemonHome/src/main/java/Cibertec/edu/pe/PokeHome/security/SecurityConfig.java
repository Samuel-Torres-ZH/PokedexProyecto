package Cibertec.edu.pe.PokeHome.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
	        .csrf(csrf -> csrf.disable())
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
	        .authorizeHttpRequests(auth -> auth
	            // 1. PUBLICOS (Cualquiera puede entrar)
	            .requestMatchers("/api/auth/**").permitAll() 
	            .requestMatchers(HttpMethod.POST, "/api/usuarios/register").permitAll() // <--- ESTA LÍNEA ES LA CLAVE
	            
	            // 2. SOLO ADMIN
	            .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
	            
	            // 3. POKEMONS (GET publico o usuarios, POST solo admin)
	            .requestMatchers(HttpMethod.GET, "/api/pokemons/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
	            .requestMatchers("/api/pokemons/**").hasAuthority("ROLE_ADMIN")
	            
	            // 4. RESTO DE USUARIOS (Requiere estar logueado)
	            // Aquí entran /me/pokemons, /batalla/capturar, etc.
	            .requestMatchers("/api/usuarios/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
	            
	            .anyRequest().authenticated()
	        )
	        .logout(logout -> logout
	            .logoutUrl("/api/auth/logout")
	            .deleteCookies("JSESSIONID")
	            .logoutSuccessHandler((req, res, auth) -> res.setStatus(200))
	        );
	    return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowedOrigins(List.of("http://localhost:4200"));
	    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
	    config.setAllowCredentials(true); // ESTO ES OBLIGATORIO PARA EL INTERCEPTOR
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", config);
	    return source;
	}

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}