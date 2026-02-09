export interface Usuario {
  id?: number;
  username: string;
  rol: string;
  pokemons: any[]; // <--- Debe llamarse igual que en tu Entidad Java
}