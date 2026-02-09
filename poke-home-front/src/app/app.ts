import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UsuarioService } from './services/usuario.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './app.html'
})
export class App {
  constructor(private router: Router) {}

  obtenerRol(): string {
    const user = localStorage.getItem('usuario_logeado');
    return user ? JSON.parse(user).rol : '';
  }

  logout(): void {
    localStorage.removeItem('usuario_logeado');
    this.router.navigate(['/login']);
  }
}