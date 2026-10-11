import { Component, inject } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { Auth } from '../auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  private readonly authService = inject(Auth);
  private readonly router = inject(Router);

  loginForm = new FormGroup({
    username: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required
      ]
    }),

    password: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required
      ]
    })
  });

  iniciarSesion(): void {

    if (this.loginForm.invalid) {
      return;
    }

    this.authService
      .login(this.loginForm.getRawValue())
      .subscribe(response => {

        console.log(
          'Respuesta del servidor:',
          response
        );

        // Guardamos el contexto de la sesión
        this.authService.guardarContexto(
          response.context
        );

        // =====================================================
        // PLATFORM
        // =====================================================

        if (response.context === 'PLATFORM') {

          if (!response.token) {
            console.error(
              'El backend indicó PLATFORM pero no devolvió token'
            );
            return;
          }

          this.authService.guardarToken(
            response.token
          );

          console.log(
            'Sesión de plataforma iniciada'
          );

          this.router.navigate(['/platform']);

          return;
        }

        // =====================================================
        // TENANT
        // =====================================================

        if (response.context === 'TENANT') {

          // Usuario con un único tenant
          if (response.token) {

            this.authService.guardarToken(
              response.token
            );

            console.log(
              'Sesión tenant iniciada'
            );

            this.router.navigate(['/dashboard']);

            return;
          }

          // Usuario con múltiples tenants
          if (
            response.selectionToken &&
            response.tenants
          ) {

            this.authService.guardarSelectionToken(
              response.selectionToken
            );

            this.authService.guardarTenantsDisponibles(
              response.tenants
            );

            console.log(
              'Selección de tenant requerida'
            );

            this.router.navigate(['/seleccionar']);

            return;
          }

          console.error(
            'Respuesta TENANT inválida'
          );
        }

      });
  }

  probarAutenticacion(): void {

    this.authService
      .probarAutenticacion()
      .subscribe(response => {

        console.log(
          'Respuesta del backend:',
          response
        );

      });
  }
}
