import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegistroMainRoutingModule } from './registro-main-routing.module';

import { RegistroMainComponent } from './registro-main.component';
import { LoginComponent } from './componentsLogin/login/login.component';
import { NewUserComponent } from './componentsLogin/new-user/new-user.component';

import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  declarations: [
    RegistroMainComponent,
    LoginComponent,
    NewUserComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatToolbarModule,
    MatButtonModule,
    RegistroMainRoutingModule 
  ]
})
export class RegistroMainModule {}
