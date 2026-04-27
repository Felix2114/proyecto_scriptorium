//export const API_URL = 'https://scriptorium-4sde.onrender.com';
export const API_URL = 'http://localhost:8080';

export const RUTAS_API = {
  BIBLIOTECARIOS: '/bibliotecarios',
  LIBROS: '/libro',
  PRESTAMOS: '/Prestamo',
  MIEMBROS: '/Usuarios',
  HTML: './biblio-bibliotecarios.component.html',
  HTML_LIBROS: './biblio-libros.component.html',
  CSS: './biblio-bibliotecarios.component.css',
  CSS_LIBROS: './biblio-libros.component.css',
  SELECTOR: 'app-biblio-bibliotecarios',
  PRESTAMOS_DEVOLVER: '/Prestamo/devolver',
  PRESTAMOS_PAGAR: '/Prestamo/pagar-multa',
  PRESTAMOS_MONTO: '/Prestamo/monto-multa',
  MULTAS: '/Multa',
  BIBLIOTECARIOID: 'bibliotecarioId',
  USUARIOLOGEADO: 'usuarioLogueado',
  HOME: '/biblioteca',
};

export const ENDPOINTS_LIBRO = {
  BUSCAR: '/libro/buscar-libros',
};

export const ENDPOINTS_AUTH = {
  LOGIN: '/bibliotecarios/login',
};

export const ENDPOINTS_USUARIOS = {
  BUSCAR: '/Usuarios/buscar-usuarios',
};

export const ENDPOINTS_PRESTAMOS = {
  BUSCAR: '/Prestamo/buscar_prestamo',
};

