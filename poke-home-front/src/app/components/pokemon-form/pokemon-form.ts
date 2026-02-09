import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { PokemonService } from '../../services/pokemon.service'; // Sin el .service
import { Pokemon } from '../../models/pokemon';

@Component({
  selector: 'app-pokemon-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './pokemon-form.html', // Nombre real de tu archivo
  styleUrls: ['./pokemon-form.css']    // Nombre real de tu archivo
})
export class PokemonFormComponent implements OnInit {

  pokemon: Pokemon = { nombre: '', tipo: '' }; 
  titulo: string = 'Crear Pokémon';
  esEdicion: boolean = false;

  constructor(
    private pokemonService: PokemonService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      const id = params['id'];
      if(id){
        this.esEdicion = true;
        this.titulo = 'Editar Pokémon';
        this.pokemonService.getPokemon(+id).subscribe({
          next: (p: Pokemon) => this.pokemon = p,
          error: (err: any) => console.error(err)
        });
      }
    });
  }

  guardar(): void {
    if (this.esEdicion && this.pokemon.id) {
        this.pokemonService.updatePokemon(this.pokemon.id, this.pokemon).subscribe({
            next: (p: Pokemon) => this.router.navigate(['/pokemons']),
            error: (err: any) => console.error(err)
        });
    } else {
        this.pokemonService.createPokemon(this.pokemon).subscribe({
            next: (p: Pokemon) => this.router.navigate(['/pokemons']),
            error: (err: any) => console.error(err)
        });
    }
  }
}