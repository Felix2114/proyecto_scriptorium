import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { LibroPrestamoModalComponent } from '../libro-prestamo-modal/libro-prestamo-modal.component';
import { MatDialog } from '@angular/material/dialog';
import { MENSAJES, CONFIG_MODAL } from '../../../../../../helpers/mensajes.helper';

@Component({
  selector: 'app-libro-detalle-modal',
  standalone: false,
  templateUrl: './libro-detalle-modal.component.html',
  styleUrl: './libro-detalle-modal.component.css'
})
export class LibroDetalleModalComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<LibroDetalleModalComponent>,
    private dialog: MatDialog
  ) { }

  cerrar(): void {
    this.dialogRef.close();
  }

  abrirAgregarPrestamo(): void {
    this.dialog.open(LibroPrestamoModalComponent, {
      width: CONFIG_MODAL.WIDTH,
      panelClass: CONFIG_MODAL.PANEL_CLASS,
      data: {
        libroId: this.data.idLibro
      }
    });
  }

}
