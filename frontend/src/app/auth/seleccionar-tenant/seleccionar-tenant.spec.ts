import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeleccionarTenant } from './seleccionar-tenant';

describe('SeleccionarTenant', () => {
  let component: SeleccionarTenant;
  let fixture: ComponentFixture<SeleccionarTenant>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SeleccionarTenant]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SeleccionarTenant);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
