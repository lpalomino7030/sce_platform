import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

import { Auth } from '../auth';
import { TenantDisponibleResponse } from '../models/tenant-disponible-response';

@Component({
  selector: 'app-seleccionar-tenant',
  imports: [],
  templateUrl: './seleccionar-tenant.html',
  styleUrl: './seleccionar-tenant.css',
})
export class SeleccionarTenant {
  private readonly authService = inject(Auth);
  private readonly router = inject(Router);

  tenants: TenantDisponibleResponse[] = [];

  ngOnInit(): void {
    this.tenants = this.authService.obtenerTenantsDisponibles();

    if (this.tenants.length === 0) {
      this.router.navigate(['/']);
    }
  }

  seleccionarTenant(tenant: TenantDisponibleResponse): void {

    const selectionToken =
      this.authService.obtenerSelectionToken();

    if (!selectionToken) {
      this.router.navigate(['/']);
      return;
    }

    this.authService.seleccionarTenant({
      tenantId: tenant.id,
      selectionToken: selectionToken
    }).subscribe(response => {

      if (!response.token) {
        return;
      }

      this.authService.guardarToken(response.token);

      this.authService.limpiarSeleccionTenant();

      this.router.navigate(['/dashboard']);
    });
  }
}
