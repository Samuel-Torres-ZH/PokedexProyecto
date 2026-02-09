import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../../services/usuario.service';
import { Usuario } from '../../models/usuario';

@Component({
  selector: 'app-admin-usuarios',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-usuarios.html',
  styleUrls: ['./admin-usuarios.css']
})
export class AdminUsuariosComponent implements OnInit {
  usuarios: Usuario[] = [];

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
  this.cargarUsuarios(); // Esto debe ejecutarse apenas entras
}

cargarUsuarios() {
  this.usuarioService.listarUsuarios().subscribe(data => {
    this.usuarios = data;
  });
}

  cambiarRol(usuario: Usuario): void {
    if (!usuario.id) return;
    // Ajustado para usar el prefijo ROLE_ que pusiste en la BD
    const nuevoRol = usuario.rol === 'ROLE_ADMIN' ? 'ROLE_USER' : 'ROLE_ADMIN'; 
    
    if (confirm(`¿Cambiar el rol de ${usuario.username} a ${nuevoRol}?`)) {
      this.usuarioService.cambiarRol(usuario.id, nuevoRol).subscribe({
        next: () => this.cargarUsuarios(),
        error: (err: any) => alert('Error al cambiar rol. Verifica sesión de Admin.')
      });
    }
  }

  removerPokemon(usuarioId: number | undefined, pokemonId: number | undefined): void {
    if (usuarioId === undefined || pokemonId === undefined) return;
    if (confirm('¿Quitar este Pokémon al entrenador?')) {
      this.usuarioService.quitarPokemon(usuarioId, pokemonId).subscribe({
        next: () => {
          const usuario = this.usuarios.find(u => u.id === usuarioId);
          if (usuario && usuario.pokemons) {
            usuario.pokemons = usuario.pokemons.filter(p => p.id !== pokemonId);
          }
        },
        error: (err: any) => console.error('Error al remover pokemon', err)
      });
    }
  }

  cambiarPassword(id: number | undefined): void {
    if (id === undefined) return;
    const nuevaPass = prompt('Escriba la nueva contraseña:');
    if (nuevaPass) {
      this.usuarioService.cambiarPassword(id, nuevaPass).subscribe({
        next: () => alert('¡Contraseña cambiada!'),
        error: (err: any) => alert('Error al cambiar contraseña')
      });
    }
  }

  eliminarEntrenador(id: number | undefined): void {
    if (id === undefined) return;
    if (confirm('¿ELIMINAR COMPLETAMENTE a este usuario?')) {
      this.usuarioService.eliminarUsuario(id).subscribe({
        next: () => this.usuarios = this.usuarios.filter(u => u.id !== id),
        error: (err: any) => alert('Error al eliminar usuario')
      });
    }
  }
}