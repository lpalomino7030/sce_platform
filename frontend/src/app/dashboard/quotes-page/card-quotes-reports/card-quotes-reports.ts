import { Component, input } from '@angular/core';

@Component({
  selector: 'app-card-quotes-reports',
  imports: [],
  templateUrl: './card-quotes-reports.html',
  styleUrl: './card-quotes-reports.css',
})
export class CardQuotesReports {

  legend=input.required<string>();
  count=input<string>();
  icon=input<string>();
  suggestion=input<string>();

}
