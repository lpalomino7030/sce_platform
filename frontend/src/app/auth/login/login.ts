import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
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
    if (this.loginForm.invalid)
    {
      return;
    }
    this.authService.login(this.loginForm.getRawValue())
      .subscribe(
      response => {
        console.log('Respuesta del servidor:', response);
    // Caso 1: el usuario ya tiene un tenant seleccionado
    if (response.token) { this.authService.guardarToken(response.token);
      console.log('JWT guardado correctamente');
    this.router.navigate(['/dashboard']);
     return;
    }
    // Caso 2: el usuario debe seleccionar un tenant
        if (response.selectionToken && response.tenants) {
          this.authService.guardarSelectionToken(
            response.selectionToken );
          this.authService.guardarTenantsDisponibles(
            response.tenants );
          console.log('Selección de tenant requerida');
          this.router.navigate(['/seleccionar-tenant']);
          return; }
      }); }

  probarAutenticacion(): void {

    this.authService.probarAutenticacion()
      .subscribe(response => {

        console.log(
          'Respuesta del backend:',
          response
        );

      });
  }

}
