import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_URL, RUTAS_API } from '../../helpers/api-url.helper';

@Injectable({
  providedIn: 'root',
})
export class RegistroService {
  constructor(private http: HttpClient) {}

  registrar(usuario: string, contraseña: string): Observable<any> {
    return this.http.post(`${API_URL}${RUTAS_API.BIBLIOTECARIOS}`, {
      usuario,
      contrasena: contraseña
    });
  }
}
