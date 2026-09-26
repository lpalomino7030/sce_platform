export interface TenantDisponibleResponse {
  id: string;
  nombre: string;
  role: 'OWNER' | 'ADMIN' | 'USER';
}
