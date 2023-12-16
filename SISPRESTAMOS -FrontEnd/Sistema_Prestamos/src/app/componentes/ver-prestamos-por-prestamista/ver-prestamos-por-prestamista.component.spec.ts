import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerPrestamosPorPrestamistaComponent } from './ver-prestamos-por-prestamista.component';

describe('VerPrestamosPorPrestamistaComponent', () => {
  let component: VerPrestamosPorPrestamistaComponent;
  let fixture: ComponentFixture<VerPrestamosPorPrestamistaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerPrestamosPorPrestamistaComponent]
    });
    fixture = TestBed.createComponent(VerPrestamosPorPrestamistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
