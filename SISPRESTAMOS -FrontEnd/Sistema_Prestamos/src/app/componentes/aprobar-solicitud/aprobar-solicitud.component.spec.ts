import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AprobarSolicitudComponent } from './aprobar-solicitud.component';

describe('AprobarSolicitudComponent', () => {
  let component: AprobarSolicitudComponent;
  let fixture: ComponentFixture<AprobarSolicitudComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AprobarSolicitudComponent]
    });
    fixture = TestBed.createComponent(AprobarSolicitudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
