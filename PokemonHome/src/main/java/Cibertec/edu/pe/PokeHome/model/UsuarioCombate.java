package Cibertec.edu.pe.PokeHome.model;

import java.util.List;

public class UsuarioCombate {
	private Integer id;
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<PokemonCombate> getPokemons() {
		return pokemons;
	}
	public void setPokemons(List<PokemonCombate> pokemons) {
		this.pokemons = pokemons;
	}
	private String username;
    private List<PokemonCombate> pokemons;

}
