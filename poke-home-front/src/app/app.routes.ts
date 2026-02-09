import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login';
import { AdminUsuariosComponent } from './components/admin-usuarios/admin-usuarios';
import { PokemonFormComponent } from './components/pokemon-form/pokemon-form';
import { EntrenadorBatallaComponent } from './components/entrenador-batalla/entrenador-batalla';
import { PokemonListComponent } from './components/pokemon-list/pokemon-list';
import { MisPokemonsComponent } from './components/mis-pokemons/mis-pokemons';
import { RegisterComponent } from './components/register/register.component';

export const routes: Routes = [
    // Al inicio mandamos al login
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },

    // Rutas exclusivas de ADMIN
    { path: 'admin/usuarios', component: AdminUsuariosComponent }, 
    { path: 'admin/pokemons/form', component: PokemonFormComponent },
    { path: 'admin/pokemons/form/:id', component: PokemonFormComponent },
    
    // Ruta de la lista general (puedes dejarla para el admin si deseas)
    { path: 'pokemons', component: PokemonListComponent },

    // Rutas exclusivas de ENTRENADOR (Donde elige y batalla)
    { path: 'entrenador/batalla', component: EntrenadorBatallaComponent },
    
    //Rutas para mis pokemones
    { path: 'mis-pokemons', component: MisPokemonsComponent },

    // Comodín para rutas inexistentes
    { path: '**', redirectTo: '/login' }

    
];