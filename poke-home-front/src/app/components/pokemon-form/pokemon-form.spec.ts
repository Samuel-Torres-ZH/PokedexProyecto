import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PokemonFormComponent } from './pokemon-form'; // Importa el nombre correcto
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';

describe('PokemonFormComponent', () => {
  let component: PokemonFormComponent;
  let fixture: ComponentFixture<PokemonFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PokemonFormComponent], // Nombre de la clase
      providers: [provideHttpClient(), provideRouter([])]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PokemonFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});