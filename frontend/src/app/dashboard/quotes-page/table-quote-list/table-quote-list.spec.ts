import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableQuoteList } from './table-quote-list';

describe('TableQuoteList', () => {
  let component: TableQuoteList;
  let fixture: ComponentFixture<TableQuoteList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TableQuoteList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TableQuoteList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
