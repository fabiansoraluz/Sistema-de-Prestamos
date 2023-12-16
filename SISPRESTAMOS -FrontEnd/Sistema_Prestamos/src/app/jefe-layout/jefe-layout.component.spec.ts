import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JefeLayoutComponent } from './jefe-layout.component';

describe('JefeLayoutComponent', () => {
  let component: JefeLayoutComponent;
  let fixture: ComponentFixture<JefeLayoutComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JefeLayoutComponent]
    });
    fixture = TestBed.createComponent(JefeLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
