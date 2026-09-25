import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Auth } from '../auth';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private readonly authService = inject(Auth);

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

    this.authService.login(this.loginForm.getRawValue())
      .subscribe(response => {

        console.log('Respuesta del servidor:', response);

        if (response.token) {
          this.authService.guardarToken(response.token);

          console.log('JWT guardado correctamente');

          // Por ahora iremos al dashboard.
        }

        if (response.selectionToken) {
          this.authService.guardarSelectionToken(
            response.selectionToken
          );

          console.log('Selection token guardado correctamente');

          // Por ahora iremos a selección de tenant.
        }
      });
  }

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
