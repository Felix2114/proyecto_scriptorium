import { Component,Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { LibroService } from '../../../../../../services/service-libro/libro.service';
import { MENSAJES } from '../../../../../../helpers/mensajes.helper';

@Component({
  selector: 'app-libro-agregar-modal',
  standalone: false,
  templateUrl: './libro-agregar-modal.component.html',
  styleUrl: './libro-agregar-modal.component.css'
})
export class LibroAgregarModalComponent {

  libro: any = {
    titulo: '',
    autor: '',
    isbn: '',
    precio: '',
    generoId: '',
    stock: ''
  };

constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<LibroAgregarModalComponent>,
    private libroService: LibroService,
  ) {}

  cerrar(): void {
    this.dialogRef.close();
  }
    guardarLibro(libro: any): void {
       
      this.libroService.guardarLibro(libro).subscribe(
        () => {
          console.log(MENSAJES.LIBRO_GUARDADO);
          alert(MENSAJES.LIBRO_GUARDADO);
        },
        (error) => {
          console.error(MENSAJES.ERROR_GUARDAR_LIBRO, error);
          alert(MENSAJES.ERROR_GUARDAR_LIBRO);
        }
      );
    }
}
