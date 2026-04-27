import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BiblioLibrosComponent } from './biblio-libros.component';

describe('BiblioLibrosComponent', () => {
  let component: BiblioLibrosComponent;
  let fixture: ComponentFixture<BiblioLibrosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BiblioLibrosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BiblioLibrosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
