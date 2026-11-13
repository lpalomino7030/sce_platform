import {Component, input} from '@angular/core';

@Component({
  selector: 'app-kanban-card-reports',
  imports: [],
  templateUrl: './kanban-card-reports.html',
  styleUrl: './kanban-card-reports.css',
})
export class KanbanCardReports {
titulo=input.required<string>();
backgroundsColor=input<string>();

}
