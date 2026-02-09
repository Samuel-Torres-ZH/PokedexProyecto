package Cibertec.edu.pe.PokeHome.service;

import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

import Cibertec.edu.pe.PokeHome.model.Pokemon;
import Cibertec.edu.pe.PokeHome.model.PokemonCombate;
import Cibertec.edu.pe.PokeHome.model.Usuario;


@Service
public class CombateService {

    private final Random random = new Random();
    private final PokemonService pokemonService;

    public CombateService(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    /**
     * Batalla entre el Pokémon del jugador y un enemigo
     */
    public String combatir(Usuario usuario, Pokemon jugador, Pokemon enemigo) {
        if (jugador == null) return "¡No tienes un Pokémon para combatir!";
        if (enemigo == null) return "¡No hay enemigo para combatir!";

        PokemonCombate pJugador = crearPokemonCombate(jugador);
        PokemonCombate pEnemigo = crearPokemonCombate(enemigo);

        StringBuilder log = new StringBuilder();
        log.append("¡Comienza la batalla!\n");

        // Turnos según velocidad
        while (pJugador.getVida() > 0 && pEnemigo.getVida() > 0) {
            if (pJugador.getVelocidad() >= pEnemigo.getVelocidad()) {
                log.append(realizarAtaque(pJugador, pEnemigo));
                if (pEnemigo.getVida() <= 0) break;
                log.append(realizarAtaque(pEnemigo, pJugador));
            } else {
                log.append(realizarAtaque(pEnemigo, pJugador));
                if (pJugador.getVida() <= 0) break;
                log.append(realizarAtaque(pJugador, pEnemigo));
            }
        }

        log.append(pJugador.getVida() > 0 ? "¡Victoria!" : "¡Derrota!");
        return log.toString();
    }

    private PokemonCombate crearPokemonCombate(Pokemon p) {
        PokemonCombate pc = new PokemonCombate();
        pc.setNombre(p.getNombre());
        pc.setVida(100);
        pc.setAtaque(10 + random.nextInt(15));
        pc.setDefensa(5 + random.nextInt(10));
        pc.setVelocidad(5 + random.nextInt(15));
        return pc;
    }

    private String realizarAtaque(PokemonCombate atacante, PokemonCombate defensor) {
        int dano = Math.max(atacante.getAtaque() - defensor.getDefensa() + random.nextInt(6), 0);

        // Golpe crítico 10%
        boolean critico = random.nextInt(100) < 10;
        if (critico) dano = (int)(dano * 1.5);

        defensor.setVida(Math.max(defensor.getVida() - dano, 0));

        return atacante.getNombre() + " ataca y causa " + dano + " de daño"
                + (critico ? " (¡Crítico!)" : "")
                + ". " + defensor.getNombre() + " vida: " + defensor.getVida() + "\n";
    }

    /**
     * Devuelve un enemigo aleatorio de la lista de Pokémon
     */
    public Pokemon generarEnemigo() {
        List<Pokemon> todos = pokemonService.listarTodos();
        int index = random.nextInt(todos.size());
        return todos.get(index);
    }
}
