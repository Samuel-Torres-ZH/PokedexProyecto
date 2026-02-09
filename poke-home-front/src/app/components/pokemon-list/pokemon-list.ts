import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterModule, Router } from '@angular/router';
import { PokemonService } from '../../services/pokemon.service';
import { Pokemon } from '../../models/pokemon';

@Component({
  selector: 'app-pokemon-list',
  standalone: true,
  imports: [CommonModule, RouterModule, RouterLink],
  templateUrl: './pokemon-list.html',
  styleUrls: ['./pokemon-list.css']
})
export class PokemonListComponent implements OnInit {
  pokemons: Pokemon[] = [];
  cargando: boolean = true;

  constructor(
    private pokemonService: PokemonService, 
    private cdr: ChangeDetectorRef,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.cargarPokemons();
  }

  obtenerRol(): string {
    const user = localStorage.getItem('usuario_logeado');
    if (!user) return '';
    return JSON.parse(user).rol;
  }

  cargarPokemons(): void {
    this.cargando = true;
    this.pokemonService.getPokemons().subscribe({
      next: (data: Pokemon[]) => {
        this.pokemons = data;
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        console.error("Error cargando pokemons:", err);
        this.cargando = false;
      }
    });
  }

  logout(): void {
    if (confirm('¿Estás seguro de que deseas cerrar sesión?')) {
      localStorage.clear();
      this.router.navigate(['/login']);
    }
  }

  eliminar(id: number | undefined): void {
    if (id === undefined) return;
    if (confirm('¿Deseas eliminar este Pokémon?')) {
      this.pokemonService.deletePokemon(id).subscribe({
        next: () => this.cargarPokemons(),
        error: (err: any) => console.error(err)
      });
    }
  }
}