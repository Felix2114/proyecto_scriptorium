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
       
      this.miembrosService.guardarUsuario(miembro).subscribe(
        () => {
          console.log(MENSAJES.USUARIO_GUARDADO);
          alert(MENSAJES.USUARIO_GUARDADO);
        },
        (error) => {
          console.error(MENSAJES.ERROR_GUARDAR_USUARIO, error);
          alert(MENSAJES.ERROR_GUARDAR_USUARIO);
        }
      );
    }


}
