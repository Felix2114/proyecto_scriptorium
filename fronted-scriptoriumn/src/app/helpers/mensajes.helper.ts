export const MENSAJES = {
  //USUARIOS
  USUARIO_ACTUALIZADO: 'Usuario actualizado',
  ERROR_GUARDAR_USUARIO: 'Error al guardar el usuario',
  ELIMINAR_USUARIO: 'seguro que quieres eliminar el usuario?',
  USUARIO_ELIMINADO: 'usuario eliminado :)',
  USUARIO_GUARDADO: 'usuario guardado',
  //LIBROS
  LIBRO_ACTUALIZADO: 'libro actualizado',
  ERROR_GUARDAR_LIBRO: 'error al guardar libro',
  ELIMINAR_LIBRO: 'seguro que quieres eliminar el libro',
  LIBRO_GUARDADO: 'libro guardado',
  LIBRO_ELIMINADO: 'Libro eliminado',
  ERROR_ELIMINAR_LIBRO: 'error al eliminar libro',
  //PRESTAMOS
  PRESTAMO_ACTUALIZADO: 'Prestamo actualizado',
  ERROR_GUARDAR_PRESTAMO: 'Error al guardar el prestamo',
  ELIMINAR_PRESTAMO: 'seguro que quieres eliminar el prestamo?',
  PRESTAMO_ELIMINADO: 'prestamo eliminado',
  //LOGIN
  CREDENCIALES_INCORRECTAS: 'Usuario o contraseña incorrecto',

 
};

export const MENSAJES_NAV = {
  saludo: (nombre: string) => `Hola, ${nombre}!`,
  bibliotecarios: 'Bibliotecarios',
  libros: 'Libros',
  prestamos: 'Préstamos',
  miembros: 'Miembros'
};

export const CLASES_TABLA = {
  PAR: 'fila-par',
  IMPAR: 'fila-impar',
};

export function crearLibroParaGuardar(libro: any): any {
  return {
    idLibro: libro.idLibro,
    titulo: libro.titulo,
    autor: libro.autor,
    isbn: libro.isbn,
    precio: libro.precio,
    generoId: libro.generoId,
  };
}


import { MatDialog } from '@angular/material/dialog';

export function abrirDialogo(dialog: MatDialog, componente: any, ancho: string, data?: any, clase: string = 'custom-modal-container') {
  return dialog.open(componente, {
    width: ancho,
    panelClass: clase,
    data
  });
}


export const CONFIG_MODAL = {
  WIDTH: '750px',
  PANEL_CLASS: 'custom-modal-container'
};

export const ESTADOS_LIBRO = ['Nuevo', 'Buen estado', 'Aceptable', 'Dañado'];

export const LOCALSTORAGE = {
  USUARIOLOGEADO: 'usuarioLogueado'
};
