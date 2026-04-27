import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrestamoDetalleModalComponent } from './prestamo-modal.component';

describe('PrestamoModalComponent', () => {
  let component: PrestamoDetalleModalComponent;
  let fixture: ComponentFixture<PrestamoDetalleModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PrestamoDetalleModalComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PrestamoDetalleModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
