import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { API_URL, RUTAS_API, ENDPOINTS_PRESTAMOS } from '../../helpers/api-url.helper';
import { ApiResponse } from '../../utils/Apiresponse';

@Injectable({
  providedIn: 'root',
})
export class PrestamosService {
  constructor(private http: HttpClient) {}

  getPrestamos(): Observable<any> {
    return this.http
      .get<ApiResponse<any>>(`${API_URL}${RUTAS_API.PRESTAMOS}`)
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }

  editarPrestamo(idPrestamo: number, prestamo: any): Observable<any> {
    return this.http
      .put<ApiResponse<any>>(
        `${API_URL}${RUTAS_API.PRESTAMOS}/${idPrestamo}`,
        prestamo
      )
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }

  eliminarPrestamo(idPrestamo: number): Observable<any> {
    return this.http
      .delete<ApiResponse<any>>(
        `${API_URL}${RUTAS_API.PRESTAMOS}/${idPrestamo}`
      )
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }
  devolverLibro(idPrestamo: number, estado: string): Observable<any> {
    const estadoEncoded = encodeURIComponent(estado.trim());
    return this.http
      .put<ApiResponse<any>>(
        `${API_URL}${RUTAS_API.PRESTAMOS_DEVOLVER}/${idPrestamo}?estadoDevuelto=${estadoEncoded}`,
        {}
      )
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }
  pagarMulta(idPrestamo: number): Observable<any> {
    return this.http
      .put<ApiResponse<any>>(
        `${API_URL}${RUTAS_API.PRESTAMOS_PAGAR}/${idPrestamo}`,
        {}
      )
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }

  monto_multa(Id: number): Observable<any> {
    const body = { idPrestamo: Id };
    return this.http.post(`${API_URL}${RUTAS_API.PRESTAMOS_MONTO}`, body);
  }

  agregarPrestamo(prestamo: any): Observable<any>{
   return this.http
      .post<ApiResponse<any>>(`${API_URL}${RUTAS_API.PRESTAMOS}`, prestamo)
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );

  }

  buscarPrestamos(palabra: string): Observable<any[]> {
    return this.http
      .get<ApiResponse<any[]>>(
        `${API_URL}${ENDPOINTS_PRESTAMOS.BUSCAR}?palabra=${encodeURIComponent(
          palabra
        )}`
      )
      .pipe(map((res) => res.data));
  }
}
