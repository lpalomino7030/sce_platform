import { Injectable, inject } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';

import { LoginRequest } from './models/login-request';
import { LoginResponse } from './models/login-response';
import {TenantSeleccionadoRequest} from './seleccionar/TenantSeleccionadoRequest';
import {TenantDisponibleResponse} from './models/tenant-disponible-response';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private readonly http = inject(HttpClient);

  private readonly apiUrl = 'http://localhost:8080/api/auth';

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      `${this.apiUrl}/login`,
      request
    );
  }
  guardarToken(token: string): void {
    localStorage.setItem('sce_token', token);
  }

  obtenerToken(): string | null {
    return localStorage.getItem('sce_token');
  }

  guardarSelectionToken(token: string): void {
    localStorage.setItem('sce_selection_token', token);
  }

  obtenerSelectionToken(): string | null {
    return localStorage.getItem('sce_selection_token');
  }

  guardarTenantsDisponibles(tenants: TenantDisponibleResponse[]): void {
    sessionStorage.setItem(
      'sce_available_tenants',
      JSON.stringify(tenants)
    );
  }
  obtenerTenantsDisponibles(): TenantDisponibleResponse[] {
    const data = sessionStorage.getItem('sce_available_tenants');

    if (!data) {
      return [];
    }

    return JSON.parse(data);
  }
  limpiarSeleccionTenant(): void {
    sessionStorage.removeItem('sce_selection_token');
    sessionStorage.removeItem('sce_available_tenants');
  }
  cerrarSesion(): void {
    localStorage.removeItem('sce_token');
    localStorage.removeItem('sce_selection_token');
  }

  seleccionarTenant(request: TenantSeleccionadoRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      `${this.apiUrl}/seleccionar`,
      request
    );
  }

  probarAutenticacion(): Observable<string> {
    return this.http.get(
      `${this.apiUrl}/prueba`,
      {
        responseType: 'text'
      }
    );
  }
}
