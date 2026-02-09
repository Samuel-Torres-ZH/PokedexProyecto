package Cibertec.edu.pe.PokeHome.model;

import java.util.List;

public class PokemonCombate {
	private Integer id;
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getVida() {
		return vida;
	}
	public void setVida(int vida) {
		this.vida = vida;
	}
	public int getAtaque() {
		return ataque;
	}
	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}
	public int getDefensa() {
		return defensa;
	}
	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}
	public int getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	public List<Ataque> getAtaques() {
		return ataques;
	}
	public void setAtaques(List<Ataque> ataques) {
		this.ataques = ataques;
	}
	private String nombre;
    private int vida;
    private int ataque;
    private int defensa;
    private int velocidad;
    private List<Ataque> ataques;

}
