import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { App } from './app/app'; // Esto ahora coincide con la clase en app.ts
import { routes } from './app/app.routes'; // Asegúrate de que tus rutas existan

bootstrapApplication(App, {
  providers: [
    provideHttpClient(withInterceptorsFromDi()),
    provideRouter(routes)
  ]
}).catch(err => console.error(err));