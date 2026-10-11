import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Auth } from '../../auth/auth';

export const platformGuard: CanActivateFn = () => {

  const authService = inject(Auth);
  const router = inject(Router);

  const token = authService.obtenerToken();
  const context = authService.obtenerContexto();

  if (!token) {
    return router.createUrlTree(['/login']);
  }

  if (context !== 'PLATFORM') {
    return router.createUrlTree(['/dashboard']);
  }

  return true;
};
