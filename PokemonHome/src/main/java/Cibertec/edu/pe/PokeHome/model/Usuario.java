package Cibertec.edu.pe.PokeHome.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 50)
    private String rol;

    @ManyToMany(fetch = FetchType.LAZY) // Cambia a LAZY para evitar que traiga todo siempre
    @JoinTable(
        name = "usuario_pokemon",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "pokemon_id")
    )
    private List<Pokemon> pokemons = new ArrayList<>();
    

   

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
    public void capturarPokemon(Pokemon p) {
        if (!this.pokemons.contains(p)) {
            this.pokemons.add(p);
            p.getUsuarios().add(this); // Sincroniza ambos lados
        }
    }

    public void liberarPokemon(Pokemon p) {
        this.pokemons.remove(p);
        p.getUsuarios().remove(this);
    }
    
}
