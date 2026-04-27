import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegistroMainComponent } from './registro-main.component';
import { NewUserComponent } from './componentsLogin/new-user/new-user.component';
import { LoginComponent } from './componentsLogin/login/login.component';

const routes: Routes = [
  {
    path: '',
    component: RegistroMainComponent,
    children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: LoginComponent },
      { path: 'register', component: NewUserComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RegistroMainRoutingModule {}
