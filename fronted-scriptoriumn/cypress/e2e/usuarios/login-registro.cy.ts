/**
 * Suite Cypress — Módulo Usuarios
 * Casos: CP-CY-USR-001, CP-CY-USR-002, CP-CY-USR-003
 *
 * Requisitos cubiertos: RF-012 (autenticación), RF-008 (registro)
 */

describe('CP-CY-USR-001 — Inicio de sesión con credenciales válidas', () => {
  beforeEach(() => {
    cy.fixture('credenciales').as('creds');

    // Interceptar la petición de login para verificarla
    cy.intercept('POST', '/bibliotecarios/login').as('loginReq');

    cy.visit('/registro/login');
  });

  it('debe mostrar el formulario de login', () => {
    cy.get('[data-cy=login-form]').should('be.visible');
    cy.get('[data-cy=input-usuario]').should('be.visible');
    cy.get('[data-cy=input-contrasena]').should('be.visible');
    cy.get('[data-cy=btn-login]').should('be.visible');
  });

  it('debe redirigir al dashboard tras login exitoso', function (this: Cypress.ThisContext) {
    cy.get('[data-cy=input-usuario]').type(this.creds.validas.usuario);
    cy.get('[data-cy=input-contrasena]').type(this.creds.validas.contrasena);
    cy.get('[data-cy=btn-login]').click();

    cy.wait('@loginReq').its('response.statusCode').should('eq', 200);

    cy.url().should('include', '/biblioteca');
  });

  it('no debe exponer la contraseña en la UI tras el login', function () {
    cy.get('[data-cy=input-contrasena]').should('have.attr', 'type', 'password');
  });

  it('debe guardar el usuario en localStorage tras login exitoso', function (this: Cypress.ThisContext) {
    cy.get('[data-cy=input-usuario]').type(this.creds.validas.usuario);
    cy.get('[data-cy=input-contrasena]').type(this.creds.validas.contrasena);
    cy.get('[data-cy=btn-login]').click();

    cy.url().should('include', '/biblioteca');

    cy.window().then((win) => {
      expect(win.localStorage.getItem('usuarioLogueado')).to.equal(
        this.creds.validas.usuario
      );
    });
  });

  it('el botón de login debe estar deshabilitado con el formulario vacío', () => {
    cy.get('[data-cy=btn-login]').should('be.disabled');
  });
});

// ---------------------------------------------------------------------------

describe('CP-CY-USR-002 — Inicio de sesión con credenciales inválidas', () => {
  beforeEach(() => {
    cy.fixture('credenciales').as('creds');

    cy.intercept('POST', '/bibliotecarios/login').as('loginReq');

    cy.visit('/registro/login');
  });

  it('debe mostrar alerta de error con credenciales incorrectas', function (this: Cypress.ThisContext) {
    // El componente usa window.alert para notificar el error
    cy.on('window:alert', (texto) => {
      expect(texto).to.equal('Usuario o contraseña incorrecto');
    });

    cy.get('[data-cy=input-usuario]').type(this.creds.invalidas.usuario);
    cy.get('[data-cy=input-contrasena]').type(this.creds.invalidas.contrasena);
    cy.get('[data-cy=btn-login]').click();

    cy.wait('@loginReq').its('response.statusCode').should('be.oneOf', [401, 403, 400]);
  });

  it('debe permanecer en /registro/login tras credenciales inválidas', function (this: Cypress.ThisContext) {
    cy.on('window:alert', () => {});

    cy.get('[data-cy=input-usuario]').type(this.creds.invalidas.usuario);
    cy.get('[data-cy=input-contrasena]').type(this.creds.invalidas.contrasena);
    cy.get('[data-cy=btn-login]').click();

    cy.wait('@loginReq');
    cy.url().should('include', '/registro/login');
  });

  it('no debe guardar datos de sesión en localStorage tras error', function (this: Cypress.ThisContext) {
    cy.on('window:alert', () => {});

    cy.get('[data-cy=input-usuario]').type(this.creds.invalidas.usuario);
    cy.get('[data-cy=input-contrasena]').type(this.creds.invalidas.contrasena);
    cy.get('[data-cy=btn-login]').click();

    cy.wait('@loginReq');

    cy.window().then((win) => {
      expect(win.localStorage.getItem('usuarioLogueado')).to.be.null;
      expect(win.localStorage.getItem('bibliotecarioId')).to.be.null;
    });
  });

  it('debe rechazar campos vacíos sin realizar petición', () => {
    cy.get('[data-cy=input-usuario]').type('alguien');
    // Dejar contraseña vacía — botón debe permanecer deshabilitado
    cy.get('[data-cy=btn-login]').should('be.disabled');
  });
});

// ---------------------------------------------------------------------------

describe('CP-CY-USR-003 — Registro de nuevo bibliotecario', () => {
  beforeEach(() => {
    cy.fixture('credenciales').as('creds');
    cy.intercept('POST', '/bibliotecarios').as('registroReq');
    cy.visit('/registro/register');
  });

  it('debe mostrar el formulario de registro', () => {
    cy.get('[data-cy=form-registro]').should('be.visible');
    cy.get('[data-cy=input-usuario-registro]').should('be.visible');
    cy.get('[data-cy=input-contrasena-registro]').should('be.visible');
    cy.get('[data-cy=btn-registrarse]').should('be.visible');
  });

  it('el botón de registro debe estar deshabilitado con el formulario vacío', () => {
    cy.get('[data-cy=btn-registrarse]').should('be.disabled');
  });

  it('debe requerir contraseña de al menos 6 caracteres', () => {
    cy.get('[data-cy=input-usuario-registro]').type('nuevo_user');
    cy.get('[data-cy=input-contrasena-registro]').type('abc');
    cy.get('[data-cy=btn-registrarse]').should('be.disabled');
  });

  it('debe redirigir a /registro/login tras registro exitoso', function (this: Cypress.ThisContext) {
    // Usuario único por ejecución para evitar conflicto de duplicados en la BD
    const usuarioUnico = `test_${Date.now()}`;

    cy.get('[data-cy=input-usuario-registro]').type(usuarioUnico);
    cy.get('[data-cy=input-contrasena-registro]').type(this.creds.registro.contrasena);
    cy.get('[data-cy=btn-registrarse]').click();

    cy.wait('@registroReq').its('response.statusCode').should('be.oneOf', [200, 201]);

    cy.url().should('include', '/registro/login');
  });

  it('no debe exponer la contraseña en el formulario de registro', () => {
    cy.get('[data-cy=input-contrasena-registro]').should('have.attr', 'type', 'password');
  });

  it('debe tener un enlace de vuelta al login', () => {
    cy.get('[data-cy=link-login]')
      .should('be.visible')
      .and('have.attr', 'href')
      .and('include', '/registro/login');
  });
});
