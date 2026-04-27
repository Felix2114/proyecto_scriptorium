import { Component, OnInit } from '@angular/core';
import { BibliotecariosService } from '../../../../../services/service-bibliotecario/bibliotecarios.service';
import { MENSAJES, CLASES_TABLA } from '../../../../../helpers/mensajes.helper';
import { RUTAS_API } from '../../../../../helpers/api-url.helper';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: RUTAS_API.SELECTOR,
  standalone: true,
  templateUrl: RUTAS_API.HTML,
  styleUrls: [RUTAS_API.CSS],

  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
  ],
})
export class BiblioBibliotecariosComponent implements OnInit {
  bibliotecarios: any[] = [];
  paginaActual = 0;
  totalPaginas = 0;

  constructor(private bibliotecariosService: BibliotecariosService) { }

  ngOnInit(): void {
    this.cargarBibliotecarios();
  }

  cargarBibliotecarios(): void {
    this.bibliotecariosService.getBibliotecarios(this.paginaActual).subscribe((res) => {
      const nuevos = res.content.map((u: any) => ({
        ...u,
        editando: false,
        mostrarContrasena: false
      }));

      this.bibliotecarios = [...this.bibliotecarios, ...nuevos];
      this.totalPaginas = res.totalPages;
    });
  }

  cargarMas(): void {
    if (this.paginaActual + 1 < this.totalPaginas) {
      this.paginaActual++;
      this.cargarBibliotecarios();
    }
  }



  editarBibliotecario(bibliotecario: any): void {
    bibliotecario.editando = true;
  }

  guardarBibliotecario(bibliotecario: any): void {
    bibliotecario.editando = false;



    const bibliotecarioParaGuardar = {
      id: bibliotecario.id,
      usuario: bibliotecario.usuario,
      contraseña: bibliotecario.contrasena || '',
    };

    this.bibliotecariosService.editarBibliotecario(bibliotecario.id, bibliotecarioParaGuardar).subscribe(
      () => {
        alert(MENSAJES.USUARIO_ACTUALIZADO);
      },
      (error) => {
        console.error(MENSAJES.ERROR_GUARDAR_USUARIO, error);
        alert(MENSAJES.ERROR_GUARDAR_USUARIO);
      }
    );
  }

  eliminarBibliotecario(id: number): void {
    if (confirm(MENSAJES.ELIMINAR_USUARIO)) {
      this.bibliotecariosService.eliminarBibliotecario(id).subscribe(() => {
        this.bibliotecarios = this.bibliotecarios.filter((b) => b.id !== id);
        alert(MENSAJES.USUARIO_ELIMINADO);
      });
    }
  }

  getRowClass(index: number): string {
    return index % 2 === 0 ? CLASES_TABLA.PAR : CLASES_TABLA.IMPAR
  }

}
