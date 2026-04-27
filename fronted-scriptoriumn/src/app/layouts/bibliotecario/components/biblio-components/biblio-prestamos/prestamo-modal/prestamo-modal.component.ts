import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  standalone: false,
  selector: 'app-prestamo-modal',
  templateUrl: './prestamo-modal.component.html',
  styleUrls: ['./prestamo-modal.component.css'],
})
export class PrestamoDetalleModalComponent {
  estadoLibro: string = '';
  constructor(
    public dialogRef: MatDialogRef<PrestamoDetalleModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  marcarDevuelto() {
    const estado = this.estadoLibro?.trim() || '';
    const multado = this.data.prestamo?.multado === true;
    const estadoDevuelto = this.data.prestamo?.estadoDevuelto;

    if (estadoDevuelto) {
      if (multado) {
        this.dialogRef.close({ accion: 'pagar' });
      } else {
        this.dialogRef.close();
      }
      return;
    }

    if (!estado) {
      alert('Por favor selecciona el estado del libro');
      return;
    }

    if (multado) {
      this.dialogRef.close({ accion: 'devolver', estado, pagarMulta: true });
    } else {
      this.dialogRef.close({ accion: 'devolver', estado });
    }
  }

  cerrar() {
    this.dialogRef.close();
  }
}
