import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KanbanQuoteList } from './kanban-quote-list';

describe('KanbanQuoteList', () => {
  let component: KanbanQuoteList;
  let fixture: ComponentFixture<KanbanQuoteList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KanbanQuoteList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KanbanQuoteList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
