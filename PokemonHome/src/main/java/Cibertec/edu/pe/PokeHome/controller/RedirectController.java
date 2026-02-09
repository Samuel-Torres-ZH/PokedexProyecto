/*package Cibertec.edu.pe.PokeHome.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RedirectController {

    @GetMapping("/redirect")
    public String redirect(Authentication auth) {

        if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/usuarios";
        }

        return "redirect:/usuario/pokemons";
    }
}*/
