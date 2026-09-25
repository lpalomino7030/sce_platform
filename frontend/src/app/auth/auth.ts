import { Injectable, inject } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';

import { LoginRequest } from './models/login-request';
import { LoginResponse } from './models/login-response';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private readonly http = inject(HttpClient);

  private readonly apiUrl = 'http://localhost:8080/api/auth';

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      `${this.apiUrl}/login`,
      request
    );
  }
  guardarToken(token: string): void {
    localStorage.setItem('sce_token', token);
  }

  obtenerToken(): string | null {
    return localStorage.getItem('sce_token');
  }

  guardarSelectionToken(token: string): void {
    localStorage.setItem('sce_selection_token', token);
  }

  obtenerSelectionToken(): string | null {
    return localStorage.getItem('sce_selection_token');
  }

  cerrarSesion(): void {
    localStorage.removeItem('sce_token');
    localStorage.removeItem('sce_selection_token');
  }

  probarAutenticacion(): Observable<string> {
    return this.http.get(
      `${this.apiUrl}/prueba`,
      {
        responseType: 'text'
      }
    );
  }
}
