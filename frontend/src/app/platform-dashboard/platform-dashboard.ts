import { Component } from '@angular/core';
import { Tenants} from './tenants/tenants';
import { Layout, MenuItem } from '../shared/layout/layout';

@Component({
  selector: 'PlatformDashboard',
  imports: [Layout],
  templateUrl: './platform-dashboard.html',
  styleUrl: './platform-dashboard.css',
})
export class PlatformDashboard {
  menuItems: MenuItem[] = [
    { label: 'Inicio', route: '/platform', exact: true },
    { label: 'Empresas', route: '/platform/empresas' },
    { label: 'Reportes', route: '/platform/reportes' }
  ];

}
