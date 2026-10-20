import { Injectable, inject } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import {CrearTenantResponse} from '../models/crear-tenant-response';
import {TenantRequest} from '../models/Tenant-Request';

@Injectable({
  providedIn: 'root',
})
export class TenantService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl =
    'http://localhost:8080';

  crearTenant(request: TenantRequest) {
    return this.http.post<CrearTenantResponse>(
      `${this.apiUrl}/api/tenants`,
      request
    );
  }


}
