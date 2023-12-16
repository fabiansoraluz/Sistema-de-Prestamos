import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrestamistaLayoutComponent } from './prestamista-layout.component';

describe('PrestamistaLayoutComponent', () => {
  let component: PrestamistaLayoutComponent;
  let fixture: ComponentFixture<PrestamistaLayoutComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrestamistaLayoutComponent]
    });
    fixture = TestBed.createComponent(PrestamistaLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
