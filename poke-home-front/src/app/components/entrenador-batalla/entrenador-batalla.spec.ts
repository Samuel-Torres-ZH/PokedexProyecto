import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EntrenadorBatallaComponent } from './entrenador-batalla';
import { provideHttpClient } from '@angular/common/http';

describe('EntrenadorBatallaComponent', () => {
  let component: EntrenadorBatallaComponent;
  let fixture: ComponentFixture<EntrenadorBatallaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EntrenadorBatallaComponent],
      providers: [provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EntrenadorBatallaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});