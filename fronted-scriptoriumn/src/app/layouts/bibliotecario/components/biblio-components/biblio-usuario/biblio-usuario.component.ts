import { Component, OnInit  } from '@angular/core';
import {  LOCALSTORAGE } from '../../../../../helpers/mensajes.helper';

@Component({
  selector: 'app-biblio-usuario',
  templateUrl: './biblio-usuario.component.html',
  styleUrls: ['./biblio-usuario.component.css'],
  standalone: false, 

})
export class BiblioUsuarioComponent implements OnInit {
 usuarioLogueado: string | null = null;
 bibliotecarioId: string | null = null;
 fechaActual: Date = new Date();

  ngOnInit(): void {
   
    this.usuarioLogueado = localStorage.getItem(LOCALSTORAGE.USUARIOLOGEADO);
  }

  
}
