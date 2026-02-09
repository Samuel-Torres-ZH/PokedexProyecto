import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {
  username = '';
  password = '';
  showPassword = false;
  error = false;

  constructor(private usuarioService: UsuarioService, private router: Router) {}

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  onLogin(): void {
    const payload = { username: this.username, password: this.password };

    this.usuarioService.login(payload).subscribe({
      next: (user: any) => {
        console.log('Login exitoso:', user);
        this.error = false;
        
        // Guardamos el usuario con su rol en el Storage
        localStorage.setItem('usuario_logeado', JSON.stringify(user));
        
        // REDIRECCIÓN SEGÚN ROL
        if (user.rol === 'ROLE_ADMIN') {
          this.router.navigate(['/admin/usuarios']);
        } else {
          // El usuario va a CAZAR, no a la lista de admin
          this.router.navigate(['/entrenador/batalla']);
        }
      },
      error: (err: any) => {
        console.error('Error en login:', err);
        this.error = true;
      }
    });
  }
}