import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_URL, ENDPOINTS_AUTH } from '../../helpers/api-url.helper';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private http: HttpClient) {}

  iniciarSesion(usuario: String, contraseña: String): Observable<any> {
    return this.http.post(`${API_URL}${ENDPOINTS_AUTH.LOGIN}`, {
      usuario: usuario,
      contrasena: contraseña,
    });
  }
}
