import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlatformDashboard } from './platform-dashboard';

describe('PlatformDashboard', () => {
  let component: PlatformDashboard;
  let fixture: ComponentFixture<PlatformDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlatformDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlatformDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
