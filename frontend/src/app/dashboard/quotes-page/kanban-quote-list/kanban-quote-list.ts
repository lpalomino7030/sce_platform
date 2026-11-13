import { Component, input } from '@angular/core';
import {KanbanCardReports} from './kanban-card-reports/kanban-card-reports';
import {Quote} from '../model/Quotes';
import {FormsModule} from '@angular/forms';
import {FiltersHeader} from '../filters-header/filters-header';

@Component({
  selector: 'app-kanban-quote-list',
  imports: [KanbanCardReports,FormsModule, FiltersHeader ],
  templateUrl: './kanban-quote-list.html',
  styleUrl: './kanban-quote-list.css',
})
export class KanbanQuoteList {
  searchText: string = '';
  selectedStatus: string = '';
  selectedResponsible: string = '';

  misEstados = ['En negociación', 'Aprobada', 'Pendiente', 'Rechazada'];
  misAuthors = ['María López', 'Juan Pérez', 'otros'];

  onStatusChange(estado: string): void {
    this.selectedStatus = estado;
  }

  onAuthorsChange(author: string): void {
    this.selectedResponsible = author;
  }

  quotes: Quote[] = [{
    codigo: 'COT-00129',
    cliente: 'Ferretería Lima S.A.C',
    emision: new Date(2025, 0, 25),
    validez: new Date(2025, 1, 24),
    importe: 2450,
    estado: 'En negociación',
    responsable: 'Juan Pérez'
  },{
    codigo: 'COT-00130',
    cliente: 'Comercial del Norte',
    emision: new Date(2025, 1, 10),
    validez: new Date(2025, 2, 10),
    importe: 3800,
    estado: 'Aprobada',
    responsable: 'María López'
  }]


  get filteredQuotes() : Quote[]{
    const search = this.searchText.toLowerCase().trim();

    return this.quotes.filter(quote => {

      const matchesSearch =
        quote.codigo.toLowerCase().includes(search) ||
        quote.cliente.toLowerCase().includes(search) ||
        quote.responsable.toLowerCase().includes(search);

      const matchesStatus =
        !this.selectedStatus ||
        quote.estado === this.selectedStatus;

      const matchesResponsible =
        !this.selectedResponsible ||
        quote.responsable === this.selectedResponsible;

      return matchesSearch && matchesStatus && matchesResponsible;

    });
  }

  clearFilters(): void {
    this.searchText = '';
    this.selectedStatus = '';
    this.selectedResponsible = '';
  }





}
