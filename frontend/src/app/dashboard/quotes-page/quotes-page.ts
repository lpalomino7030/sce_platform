import { Component } from '@angular/core';
import {CardQuotesReports} from './card-quotes-reports/card-quotes-reports';

@Component({
  selector: 'app-quotes-page',
  imports: [CardQuotesReports],
  templateUrl: './quotes-page.html',
  styleUrl: './quotes-page.css',
})
export class QuotesPage {

}
