import { Routes } from '@angular/router';
import {Login} from './auth/login/login';
import { Dashboard } from './dashboard/dashboard';
import { authGuard } from './core/guards/auth.guard';
import { SeleccionarTenant } from './auth/seleccionar-tenant/seleccionar-tenant';
import {PlatformDashboard} from './platform-dashboard/platform-dashboard';
import {platformGuard} from './core/guards/platform.guard';
import {LandingPage} from './dashboard/landing-page/landing-page';
import {Inicio} from './platform-dashboard/inicio/inicio';
import {QuotesPage} from './dashboard/quotes-page/quotes-page';
import {Tenants} from './platform-dashboard/tenants/tenants';
import {Reportes} from './platform-dashboard/reportes/reportes';
import {TenantCrear} from './platform-dashboard/tenants/crear/tenantCrear';

export const routes: Routes = [ {
  path: 'login',
  component: Login
},
  {
    path: 'dashboard',
    component: Dashboard,
    canActivate: [authGuard],
    children: [
      {
        path: '',
        component: LandingPage
      },
      {
        path: 'cotizaciones',
        component: QuotesPage
      }
    ]
  },
  {
    path: 'seleccionar',
    component: SeleccionarTenant
  },
  {
    path: 'platform',
    component: PlatformDashboard,
    canActivate: [platformGuard],
    children:[{
      path:'',
      component:Inicio
    },
      {
        path:'empresas',
        component:Tenants
      },
      {
        path:'empresas/crear',
        component:TenantCrear
      },
      {
        path:'reportes',
        component:Reportes
      }]
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'login'
  }
  ]
