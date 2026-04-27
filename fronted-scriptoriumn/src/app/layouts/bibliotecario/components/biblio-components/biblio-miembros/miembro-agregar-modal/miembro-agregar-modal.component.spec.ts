import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MiembroAgregarModalComponent } from './miembro-agregar-modal.component';

describe('MiembroAgregarModalComponent', () => {
  let component: MiembroAgregarModalComponent;
  let fixture: ComponentFixture<MiembroAgregarModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MiembroAgregarModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MiembroAgregarModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
