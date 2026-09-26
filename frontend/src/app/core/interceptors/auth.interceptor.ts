import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Auth } from '../../auth/auth';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const authService = inject(Auth);

  const rutasPublicas = [
    '/api/auth/login',
    '/api/auth/seleccionar'
  ];

  const esRutaPublica = rutasPublicas.some(
    ruta => req.url.endsWith(ruta)
  );

  if (esRutaPublica) {
    return next(req);
  }

  const token = authService.obtenerToken();

  if (!token) {
    return next(req);
  }

  const requestConToken = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`
    }
  });

  return next(requestConToken);
};
