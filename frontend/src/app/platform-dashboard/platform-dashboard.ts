import { Component } from '@angular/core';
import { Tenants} from './tenants/tenants';

@Component({
  selector: 'PlatformDashboard',
  imports: [Tenants],
  templateUrl: './platform-dashboard.html',
  styleUrl: './platform-dashboard.css',
})
export class PlatformDashboard {
  modalTenant = false

  abrirModalTenant(): void {
    this.modalTenant = true;
}

  cerrarModalTenant(): void {
    this.modalTenant = false;
  }

}
