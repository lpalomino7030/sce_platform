import { Component } from '@angular/core';
import {CardQuotesReports} from './card-quotes-reports/card-quotes-reports';
import {TableQuoteList} from './table-quote-list/table-quote-list';

@Component({
  selector: 'app-quotes-page',
  imports: [CardQuotesReports, TableQuoteList],
  templateUrl: './quotes-page.html',
  styleUrl: './quotes-page.css',
})
export class QuotesPage {

}
