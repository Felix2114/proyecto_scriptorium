/// <reference types="jasmine" />

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistroMainComponent } from './registro-main.component';

describe('RegistroMainComponent', () => {
  let component: RegistroMainComponent;
  let fixture: ComponentFixture<RegistroMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegistroMainComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistroMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
