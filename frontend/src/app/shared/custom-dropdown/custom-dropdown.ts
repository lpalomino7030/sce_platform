import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-custom-dropdown',
  imports: [],
  templateUrl: './custom-dropdown.html',
  styleUrl: './custom-dropdown.css',
})
export class CustomDropdown {

// @Input: Recibe la lista de estados desde el componente padre
  @Input() statuses: string[] = [];

  // @Input opcional: Texto por defecto si no hay nada seleccionado
  @Input() placeholder: string = 'Todos los estados';

  // @Input opcional: Estado seleccionado actualmente
  @Input() selectedStatus: string = '';

  // @Output: Emite el estado seleccionado hacia el padre
  @Output() statusSelected = new EventEmitter<string>();

  showStatusDropdown = false;

  toggleStatusDropdown(): void {
    this.showStatusDropdown = !this.showStatusDropdown;
  }

  selectStatus(status: string): void {
    this.selectedStatus = status;
    this.showStatusDropdown = false;

    // Emitimos el valor hacia el componente padre
    this.statusSelected.emit(status);
  }
}
