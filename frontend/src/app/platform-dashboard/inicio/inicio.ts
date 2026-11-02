import { Component } from '@angular/core';
import {Tenants} from '../tenants/tenants';

@Component({
  selector: 'app-inicio',
  imports: [Tenants],
  templateUrl: './inicio.html',
  styleUrl: './inicio.css',
})
export class Inicio {

  modalTenant = false

  abrirModalTenant(): void {
    this.modalTenant = true;
  }

  cerrarModalTenant(): void {
    this.modalTenant = false;
  }
}
