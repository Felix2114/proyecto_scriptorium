import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegistroService } from '../../../../services/service-registro/registro.service';

@Component({
  selector: 'app-new-user',
  standalone: false,
  templateUrl: './new-user.component.html',
  styleUrl: './new-user.component.css'
})
export class NewUserComponent implements OnInit {
  registroForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private registroService: RegistroService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registroForm = this.fb.group({
      usuario: ['', [Validators.required]],
      contraseña: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  registrarBibliotecario(): void {
    if (this.registroForm.invalid) return;
    const { usuario, contraseña } = this.registroForm.value;

    this.registroService.registrar(usuario, contraseña).subscribe({
      next: (res) => {
      console.log('Registro exitoso:', res);
      this.router.navigate(['/registro/login']);
      },
      error: (err) => {
        console.error('Error en el registro:', err);
      }
    });
  }
}
