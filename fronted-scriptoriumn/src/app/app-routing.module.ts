import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';



const routes: Routes = [
  { path: '', redirectTo: 'registro', pathMatch: 'full' },

  {
    path: 'registro',
    loadChildren: () =>
      import('./layouts/registro/registro-main.module').then(
        (m) => m.RegistroMainModule
      )
  },

  {
    path: 'biblioteca', // ✅ ahora sí coincide con tu redirección
    loadChildren: () =>
      import('./layouts/bibliotecario/bibliotecario.module').then(
        (m) => m.BibliotecarioModule
      )
  }
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
