import { Routes } from '@angular/router';
import {Login} from './auth/login/login';
import { Dashboard } from './dashboard/dashboard';
import { authGuard } from './core/guards/auth.guard';
import { SeleccionarTenant } from './auth/seleccionar-tenant/seleccionar-tenant';

export const routes: Routes = [{
  path: '',
  component: Login
},
  {
    path: 'dashboard',
    component: Dashboard,
    canActivate: [authGuard],
  },
  {
    path: 'seleccionar-tenant',
    component: SeleccionarTenant
  }
];
