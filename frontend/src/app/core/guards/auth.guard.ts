import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Auth } from '../../auth/auth';

export const authGuard: CanActivateFn = () => {

  const authService = inject(Auth);
  const router = inject(Router);

  const token = authService.obtenerToken();
  const context = authService.obtenerContexto();

  if (!token) {

    const selectionToken =
      authService.obtenerSelectionToken();

    if (selectionToken) {
      return router.createUrlTree(['/seleccionar']);
    }

    return router.createUrlTree(['/login']);
  }

  if (context === 'PLATFORM') {
    return router.createUrlTree(['/platform']);
  }

  if (context === 'TENANT') {
    return true;
  }

  return router.createUrlTree(['/login']);
};
