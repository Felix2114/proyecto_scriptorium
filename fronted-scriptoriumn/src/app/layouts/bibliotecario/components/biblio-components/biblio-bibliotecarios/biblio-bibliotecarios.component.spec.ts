import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BiblioBibliotecariosComponent } from './biblio-bibliotecarios.component';

describe('BiblioBibliotecariosComponent', () => {
  let component: BiblioBibliotecariosComponent;
  let fixture: ComponentFixture<BiblioBibliotecariosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BiblioBibliotecariosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BiblioBibliotecariosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
