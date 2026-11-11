import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KanbanCardReports } from './kanban-card-reports';

describe('KanbanCardReports', () => {
  let component: KanbanCardReports;
  let fixture: ComponentFixture<KanbanCardReports>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KanbanCardReports]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KanbanCardReports);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
