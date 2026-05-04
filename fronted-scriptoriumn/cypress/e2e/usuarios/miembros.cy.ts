/**
 * Suite Cypress — Modulo Usuarios (Miembros)
 * Caso: CP-CY-USR-003 — Registro de nuevo usuario miembro
 * Requisito cubierto: RF-008
 */

describe('CP-CY-USR-003 — Registro de nuevo usuario miembro', () => {
  beforeEach(() => {
    cy.fixture('credenciales').as('creds');
    cy.fixture('miembros').as('miembros');

    cy.intercept('POST', '/Usuarios').as('crearMiembroReq');
    cy.intercept('GET', '/Usuarios').as('listaMiembros');
    cy.intercept('GET', '/Usuarios/buscar-usuarios*').as('buscarMiembroReq');

    cy.fixture('credenciales').then((creds) => {
      cy.loginBibliotecario(creds.validas.usuario, creds.validas.contrasena);
    });

    cy.visit('/biblioteca/miembros');
    cy.wait('@listaMiembros');
  });

  // Función auxiliar para cerrar cualquier modal abierto
  const closeModalIfOpen = () => {
    cy.get('body').then(($body) => {
      if ($body.find('.mat-mdc-dialog-container').length > 0) {
        cy.get('.mat-mdc-dialog-container button[mat-icon-button]').click({ force: true });
        cy.wait(300);
      }
    });
  };

  // Función auxiliar para abrir modal de agregar
  const openAddMemberModal = () => {
    closeModalIfOpen();
    cy.get('[data-cy=btn-agregar-miembro]').click();
    cy.wait(300);
  };

  it('debe mostrar la pantalla de miembros con la barra de busqueda y el boton de alta', () => {
    cy.get('[data-cy=miembros-page]').should('be.visible');
    cy.get('[data-cy=search-miembro-input]').should('be.visible');
    cy.get('[data-cy=btn-buscar-miembro]').should('be.visible');
    cy.get('[data-cy=btn-agregar-miembro]').should('be.visible');
    cy.get('[data-cy=lista-miembros]').should('be.visible');
  });

  it('debe abrir el modal de alta al hacer click en "Anadir miembro"', () => {
    openAddMemberModal();
    cy.get('[data-cy=form-agregar-miembro]').should('be.visible');
    cy.get('[data-cy=input-miembro-nombre]').should('be.visible');
    cy.get('[data-cy=input-miembro-fecha]').should('be.visible');
    cy.get('[data-cy=input-miembro-direccion]').should('be.visible');
    cy.get('[data-cy=input-miembro-contacto]').should('be.visible');
    cy.get('[data-cy=btn-guardar-miembro]').should('be.visible');
  });

  it('debe registrar un nuevo miembro con datos validos', function (this: Cypress.ThisContext) {
    // Aceptamos la alerta de exito que dispara el componente
    cy.on('window:alert', () => true);

    // Nombre unico por ejecucion para evitar duplicados en la BD
    const nombreUnico = `${this.miembros.valido.nombre} ${Date.now()}`;

    openAddMemberModal();

    // Llenar el formulario con {delay} para dar tiempo a Angular
    cy.get('[data-cy=input-miembro-nombre]').type(nombreUnico, { delay: 50 });
    cy.get('[data-cy=input-miembro-fecha]').type(this.miembros.valido.fechaNacimiento, { delay: 50 });
    cy.get('[data-cy=input-miembro-direccion]').type(this.miembros.valido.direccion, { delay: 50 });
    cy.get('[data-cy=input-miembro-contacto]').type(this.miembros.valido.contacto, { delay: 50 });

    cy.wait(300); // Esperar a que se procesen todos los cambios

    cy.get('[data-cy=btn-guardar-miembro]').click();

    cy.wait('@crearMiembroReq', { timeout: 10000 }).then((interception) => {
      expect(interception.response?.statusCode).to.be.oneOf([200, 201]);
    });
  });

  it('no debe enviar la peticion si faltan campos obligatorios', () => {
    openAddMemberModal();

    cy.get('[data-cy=input-miembro-nombre]').type('Solo Nombre', { delay: 50 });
    // Dejar el resto en blanco

    cy.wait(300);

    cy.get('[data-cy=btn-guardar-miembro]').click();

    // El form HTML5 (required) impide el submit; no debe haber peticion POST
    cy.get('@crearMiembroReq.all').should('have.length', 0);
    cy.get('[data-cy=form-agregar-miembro]').should('be.visible');
  });

  it('debe respetar la longitud maxima del nombre (50 chars)', function (this: Cypress.ThisContext) {
    openAddMemberModal();

    cy.get('[data-cy=input-miembro-nombre]').type(this.miembros.limites.nombre, { delay: 50 });

    cy.get('[data-cy=input-miembro-nombre]')
      .invoke('val')
      .then((val) => {
        // La UI puede o no truncar; documentamos el limite del PDF (<=50)
        expect(String(val).length).to.be.lte(50);
      });
  });
});
