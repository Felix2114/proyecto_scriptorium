import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../../../services/service-login/login.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MENSAJES } from '../../../../helpers/mensajes.helper';
import { RUTAS_API } from '../../../../helpers/api-url.helper';
@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})



export class LoginComponent implements OnInit {
 loginForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private loginService: LoginService,
    private router: Router
  ) {}

  ngOnInit(): void {
   
    this.loginForm = this.fb.group({
      usuario: ['', Validators.required],
      contraseña: ['', Validators.required]
    });
  }

  iniciarSesion(): void {
    const { usuario, contraseña } = this.loginForm.value;

    this.loginService.iniciarSesion(usuario, contraseña).subscribe({
      next: (res) => {
        localStorage.setItem(RUTAS_API.BIBLIOTECARIOID, res.id);
        localStorage.setItem(RUTAS_API.USUARIOLOGEADO, res.usuario);
        this.router.navigate([RUTAS_API.HOME]); 
      },
      error: (err) => {
        alert(MENSAJES.CREDENCIALES_INCORRECTAS);
        console.error(err);
      }
    });
  }
}
