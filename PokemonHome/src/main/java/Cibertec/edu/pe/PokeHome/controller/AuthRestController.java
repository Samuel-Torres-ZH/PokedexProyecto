package Cibertec.edu.pe.PokeHome.controller;


import Cibertec.edu.pe.PokeHome.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;

    public AuthRestController(AuthenticationManager authenticationManager, UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(body.get("username"), body.get("password"));
            Authentication auth = authenticationManager.authenticate(authReq);
            
            // ESTO ES LO QUE CREA LA SESIÓN FÍSICA
            SecurityContextHolder.getContext().setAuthentication(auth);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return ResponseEntity.ok(Map.of("rol", auth.getAuthorities().iterator().next().getAuthority()));
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Map.of("message", "Sesión cerrada"));
    }
}