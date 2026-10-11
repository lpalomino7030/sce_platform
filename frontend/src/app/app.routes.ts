import { Routes } from '@angular/router';
import {Login} from './auth/login/login';
import { Dashboard } from './dashboard/dashboard';
import { authGuard } from './core/guards/auth.guard';
import { SeleccionarTenant } from './auth/seleccionar-tenant/seleccionar-tenant';
import {PlatformDashboard} from './platform-dashboard/platform-dashboard';
import {platformGuard} from './core/guards/platform.guard';

export const routes: Routes = [ {
  path: 'login',
  component: Login
},
  {
    path: 'dashboard',
    component: Dashboard,
    canActivate: [authGuard],
  },
  {
    path: 'seleccionar',
    component: SeleccionarTenant
  },
  {
    path: 'platform',
    component: PlatformDashboard,
    canActivate: [platformGuard]
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'login'
  }
  ]
