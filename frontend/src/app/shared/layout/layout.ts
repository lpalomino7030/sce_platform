
import {Component, inject, Input} from '@angular/core';
import {
  Router,
  RouterLink,
  RouterLinkActive,
  RouterOutlet
} from '@angular/router';
import { Auth } from '../../auth/auth';

export interface MenuItem {
  label: string;
  route: string;
  exact?: boolean;
}

@Component({
  selector: 'app-dashboard-layout',
  standalone: true,
  imports: [
    RouterLink,
    RouterLinkActive,
    RouterOutlet
  ],
  templateUrl: './layout.html',
  styleUrl: './layout.css'
})
export class Layout {

  private readonly auth = inject(Auth);
  private readonly router = inject(Router);

  @Input() title = 'SCE Platform';
  @Input() menuItems: MenuItem[] = [];

  cerrarSesion(): void {

    this.auth.cerrarSesion();

    this.router.navigate(['/login']);
  }
}
