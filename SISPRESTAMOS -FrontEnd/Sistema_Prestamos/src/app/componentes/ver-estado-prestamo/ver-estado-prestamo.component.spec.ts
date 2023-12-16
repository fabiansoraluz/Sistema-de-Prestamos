import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerEstadoPrestamoComponent } from './ver-estado-prestamo.component';

describe('VerEstadoPrestamoComponent', () => {
  let component: VerEstadoPrestamoComponent;
  let fixture: ComponentFixture<VerEstadoPrestamoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerEstadoPrestamoComponent]
    });
    fixture = TestBed.createComponent(VerEstadoPrestamoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
