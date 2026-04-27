
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'; 
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
//import { LoginComponent } from './layouts/registro/componentsLogin/login/login.component';
//import { LoginComponent } from './layouts/registro/componentsLogin/login/login.component';
//import { RegistroMainComponent } from './layouts/registro/registro-main.component';
//import { LoginComponent } from './layouts/login/components/login/login.component';  // Solo AppComponent aquí

@NgModule({
  declarations: [
    AppComponent,
    //LoginComponent,
    //RegistroMainComponent,
 
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
