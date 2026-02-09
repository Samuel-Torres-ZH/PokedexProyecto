import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';
import { Pokemon } from '../../models/pokemon';

@Component({
  selector: 'app-mis-pokemons',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './mis-pokemons.html',
  styleUrls: ['./mis-pokemons.css'] // Asegúrate de tener el CSS o dejarlo vacío
})
export class MisPokemonsComponent implements OnInit {
  misPokemons: Pokemon[] = [];
  cargando: boolean = true;

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.cargarMochila();
  }

  cargarMochila(): void {
    this.cargando = true;
    this.usuarioService.getMisPokemons().subscribe({
  next: (data) => {
    this.misPokemons = data;
    this.cargando = false; // Detener spinner
  },
  error: (err) => {
    console.error(err);
    this.cargando = false; // Detener spinner aunque falle
  }
});
  }

  liberarPokemon(id: any): void {
    if (!id) return;
    if (confirm('¿Estás seguro de que quieres liberar a este Pokémon? Volverá a la zona salvaje.')) {
      this.usuarioService.liberarMiPokemon(Number(id)).subscribe({
        next: () => {
          this.misPokemons = this.misPokemons.filter(p => p.id !== id);
          alert('¡Pokémon liberado con éxito! 🍃');
        },
        error: (err: any) => alert('No se pudo liberar al Pokémon.')
      });
    }
  }
}