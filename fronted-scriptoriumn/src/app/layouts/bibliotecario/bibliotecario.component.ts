import { Component, OnInit } from '@angular/core';
import { MENSAJES_NAV } from '../../helpers/mensajes.helper';

@Component({
  selector: 'app-bibliotecario',  
  templateUrl: './bibliotecario.component.html', 
  styleUrls: ['./bibliotecario.component.css'],  
  standalone: false,
})
export class BibliotecarioComponent implements OnInit {
 usuarioLogueado: string = 'Bibliotecario';
  labels = MENSAJES_NAV;

  ngOnInit(): void {
    const nombre = localStorage.getItem('usuarioLogueado');
    if (nombre) this.usuarioLogueado = nombre;
  }

}