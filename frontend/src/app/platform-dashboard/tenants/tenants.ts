import { Component,output } from '@angular/core';

@Component({
  selector: 'app-tenants',
  imports: [],
  templateUrl: './tenants.html',
  styleUrl: './tenants.css',
})
export class Tenants {
  cerrar = output<void>();

  cerrarModal(): void {
    this.cerrar.emit();
  }
}
