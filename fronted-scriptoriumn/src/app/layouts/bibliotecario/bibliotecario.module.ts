import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BibliotecarioRoutingModule } from './bibliotecario-routing.module';

import { BibliotecarioComponent } from './bibliotecario.component';
import { BiblioUsuarioComponent } from './components/biblio-components/biblio-usuario/biblio-usuario.component';
import { BiblioLibrosComponent } from './components/biblio-components/biblio-libros/biblio-libros.component';
import { BiblioMiembrosComponent } from './components/biblio-components/biblio-miembros/biblio-miembros.component';
import { BiblioPrestamosComponent } from './components/biblio-components/biblio-prestamos/biblio-prestamos.component';
import { BiblioBibliotecariosComponent } from './components/biblio-components/biblio-bibliotecarios/biblio-bibliotecarios.component';

import { MatSelectModule } from '@angular/material/select';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { LibroDetalleModalComponent } from './components/biblio-components/biblio-libros/libro-detalle-modal/libro-detalle-modal.component';
import { LibroAgregarModalComponent } from './components/biblio-components/biblio-libros/libro-agregar-modal/libro-agregar-modal.component';
import { LibroPrestamoModalComponent } from './components/biblio-components/biblio-libros/libro-prestamo-modal/libro-prestamo-modal.component';
import { MiembroAgregarModalComponent } from './components/biblio-components/biblio-miembros/miembro-agregar-modal/miembro-agregar-modal.component';
import { PrestamoDetalleModalComponent } from './components/biblio-components/biblio-prestamos/prestamo-modal/prestamo-modal.component';

//quite a todos de las declaraciones por que son standalone y me marca error
@NgModule({
  declarations: [
    BibliotecarioComponent,
    BiblioUsuarioComponent,
    LibroDetalleModalComponent,
    LibroAgregarModalComponent,
    LibroPrestamoModalComponent,
    MiembroAgregarModalComponent,
    PrestamoDetalleModalComponent,
    //BiblioLibrosComponent,
    //BiblioMiembrosComponent,
    //BiblioPrestamosComponent,
    //BiblioBibliotecariosComponent
  ],
  imports: [
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatButtonModule,
    CommonModule,
    RouterModule,
    BibliotecarioRoutingModule,
    BiblioBibliotecariosComponent,
    BiblioPrestamosComponent,
    BiblioLibrosComponent,
    BiblioMiembrosComponent,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    FormsModule,
  ],
})
export class BibliotecarioModule {}
