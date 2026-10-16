import {UsuarioRequest} from './Usuario-Request';

export interface TenantRequest {
  nombre: string;
  razonSocial: string;
  ruc: string;

  usuario: UsuarioRequest;
}
