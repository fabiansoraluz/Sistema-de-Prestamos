import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PagoCuotaComponent } from './pago-cuota.component';

describe('PagoCuotaComponent', () => {
  let component: PagoCuotaComponent;
  let fixture: ComponentFixture<PagoCuotaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PagoCuotaComponent]
    });
    fixture = TestBed.createComponent(PagoCuotaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
