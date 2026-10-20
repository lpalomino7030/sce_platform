import { Component,output,inject } from '@angular/core';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { CrearTenantResponse } from '../models/crear-tenant-response';
import {TenantService} from '../services/tenants';
import {TenantRequest} from '../models/Tenant-Request';


@Component({
  selector: 'app-tenants',
  imports: [ReactiveFormsModule],
  templateUrl: './tenants.html',
  styleUrl: './tenants.css',
})
export class Tenants {

  private fb = inject(FormBuilder);
  private tenantService = inject(TenantService);

  cerrar = output<void>();

  cargando = false;
  errorMensaje = '';
  respuestaCreacion: CrearTenantResponse | null = null;

  formulario = this.fb.nonNullable.group({
    nombre: ['', Validators.required],
    razonSocial: ['', Validators.required],
    ruc: [
      '',
      [
        Validators.required,
        Validators.pattern(/^\d{11}$/)
      ]
    ],
    nombreUsuario: ['', Validators.required],
    correo: ['', [Validators.required, Validators.email]],
    nombres: ['', Validators.required],
    apellidos: ['', Validators.required]
  });

  cerrarModal(): void {
    this.cerrar.emit();
  }

  guardarTenant(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const valores = this.formulario.getRawValue();

    const request: TenantRequest = {
      nombre: valores.nombre,
      razonSocial: valores.razonSocial,
      ruc: valores.ruc,
      usuario: {
        nombreUsuario: valores.nombreUsuario,
        correo: valores.correo,
        nombres: valores.nombres,
        apellidos: valores.apellidos
      }
    };

    this.cargando = true;
    this.errorMensaje = '';

    this.tenantService.crearTenant(request).subscribe({
      next: (response) => {
        this.respuestaCreacion = response;
        this.cargando = false;
      },
      error: (error) => {
        this.cargando = false;

        this.errorMensaje =
          error.error?.message ??
          'No se pudo crear la empresa. Verifica los datos e inténtalo nuevamente.';
      }
    });
  }

}
