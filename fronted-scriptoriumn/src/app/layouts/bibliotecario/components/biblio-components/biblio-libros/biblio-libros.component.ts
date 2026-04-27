import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { LibroService } from '../../../../../services/service-libro/libro.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MENSAJES, CLASES_TABLA, crearLibroParaGuardar, abrirDialogo } from '../../../../../helpers/mensajes.helper';
import { RUTAS_API } from '../../../../../helpers/api-url.helper';
import { MatDialog } from '@angular/material/dialog';
import { LibroDetalleModalComponent } from './libro-detalle-modal/libro-detalle-modal.component';
import { LibroAgregarModalComponent } from './libro-agregar-modal/libro-agregar-modal.component';

import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'biblio-libros',
  standalone: true,
  templateUrl: RUTAS_API.HTML_LIBROS,
  styleUrls: [RUTAS_API.CSS_LIBROS],
  encapsulation: ViewEncapsulation.None,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule
  ]
})
export class BiblioLibrosComponent implements OnInit {
  libros: any[] = [];
  filtroBusqueda: string = '';

  constructor(
    private libroService: LibroService,
    private dialog: MatDialog
  ) { }



  ngOnInit(): void {
    this.libroService.getLibros().subscribe(data => {
      this.libros = data.map((l: any) => ({ ...l, editando: false }));
    });
  }

  editarLibro(libro: any): void {
    libro.editando = true;
  }
  getRowClass(index: number): string {
    return index % 2 === 0 ? CLASES_TABLA.PAR : CLASES_TABLA.IMPAR
  }

  abrirDetalleLibro(libro: any): void {
    abrirDialogo(this.dialog, LibroDetalleModalComponent, '500px', libro);
  }

  abrirAgregarLibro(): void {
    abrirDialogo(this.dialog, LibroAgregarModalComponent, '750px');
  }

  guardarLibro(libro: any): void {

    libro.editando = false;

    const libroParaGuardar = crearLibroParaGuardar(libro);

    this.libroService.editarLibro(libro.idLibro, libroParaGuardar).subscribe(
      () => {
        console.log(MENSAJES.LIBRO_ACTUALIZADO);
        alert(MENSAJES.LIBRO_ACTUALIZADO);
      },
      (error) => {
        console.error(MENSAJES.ERROR_GUARDAR_LIBRO, error);
        alert(MENSAJES.ERROR_GUARDAR_LIBRO);
      }
    );
  }

  eliminarLibro(idLibro: number): void {
    if (confirm(MENSAJES.ELIMINAR_LIBRO)) {

      this.libroService.eliminarLibro(idLibro).subscribe(
        () => {
          this.libros = this.libros.filter(l => l.idLibro !== idLibro);
          alert(MENSAJES.LIBRO_ELIMINADO);
        },
        (error) => {
          alert(MENSAJES.ERROR_ELIMINAR_LIBRO);
        }
      );
    }

  }

  filtrarLibros(): void {
    const palabra = this.filtroBusqueda.trim();

    if (palabra === '') {
      this.libroService.getLibros().subscribe(data => {
        this.libros = data.map((l: any) => ({ ...l, editando: false }));
      });
      return;
    }

    this.libroService.buscarLibros(palabra).subscribe(
      (resultados) => {
        this.libros = resultados.map((l: any) => ({ ...l, editando: false }));
      },
      (error) => {
        this.libros = [];
      }
    );
  }


}
