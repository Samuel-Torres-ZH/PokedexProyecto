/*package Cibertec.edu.pe.PokeHome.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Cibertec.edu.pe.PokeHome.service.UsuarioService;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/register")
    public String mostrarRegistro() {
        return "register";
    }

    @PostMapping("/register")
    public String registrarUsuario(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model
    ) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "register";
        }

        try {
            usuarioService.registrarUsuario(username, password);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }

        return "redirect:/login?registered";
    }
}*/

