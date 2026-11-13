import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltersHeader } from './filters-header';

describe('FiltersHeader', () => {
  let component: FiltersHeader;
  let fixture: ComponentFixture<FiltersHeader>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FiltersHeader]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FiltersHeader);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
