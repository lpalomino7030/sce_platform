import { TenantDisponibleResponse } from './tenant-disponible-response';


export interface LoginResponse {
  usuarioId: string;
  nombreUsuario: string;
  nombres: string;
  tenantSeleccionado?: TenantDisponibleResponse;
  tenants?: TenantDisponibleResponse[];
  token?: string;
  selectionToken?: string;
}
