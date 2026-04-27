import { Component, OnInit } from '@angular/core';
import { MiembrosService } from '../../../../../services/service-miembros/miembros.service';
import { MENSAJES } from '../../../../../helpers/mensajes.helper';
import { MatDialog } from '@angular/material/dialog';

import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MiembroAgregarModalComponent } from './miembro-agregar-modal/miembro-agregar-modal.component';
@Component({
  selector: 'app-biblio-miembros',
  standalone: true,
  templateUrl: './biblio-miembros.component.html',
  styleUrl: './biblio-miembros.component.css',
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
export class BiblioMiembrosComponent {
 miembros: any[] = [];
 filtroBusqueda: string = '';


 constructor(
  private miembrosService: MiembrosService,
   private dialog: MatDialog
 ) {}

  ngOnInit(): void {
    this.miembrosService.getMiembros().subscribe(data => {
      this.miembros = data.map((l: any) => ({ ...l, editando: false }));
    });
  }
   

editarUsuario(usuario: any): void {
    usuario.editando = true;
  }

  abrirAgregarUsuario(): void {
    this.dialog.open(MiembroAgregarModalComponent, {
      width: '750px',
      panelClass: 'custom-modal-container' // opcional, para estilos
    });
  }

  guardarUsuario(usuario: any): void {
      usuario.editando = false;
  
      const usuarioParaGuardar = {
        id: usuario.id,
        nombre: usuario.nombre,
        fechaNacimiento: usuario.fechaNacimiento,
        direccion: usuario.direccion,
        contacto: usuario.contacto,
      };
  
      this.miembrosService.editarUsuario(usuario.id, usuarioParaGuardar).subscribe(
        () => {
          console.log(MENSAJES.USUARIO_ACTUALIZADO);
          alert(MENSAJES.USUARIO_ACTUALIZADO);
        },
        (error) => {
          console.error(MENSAJES.ERROR_GUARDAR_USUARIO, error);
          alert(MENSAJES.ERROR_GUARDAR_USUARIO);
        }
      );
    }

    eliminarUsuario(id: number): void {
    if (confirm(MENSAJES.ELIMINAR_USUARIO)) {
      this.miembrosService.eliminarUsuario(id).subscribe(() => {
        this.miembros = this.miembros.filter((m) => m.id !== id);
        alert(MENSAJES.USUARIO_ELIMINADO);
      });
    }
  }

  filtrarUsuarios(): void {
  const palabra = this.filtroBusqueda.trim();

  if (palabra === '') {
    // Si está vacío, carga todos de nuevo
    this.miembrosService.getMiembros().subscribe(data => {
      this.miembros = data.map((l: any) => ({ ...l, editando: false }));
    });
    return;
  }

  this.miembrosService.buscarUsuarios(palabra).subscribe(
    (resultados) => {
      this.miembros = resultados.map((l: any) => ({ ...l, editando: false }));
    },
    (error) => {
      this.miembros = []; // Limpia la tabla si no encuentra nada
    }
  );
}

  getRowClass(index: number): string {
  return index % 2 === 0 ? 'fila-par' : 'fila-impar';
}

}
