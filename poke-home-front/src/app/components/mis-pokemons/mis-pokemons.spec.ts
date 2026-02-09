import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MisPokemonsComponent } from './mis-pokemons';

describe('MisPokemons', () => {
  let component: MisPokemonsComponent;
  let fixture: ComponentFixture<MisPokemonsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MisPokemonsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MisPokemonsComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
