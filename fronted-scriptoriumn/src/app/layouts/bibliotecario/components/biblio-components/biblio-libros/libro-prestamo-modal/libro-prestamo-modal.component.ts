import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { PrestamosService } from '../../../../../../services/service-prestamos/prestamos.service';
import { MENSAJES, ESTADOS_LIBRO, LOCALSTORAGE } from '../../../../../../helpers/mensajes.helper';

@Component({
  selector: 'app-libro-prestamo-modal',
  standalone: false,
  templateUrl: './libro-prestamo-modal.component.html',
  styleUrl: './libro-prestamo-modal.component.css'
})
export class LibroPrestamoModalComponent implements OnInit {
  usuarioLogueado: string | null = null;


  prestamo: any = {

    idPrestamo: '',
    ficha: '',
    usuario: '',
    libroId: '',
    bibliotecario: '',
    activo: true,
    multado: true,
    devuelto: false,
    estadoPrestamo: '',
    estadoDevuelto: '',
    fechaInicio: '',
    fechaFin: '',

  };

  estados = ESTADOS_LIBRO;

  ngOnInit(): void {

    this.usuarioLogueado = localStorage.getItem(LOCALSTORAGE.USUARIOLOGEADO);

    this.prestamo.bibliotecario = this.usuarioLogueado;

    this.prestamo.fechaInicio = this.formatDate(new Date());
  }


  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<LibroPrestamoModalComponent>,
    private prestamosService: PrestamosService,

  ) {

    if (data && data.libroId) {
      this.prestamo.libroId = data.libroId;
    }
  }


  formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
  }


  cerrar(): void {
    this.dialogRef.close();
  }

  guardarPrestamo(prestamo: any): void {
    this.prestamosService.agregarPrestamo(prestamo).subscribe(
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
}
