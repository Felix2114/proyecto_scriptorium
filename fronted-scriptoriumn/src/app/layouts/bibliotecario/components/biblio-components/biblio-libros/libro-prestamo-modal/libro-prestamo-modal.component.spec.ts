import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LibroPrestamoModalComponent } from './libro-prestamo-modal.component';

describe('LibroPrestamoModalComponent', () => {
  let component: LibroPrestamoModalComponent;
  let fixture: ComponentFixture<LibroPrestamoModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LibroPrestamoModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LibroPrestamoModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
