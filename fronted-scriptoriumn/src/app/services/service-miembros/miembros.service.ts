import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { API_URL, RUTAS_API, ENDPOINTS_USUARIOS } from '../../helpers/api-url.helper';
import { ApiResponse } from '../../utils/Apiresponse';

@Injectable({
  providedIn: 'root',
})
export class MiembrosService {
  constructor(private http: HttpClient) {}

  getMiembros(): Observable<any> {
    return this.http
      .get<ApiResponse<any>>(`${API_URL}${RUTAS_API.MIEMBROS}`)
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }
  getMiembro(id: number): Observable<any> {
    return this.http
      .get<ApiResponse<any>>(`${API_URL}${RUTAS_API.MIEMBROS}/${id}`)
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }
  editarUsuario(id: number, usuario: any): Observable<any> {
    return this.http
      .put<ApiResponse<any>>(`${API_URL}${RUTAS_API.MIEMBROS}/${id}`, usuario)
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }

  buscarUsuarios(palabra: string): Observable<any[]> {
    return this.http
      .get<ApiResponse<any[]>>(
        `${API_URL}${ENDPOINTS_USUARIOS.BUSCAR}?palabra=${encodeURIComponent(
          palabra
        )}`
      )
      .pipe(map((res) => res.data));
  }

  eliminarUsuario(id: number): Observable<any> {
    return this.http
      .delete<ApiResponse<any>>(`${API_URL}${RUTAS_API.MIEMBROS}/${id}`)
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }

  guardarUsuario(usuario: any): Observable<any> {
    return this.http
      .post<ApiResponse<any>>(`${API_URL}${RUTAS_API.MIEMBROS}`, usuario)
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }
}
