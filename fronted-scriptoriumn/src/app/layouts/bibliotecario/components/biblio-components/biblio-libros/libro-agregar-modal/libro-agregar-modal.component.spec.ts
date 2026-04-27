import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LibroAgregarModalComponent } from './libro-agregar-modal.component';

describe('LibroAgregarModalComponent', () => {
  let component: LibroAgregarModalComponent;
  let fixture: ComponentFixture<LibroAgregarModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LibroAgregarModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LibroAgregarModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
