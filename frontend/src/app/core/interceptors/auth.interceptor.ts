import {HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {Auth} from '../../auth/auth';


export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const authService = inject(Auth);
  const token = authService.obtenerToken();

  if (!token) {
    return next(req);
  }

  const requestToken = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`
    }
  });

  return next(requestToken);
};
