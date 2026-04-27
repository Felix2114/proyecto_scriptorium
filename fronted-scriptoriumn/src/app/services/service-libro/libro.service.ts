import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { API_URL, RUTAS_API, ENDPOINTS_LIBRO } from '../../helpers/api-url.helper';
import { ApiResponse } from '../../utils/Apiresponse';

@Injectable({
  providedIn: 'root',
})
export class LibroService {
  constructor(private http: HttpClient) { }

  getLibros(): Observable<any> {
    return this.http
      .get<ApiResponse<any>>(`${API_URL}${RUTAS_API.LIBROS}`)
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }

  getLibro(id: number): Observable<any> {
    return this.http
      .get<ApiResponse<any>>(`${API_URL}${RUTAS_API.LIBROS}/${id}`)
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }

  editarLibro(id: number, libro: any): Observable<any> {
    return this.http
      .put<ApiResponse<any>>(`${API_URL}${RUTAS_API.LIBROS}/${id}`, libro)
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }

  eliminarLibro(id: number): Observable<any> {
    return this.http
      .delete<ApiResponse<any>>(`${API_URL}${RUTAS_API.LIBROS}/${id}`)
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }

  buscarLibros(palabra: string): Observable<any[]> {
    return this.http
      .get<ApiResponse<any[]>>(
        `${API_URL}${ENDPOINTS_LIBRO.BUSCAR}?palabra=${encodeURIComponent(palabra)}`
      )
      .pipe(map((res) => res.data));
  }

  guardarLibro(libro: any): Observable<any> {
    return this.http
      .post<ApiResponse<any>>(`${API_URL}${RUTAS_API.LIBROS}`, libro)
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }
}
