import {Component, input} from '@angular/core';
import {NgIcon} from '@ng-icons/core';


@Component({
  selector: 'app-kanban-card-reports',
  imports: [NgIcon],
  templateUrl: './kanban-card-reports.html',
  styleUrl: './kanban-card-reports.css',
})
export class KanbanCardReports {
titulo=input.required<string>();
backgroundsColor=input<string>();
  icono=input<string>();
}
