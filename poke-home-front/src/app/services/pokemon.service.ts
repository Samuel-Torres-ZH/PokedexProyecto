import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PokemonService {
  private apiUrl = 'http://localhost:8080/api/pokemons';

  constructor(private http: HttpClient) {}

  // Listar todos
  getPokemons(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, { withCredentials: true });
  }

  // Obtener uno solo (Para el formulario de edición)
  getPokemon(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`, { withCredentials: true });
  }

  // Crear nuevo
  createPokemon(pokemon: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, pokemon, { withCredentials: true });
  }

  // Actualizar existente
  updatePokemon(id: any, pokemon: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, pokemon, { withCredentials: true });
  }

  // Eliminar (Usado por la lista y la zona de batalla)
  eliminarPokemon(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { withCredentials: true });
  }

  // Alias para que no falle pokemon-list.ts si usa 'deletePokemon'
  deletePokemon(id: number): Observable<any> {
    return this.eliminarPokemon(id);
  }
}