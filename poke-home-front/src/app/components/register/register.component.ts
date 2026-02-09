import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  nuevoUsuario = { 
    username: '', 
    password: '', 
    confirmPassword: ''
  };

  constructor(private authService: AuthService, private router: Router) {}

  onRegister() {
    // 1. Validaciones básicas en el Front
    if (!this.nuevoUsuario.username || !this.nuevoUsuario.password) {
      alert('Por favor, completa todos los campos.');
      return;
    }

    if (this.nuevoUsuario.password !== this.nuevoUsuario.confirmPassword) {
      alert('Las contraseñas no coinciden.');
      return;
    }

    // 2. Preparamos el objeto EXACTO que espera el modelo de Java
    // No enviamos confirmPassword porque Java no lo tiene en su Entidad Usuario
    const usuarioParaJava = {
      username: this.nuevoUsuario.username,
      password: this.nuevoUsuario.password,
      rol: 'USER' // Forzamos el rol desde aquí o lo dejamos que Java lo asigne
    };

    console.log("Enviando registro...", usuarioParaJava);

    this.authService.registrar(usuarioParaJava).subscribe({
      next: (res: any) => {
        alert('¡Registro exitoso, entrenador! Ahora puedes iniciar sesión.');
        this.router.navigate(['/login']);
      },
      error: (err: any) => {
        console.error("Código de error:", err.status);
        // Si el status es 403, es porque SecurityConfig está bloqueando la ruta
        if (err.status === 403) {
          alert("Error 403: El servidor bloqueó el registro. Revisa SecurityConfig.java");
        } else {
          const mensaje = err.error?.message || 'Error al conectar con el servidor';
          alert("Error: " + mensaje);
        }
      }
    });
  }
}