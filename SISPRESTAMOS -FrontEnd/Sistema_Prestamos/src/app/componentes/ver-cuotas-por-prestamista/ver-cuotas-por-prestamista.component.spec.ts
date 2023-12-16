import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerCuotasPorPrestamistaComponent } from './ver-cuotas-por-prestamista.component';

describe('VerCuotasPorPrestamistaComponent', () => {
  let component: VerCuotasPorPrestamistaComponent;
  let fixture: ComponentFixture<VerCuotasPorPrestamistaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerCuotasPorPrestamistaComponent]
    });
    fixture = TestBed.createComponent(VerCuotasPorPrestamistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
