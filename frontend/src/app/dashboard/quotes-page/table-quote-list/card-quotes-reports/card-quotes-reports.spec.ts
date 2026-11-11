import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardQuotesReports } from './card-quotes-reports';

describe('CardQuotesReports', () => {
  let component: CardQuotesReports;
  let fixture: ComponentFixture<CardQuotesReports>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardQuotesReports]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardQuotesReports);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
