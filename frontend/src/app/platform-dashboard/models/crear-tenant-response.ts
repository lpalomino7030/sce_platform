import { TenantResponse } from './tenant-response';

export type UsuarioTenantRole = 'OWNER' | 'ADMIN' | 'MEMBER';

export interface OwnerInicialResponse {
  id: string;
  nombreUsuario: string;
  correo: string;
  nombres: string;
  apellidos: string;
  codigoSce: string;
  identificadorSce: string;
  role: UsuarioTenantRole;
}

export interface CrearTenantResponse {
  tenant: TenantResponse;
  usuario: OwnerInicialResponse;
  passwordTemporal: string;
}
