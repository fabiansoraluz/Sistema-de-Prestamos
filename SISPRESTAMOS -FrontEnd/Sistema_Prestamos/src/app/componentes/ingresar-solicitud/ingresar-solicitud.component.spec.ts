import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IngresarSolicitudComponent } from './ingresar-solicitud.component';

describe('IngresarSolicitudComponent', () => {
  let component: IngresarSolicitudComponent;
  let fixture: ComponentFixture<IngresarSolicitudComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IngresarSolicitudComponent]
    });
    fixture = TestBed.createComponent(IngresarSolicitudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
