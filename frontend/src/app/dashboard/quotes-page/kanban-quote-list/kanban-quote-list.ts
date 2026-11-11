import { Component } from '@angular/core';
import {KanbanCardReports} from './kanban-card-reports/kanban-card-reports';

@Component({
  selector: 'app-kanban-quote-list',
  imports: [KanbanCardReports],
  templateUrl: './kanban-quote-list.html',
  styleUrl: './kanban-quote-list.css',
})
export class KanbanQuoteList {

}
