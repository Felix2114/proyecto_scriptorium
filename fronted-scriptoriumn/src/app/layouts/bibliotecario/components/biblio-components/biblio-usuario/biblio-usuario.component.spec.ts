import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BiblioUsuarioComponent } from './biblio-usuario.component';

describe('BiblioUsuarioComponent', () => {
  let component: BiblioUsuarioComponent;
  let fixture: ComponentFixture<BiblioUsuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BiblioUsuarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BiblioUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});


