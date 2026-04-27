import { Component, OnInit } from '@angular/core';
import { PrestamosService } from '../../../../../services/service-prestamos/prestamos.service';
import { MENSAJES } from '../../../../../helpers/mensajes.helper';

import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PrestamoDetalleModalComponent } from './prestamo-modal/prestamo-modal.component';
import { MatDialog } from '@angular/material/dialog';
import { LibroService } from '../../../../../services/service-libro/libro.service';
import { MiembrosService } from '../../../../../services/service-miembros/miembros.service';
import { forkJoin } from 'rxjs';
//import { MultaService } from '../../../../../services/service-multa.service';

@Component({
  selector: 'app-biblio-prestamos',
  standalone: true,
  templateUrl: './biblio-prestamos.component.html',
  styleUrl: './biblio-prestamos.component.css',
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
export class BiblioPrestamosComponent {
  prestamo: any[] = [];
  filtroBusqueda: string = '';

  constructor(
    private prestamosService: PrestamosService,
    private usuarioService: MiembrosService,
    private libroService: LibroService,
    // private multaService: MultaService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.prestamosService.getPrestamos().subscribe((data) => {
      this.prestamo = data.map((l: any) => ({ ...l, editando: false }));
    });
  }

  editarPrestamo(prestamo: any): void {
    prestamo.editando = true;
  }

  getRowClass(index: number): string {
    return index % 2 === 0 ? 'fila-par' : 'fila-impar';
  }

  guardarPrestamo(prestamo: any): void {
    prestamo.editando = false;


    const prestamoParaGuardar = {
      idPrestamo: prestamo.idPrestamo,
      ficha: prestamo.ficha,
      usuarioId: prestamo.usuarioId,
      libroId: prestamo.libroId,
      bibliotecarioId: prestamo.bibliotecarioId,
      activo: prestamo.activo,
      multado: prestamo.multado,
      devuelto: prestamo.devuelto,
      estadoPrestamo: prestamo.estadoPrestamo,
      estadoDevuelto: prestamo.estadoDevuelto,
      fechaInicio: prestamo.fechaInicio,
      fechaFin: prestamo.fechaFin,
    };
    this.prestamosService
      .editarPrestamo(prestamo.idPrestamo, prestamoParaGuardar)
      .subscribe(
        () => {
          console.log(MENSAJES.PRESTAMO_ACTUALIZADO);
          alert(MENSAJES.PRESTAMO_ACTUALIZADO);
        },
        (error) => {
          console.error(MENSAJES.ERROR_GUARDAR_PRESTAMO, error);
          alert(MENSAJES.ERROR_GUARDAR_PRESTAMO);
        }
      );
  }

  eliminarPrestamo(idPrestamo: number): void {
    if (confirm(MENSAJES.ELIMINAR_PRESTAMO)) {
      this.prestamosService.eliminarPrestamo(idPrestamo).subscribe(() => {
        this.prestamo = this.prestamo.filter((u) => u.id !== idPrestamo);
        alert(MENSAJES.PRESTAMO_ELIMINADO);
      });
    }
  }
  refrescarPrestamos(): void {
    this.prestamosService.getPrestamos().subscribe((data) => {
      this.prestamo = data.map((l: any) => ({ ...l, editando: false }));
    });
  }

  abrirDetallePrestamo(prestamo: any) {
    forkJoin({
      libro: this.libroService.getLibro(prestamo.libroId),
      usuario: this.usuarioService.getMiembro(prestamo.usuarioId),
      Monto: this.prestamosService.monto_multa(prestamo.idPrestamo),
    }).subscribe(({ libro, usuario, Monto }) => {
      console.log(Monto);
      this.montoMulta(prestamo.idPrestamo);
      console.log('Monto recibido del backend:', prestamo.idPrestamo);

      const dialogRef = this.dialog.open(PrestamoDetalleModalComponent, {
        data: {
          libroTitulo: libro.titulo,
          nombreMiembro: usuario.nombre,
          monto: Monto,
          tipo: this.getTipoAccion(prestamo),
          prestamo: prestamo,
          listaEstados: [
            { label: 'Estado nuevo', value: 'Nuevo' },
            { label: 'En buen estado', value: 'Buena' },
            { label: 'Aceptable', value: 'Aceptable' },
            { label: 'Dañado', value: 'Dañado' },
          ],
        },
      });

      dialogRef.afterClosed().subscribe((result) => {
        if (!result) return;

        const { accion, estado, pagarMulta } = result;

        if (accion === 'pagar') {
          this.pagoMulta(prestamo.idPrestamo);
        }

        if (accion === 'devolver') {
          this.devolverLibro(prestamo.idPrestamo, estado);
          if (pagarMulta) this.pagoMulta(prestamo.idPrestamo);
        }

        this.refrescarPrestamos(); // Actualizar tabla
      });
    });
  }

  getTipoAccion(prestamo: any): string {
    if (prestamo.devuelto) return 'ninguno';
    return 'devolver';
  }

  pagoMulta(id: number) {
    this.prestamosService.pagarMulta(id).subscribe({
      next: (resp) => console.log('Multa pagada:', resp),
      error: (err) => console.error('Error al pagar multa:', err),
    });
  }

  devolverLibro(id: number, estado: string) {
    this.prestamosService.devolverLibro(id, estado).subscribe({
      next: (resp) => console.log('Libro devuelto:', resp),
      error: (err) => console.error('Error al devolver libro:', err),
    });
  }
  montoMulta(id: number) {
    this.prestamosService.monto_multa(id).subscribe((monto) => {
      console.log('Monto real:', monto);
    });
  }


  filtrarUsuarios(): void {
    const palabra = this.filtroBusqueda.trim();

    if (palabra === '') {
      // Si está vacío, carga todos de nuevo
      this.prestamosService.getPrestamos().subscribe(data => {
        this.prestamo = data.map((l: any) => ({ ...l, editando: false }));
      });
      return;
    }

    this.prestamosService.buscarPrestamos(palabra).subscribe(
      (resultados) => {
        this.prestamo = resultados.map((l: any) => ({ ...l, editando: false }));
      },
      (error) => {
        this.prestamo = [];
      }
    );
  }

}
