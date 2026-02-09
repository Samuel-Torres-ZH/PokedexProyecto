import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';
import { PokemonService } from '../../services/pokemon.service';
import { Pokemon } from '../../models/pokemon';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-entrenador-batalla',
  standalone: true,
  imports: [CommonModule, RouterLink], 
  templateUrl: './entrenador-batalla.html',
  styleUrls: ['./entrenador-batalla.css']
})
export class EntrenadorBatallaComponent implements OnInit {
  pokemonsSalvajes: Pokemon[] = [];
  mensajeResultado: string = '';
  claseResultado: string = '';

  constructor(
    private pokemonService: PokemonService,
    private usuarioService: UsuarioService,
    private router: Router,
    private cdr: ChangeDetectorRef 
  ) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  obtenerRol(): string {
    const user = localStorage.getItem('usuario_logeado');
    return user ? JSON.parse(user).rol : '';
  }

  cargarDatos(): void {
    const rol = this.obtenerRol();

    if (rol === 'ROLE_ADMIN') {
      this.pokemonService.getPokemons().subscribe({
        next: (todos) => {
          this.pokemonsSalvajes = todos;
          this.cdr.detectChanges();
        },
        error: (err: any) => console.error("Error al cargar como admin:", err)
      });
    } else {
      // ESTE BLOQUE ES EL CORRECTO: Hace las dos peticiones a la vez
      forkJoin({
        todos: this.pokemonService.getPokemons().pipe(catchError(() => of([]))),
        mios: this.usuarioService.getMisPokemons().pipe(catchError(() => of([])))
      }).subscribe({
        next: ({ todos, mios }) => {
          const idsMios = mios.map((p: any) => p.id);
          // INDEPENDENCIA: Aquí filtramos para que NO aparezcan los que ya tienes
          this.pokemonsSalvajes = todos.filter((p: any) => !idsMios.includes(p.id));
          this.cdr.detectChanges();
        },
        error: (err) => console.error("Error en forkJoin:", err)
      });
    }
  }

  borrarPokemon(id: any): void {
    if (!id) return;
    if (confirm('¿Deseas eliminar este Pokémon definitivamente?')) {
      this.pokemonService.eliminarPokemon(Number(id)).subscribe({
        next: () => {
          this.mensajeResultado = "Pokémon borrado con éxito";
          this.claseResultado = "alert-dark";
          this.cargarDatos();
        },
        error: (err: any) => alert("No se pudo borrar. Revisa permisos.")
      });
    }
  }

  intentarCapturar(p: Pokemon): void {
    this.mensajeResultado = `¡Luchando contra ${p.nombre}...!`;
    this.claseResultado = 'alert-info';
    this.usuarioService.intentarCaptura(Number(p.id)).subscribe({
      next: (res: any) => {
        if (res.resultado === 'VICTORIA') {
          this.mensajeResultado = `¡Capturaste a ${p.nombre}! 🎊`;
          this.claseResultado = 'alert-success';
          this.pokemonsSalvajes = this.pokemonsSalvajes.filter(poke => poke.id !== p.id);
        } else {
          this.mensajeResultado = `${p.nombre} escapó... 💨`;
          this.claseResultado = 'alert-danger';
        }
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        this.mensajeResultado = 'Error en batalla (403). Re-loguea.';
        this.claseResultado = 'alert-warning';
      }
    });
  }

  logout(): void {
    if (confirm('¿Cerrar sesión?')) {
      localStorage.clear();
      this.router.navigate(['/login']);
    }
  }
}