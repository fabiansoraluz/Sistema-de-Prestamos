import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerSolicitudClienteComponent } from './ver-solicitud-cliente.component';

describe('VerSolicitudClienteComponent', () => {
  let component: VerSolicitudClienteComponent;
  let fixture: ComponentFixture<VerSolicitudClienteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerSolicitudClienteComponent]
    });
    fixture = TestBed.createComponent(VerSolicitudClienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
