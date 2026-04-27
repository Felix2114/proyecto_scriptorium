import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BibliotecarioComponent } from './bibliotecario.component';
import { BiblioUsuarioComponent } from './components/biblio-components/biblio-usuario/biblio-usuario.component';
import { BiblioLibrosComponent } from './components/biblio-components/biblio-libros/biblio-libros.component';
import { BiblioMiembrosComponent } from './components/biblio-components/biblio-miembros/biblio-miembros.component';
import { BiblioPrestamosComponent } from './components/biblio-components/biblio-prestamos/biblio-prestamos.component';
import { BiblioBibliotecariosComponent } from './components/biblio-components/biblio-bibliotecarios/biblio-bibliotecarios.component';

const routes: Routes = [
  {
    path: '',
    component: BibliotecarioComponent, 
    children: [
      { path: '', redirectTo: 'usuario', pathMatch: 'full' },
      { path: 'usuario', component: BiblioUsuarioComponent },
      { path: 'bibliotecarios', component: BiblioBibliotecariosComponent },
      { path: 'libros', component: BiblioLibrosComponent },
      { path: 'miembros', component: BiblioMiembrosComponent },
      { path: 'prestamos', component: BiblioPrestamosComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BibliotecarioRoutingModule {}
