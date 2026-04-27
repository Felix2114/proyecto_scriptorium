import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BiblioPrestamosComponent } from './biblio-prestamos.component';

describe('BiblioPrestamosComponent', () => {
  let component: BiblioPrestamosComponent;
  let fixture: ComponentFixture<BiblioPrestamosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BiblioPrestamosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BiblioPrestamosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
