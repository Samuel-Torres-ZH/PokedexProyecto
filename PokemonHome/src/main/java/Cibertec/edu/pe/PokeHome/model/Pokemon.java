package Cibertec.edu.pe.PokeHome.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "pokemon")
public class Pokemon {

    @Id
    private Integer id; // No es autoincrement porque usas los IDs oficiales (1-151)

    private String nombre;
    private String tipo;
    
    // --- NUEVAS ESTADÍSTICAS (Deben coincidir con tu SQL) ---
    private Integer vida;
    private Integer ataque;
    private Integer defensa;
    private Integer velocidad;
    
    @ManyToMany
    @JoinTable(
        name = "pokemon_ataque",
        joinColumns = @JoinColumn(name = "pokemon_id"),
        inverseJoinColumns = @JoinColumn(name = "ataque_id")
    )
    private List<Ataque> ataques = new ArrayList<>();

    @ManyToMany(mappedBy = "pokemons", fetch = FetchType.LAZY)
    @JsonIgnore // Evita bucles infinitos al convertir a JSON
    private List<Usuario> usuarios = new ArrayList<>();

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    // Getters y Setters de Stats
    public Integer getVida() { return vida; }
    public void setVida(Integer vida) { this.vida = vida; }

    public Integer getAtaque() { return ataque; }
    public void setAtaque(Integer ataque) { this.ataque = ataque; }

    public Integer getDefensa() { return defensa; }
    public void setDefensa(Integer defensa) { this.defensa = defensa; }

    public Integer getVelocidad() { return velocidad; }
    public void setVelocidad(Integer velocidad) { this.velocidad = velocidad; }

    // Getters y Setters de Listas
    public List<Ataque> getAtaques() { return ataques; }
    public void setAtaques(List<Ataque> ataques) { this.ataques = ataques; }

    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }

    // --- ESTO ES LO QUE SOLUCIONA LOS REPETIDOS EN JAVA ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return id != null && id.equals(pokemon.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}