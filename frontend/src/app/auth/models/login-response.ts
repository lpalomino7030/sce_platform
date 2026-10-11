import { TenantDisponibleResponse } from './tenant-disponible-response';


export type AuthContext = 'PLATFORM' | 'TENANT';

export interface LoginResponse {
  usuarioId: string;
  nombreUsuario: string;
  nombres: string;

  context: AuthContext;
  platformRole?: 'ADMIN' | 'SUPPORT' | 'BILLING';

  tenantSeleccionado?: TenantDisponibleResponse;
  tenants?: TenantDisponibleResponse[];

  token?: string;
  selectionToken?: string;
}
