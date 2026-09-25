import { Routes } from '@angular/router';
import {Login} from './auth/login/login';
import { Dashboard } from './dashboard/dashboard';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [{
  path: '',
  component: Login
},
  {
    path: 'app-dashboard',
    component: Dashboard,
    canActivate: [authGuard],
  }
];
