import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Usuario } from '../models/usuario';
import { Pokemon } from '../models/pokemon';

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private userUrl = 'http://localhost:8080/api/usuarios';
  private adminUrl = 'http://localhost:8080/api/admin';
  private authUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  // Centralizamos el guardado del usuario aquí
  login(credentials: any): Observable<any> {
    return this.http.post<any>(`${this.authUrl}/login`, credentials, { withCredentials: true }).pipe(
      tap(user => {
        if (user) {
          localStorage.setItem('usuario_logeado', JSON.stringify(user));
        }
      })
    );
  }

  // Este es el método que usan tus componentes de Batalla y Lista
  obtenerRol(): string {
    const userJson = localStorage.getItem('usuario_logeado');
    if (!userJson) return '';
    try {
      const user = JSON.parse(userJson);
      return user.rol || '';
    } catch (e) {
      return '';
    }
  }

  // Alias por si algún componente usa el nombre viejo
  getRolActual(): string {
    return this.obtenerRol();
  }

  logout(): void {
    localStorage.clear();
    this.http.post(`${this.authUrl}/logout`, {}, { withCredentials: true }).subscribe({
      next: () => console.log('Sesión cerrada en servidor'),
      error: () => console.log('Error al cerrar sesión o ya estaba cerrada')
    });
  }

  listarUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.adminUrl}/usuarios`, { withCredentials: true });
  }

  eliminarUsuario(id: number): Observable<any> {
    return this.http.delete(`${this.adminUrl}/usuarios/${id}`, { withCredentials: true });
  }

  cambiarRol(usuarioId: number, nuevoRol: string): Observable<any> {
    return this.http.post(`${this.adminUrl}/usuarios/${usuarioId}/rol`, { rol: nuevoRol }, { withCredentials: true });
  }

  quitarPokemon(usuarioId: number, pokemonId: number): Observable<any> {
    return this.http.post(`${this.adminUrl}/usuarios/${usuarioId}/quitar-pokemon/${pokemonId}`, {}, { withCredentials: true });
  }

  cambiarPassword(id: number, nuevaPassword: string): Observable<any> {
    return this.http.post(`${this.adminUrl}/usuarios/cambiar-password`, { id, nuevaPassword }, { withCredentials: true });
  }

  getMisPokemons(): Observable<Pokemon[]> {
    return this.http.get<Pokemon[]>(`${this.userUrl}/me/pokemons`, { withCredentials: true });
  }

  intentarCaptura(pokemonId: number): Observable<any> {
    return this.http.post(`${this.userUrl}/batalla/capturar/${pokemonId}`, {}, { withCredentials: true });
  }

  liberarMiPokemon(pokemonId: number): Observable<void> {
    return this.http.post<void>(`${this.userUrl}/me/quitar-pokemon/${pokemonId}`, {}, { withCredentials: true });
  }
}