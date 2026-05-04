import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MiembrosService } from '../../../../../../services/service-miembros/miembros.service';
import { MENSAJES } from '../../../../../../helpers/mensajes.helper';

@Component({
  selector: 'app-miembro-agregar-modal',
  standalone: false,
  templateUrl: './miembro-agregar-modal.component.html',
  styleUrl: './miembro-agregar-modal.component.css'
})
export class MiembroAgregarModalComponent {
  miembro: any = {
    id: '',
    nombre: '',
    fechaNacimiento: '',
    direccion: '',
    contacto: ''
  };

  constructor(
      @Inject(MAT_DIALOG_DATA) public data: any,
      private dialogRef: MatDialogRef<MiembroAgregarModalComponent>,
      private miembrosService: MiembrosService,
    ) {}

  cerrar(): void {
    this.dialogRef.close();
  }

  guardarUsuario(miembro: any): void {
    // Validación de campos requeridos y espacios en blanco
    const nombreTrimmed = miembro?.nombre?.trim() || '';
    const direccionTrimmed = miembro?.direccion?.trim() || '';
    const contactoTrimmed = miembro?.contacto?.trim() || '';
    const fechaNacimiento = miembro?.fechaNacimiento?.trim() || '';

    if (!nombreTrimmed || !fechaNacimiento || !direccionTrimmed || !contactoTrimmed) {
      alert('Por favor completa todos los campos requeridos');
      return;
    }

    // Validación de longitud máxima del nombre
    if (nombreTrimmed.length > 50) {
      alert('El nombre no puede exceder 50 caracteres');
      return;
    }

    const nuevoMiembro = {
      nombre: nombreTrimmed,
      fechaNacimiento: fechaNacimiento,
      direccion: direccionTrimmed,
      contacto: contactoTrimmed
    };

    this.miembrosService.guardarUsuario(nuevoMiembro).subscribe(
      () => {
        console.log(MENSAJES.USUARIO_GUARDADO);
        alert(MENSAJES.USUARIO_GUARDADO);
        this.cerrar();
      },
      (error) => {
        console.error(MENSAJES.ERROR_GUARDAR_USUARIO, error);
        alert(MENSAJES.ERROR_GUARDAR_USUARIO);
      }
    );
  }
}
