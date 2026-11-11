import { Component } from '@angular/core';
import {CardQuotesReports} from './table-quote-list/card-quotes-reports/card-quotes-reports';
import {TableQuoteList} from './table-quote-list/table-quote-list';
import {KanbanQuoteList} from './kanban-quote-list/kanban-quote-list';

@Component({
  selector: 'app-quotes-page',
  imports: [CardQuotesReports, TableQuoteList, KanbanQuoteList],
  templateUrl: './quotes-page.html',
  styleUrl: './quotes-page.css',
})
export class QuotesPage {
  selectedVista: String = 'Tabla';
 modoVista :string[] =['Tabla', 'kanban'];

 onChangeVista(vista:String){
   this.selectedVista = vista;
 }
}
