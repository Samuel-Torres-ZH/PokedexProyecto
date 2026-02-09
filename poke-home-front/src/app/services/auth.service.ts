import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private authUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
  // IMPORTANTE: withCredentials permite que el navegador guarde la Cookie JSESSIONID
  return this.http.post('http://localhost:8080/api/auth/login', 
    { username, password }, 
    { withCredentials: true }
  );
}

registrar(usuario: any): Observable<any> {
  // Ahora apunta a la ruta pública que definimos
  return this.http.post('http://localhost:8080/api/usuarios/register', usuario);
}
// En auth.service.ts
logout() {
  return this.http.post('http://localhost:8080/api/auth/logout', {}, { withCredentials: true });
}
}