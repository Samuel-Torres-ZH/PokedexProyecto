package Cibertec.edu.pe.PokeHome.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // <--- ESTO LE DICE A SPRING QUE ES UNA TABLA
@Table(name = "ataque") // <--- SE CONECTA CON TU TABLA 'ataque' DE MYSQL
public class Ataque {

    @Id // <--- DEFINE LA LLAVE PRIMARIA
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <--- AUTO_INCREMENT
    private Integer id;
    
    private String nombre;
    private int poder;
    private String tipo;

    // Constructor vacío (Obligatorio para que Spring no falle)
    public Ataque() {
    }

    // --- TUS GETTERS Y SETTERS ---
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

    public int getPoder() {
        return poder;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}