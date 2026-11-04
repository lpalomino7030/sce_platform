import { Component } from '@angular/core';
import { Layout, MenuItem } from '../shared/layout/layout';

@Component({
  selector: 'app-dashboard',
  imports: [Layout],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  menuItems: MenuItem[] = [
    { label: 'Inicio', route: '/dashboard', exact: true },
    { label: 'Perfil', route: '/dashboard/perfil' },
    { label: 'Cotizaciones', route: '/dashboard/cotizaciones' },
    { label: 'Reportes', route: '/dashboard/reportes' }
  ];
}
