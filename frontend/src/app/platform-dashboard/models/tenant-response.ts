export type TenantEstado = 'ACTIVE' | 'INACTIVE';

export interface TenantResponse {
  id: string;
  nombre: string;
  razonSocial: string;
  ruc: string;
  slug: string;
  estado: TenantEstado;
}
