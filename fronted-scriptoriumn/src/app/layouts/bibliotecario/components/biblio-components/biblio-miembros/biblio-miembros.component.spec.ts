import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BiblioMiembrosComponent } from './biblio-miembros.component';

describe('BiblioMiembrosComponent', () => {
  let component: BiblioMiembrosComponent;
  let fixture: ComponentFixture<BiblioMiembrosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BiblioMiembrosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BiblioMiembrosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
