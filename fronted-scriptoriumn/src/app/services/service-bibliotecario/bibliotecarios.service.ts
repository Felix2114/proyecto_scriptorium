// src/app/servicios/usuario.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { API_URL, RUTAS_API } from '../../helpers/api-url.helper';
import { ApiResponse } from '../../utils/Apiresponse';

@Injectable({
  providedIn: 'root',
})
export class BibliotecariosService {
  constructor(private http: HttpClient) { }

  getBibliotecarios(paginaActual: number = 0): Observable<any> {
    const params = { page: paginaActual.toString() };

    return this.http
      .get<ApiResponse<any>>(`${API_URL}${RUTAS_API.BIBLIOTECARIOS}`, { params })
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }


  editarBibliotecario(id: number, bibliotecario: any): Observable<any> {


    return this.http
      .put<ApiResponse<any>>(
        `${API_URL}${RUTAS_API.BIBLIOTECARIOS}/${id}`,
        bibliotecario
      )
      .pipe(
        map((resp: ApiResponse<any>) => {
          //console.log(resp, resp.data);
          return resp.data;
        })
      );
  }

  eliminarBibliotecario(id: number): Observable<any> {
    return this.http
      .delete<ApiResponse<any>>(`${API_URL}${RUTAS_API.BIBLIOTECARIOS}/${id}`)
      .pipe(
        map((resp: ApiResponse<any>) => {
          console.log(resp, resp.data);
          return resp.data;
        })
      );
  }

}
