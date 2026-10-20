export interface OwnerInicialRequest {
  nombreUsuario: string;
  correo: string;
  nombres: string;
  apellidos: string;
}

export interface TenantRequest {
  nombre: string;
  razonSocial: string;
  ruc: string;
  usuario: OwnerInicialRequest;
}
