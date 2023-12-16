import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListadoCuotas } from './listado-cuota.component';

describe('VerEstadoCuotaComponent', () => {
  let component: ListadoCuotas;
  let fixture: ComponentFixture<ListadoCuotas>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListadoCuotas]
    });
    fixture = TestBed.createComponent(ListadoCuotas);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
