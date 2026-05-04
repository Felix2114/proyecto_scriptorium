/**
 * Suite Cypress — Modulo Libros
 * Casos: CP-CY-LIB-001, CP-CY-LIB-002
 * Requisitos cubiertos: RF-011 (busqueda), RF-006 (disponibilidad / inventario)
 */

describe('CP-CY-LIB-001 — Busqueda de libro por titulo', () => {
  beforeEach(() => {
    cy.fixture('credenciales').as('creds');
    cy.fixture('busquedas').as('busq');

    cy.intercept('GET', '/libro/buscar-libros*').as('buscarReq');
    cy.intercept('GET', '/libro').as('listaLibros');

    cy.fixture('credenciales').then((creds) => {
      cy.loginBibliotecario(creds.validas.usuario, creds.validas.contrasena);
    });

    cy.visit('/biblioteca/libros');
    cy.wait('@listaLibros');
  });

  it('debe mostrar la pagina de gestion de libros con la barra de busqueda', () => {
    cy.get('[data-cy=libros-page]').should('be.visible');
    cy.get('[data-cy=search-input]').should('be.visible');
    cy.get('[data-cy=btn-buscar]').should('be.visible');
    cy.get('[data-cy=lista-libros]').should('be.visible');
  });

  it('debe devolver al menos 1 resultado al buscar un termino existente', function (this: Cypress.ThisContext) {
    cy.get('[data-cy=search-input]').clear().type(this.busq.terminoValido);
    cy.get('[data-cy=btn-buscar]').click();

    cy.wait('@buscarReq').its('response.statusCode').should('eq', 200);

    cy.get('[data-cy=libro-row]').should('have.length.at.least', 1);
  });

  it('debe filtrar la tabla mostrando titulos que contienen el termino buscado', function (this: Cypress.ThisContext) {
    cy.get('[data-cy=search-input]').clear().type(this.busq.terminoValido);
    cy.get('[data-cy=btn-buscar]').click();

    cy.wait('@buscarReq');

    // Comparacion insensible a mayusculas: cada celda titulo debe contener el termino
    cy.get('[data-cy=libro-titulo]').each(($el) => {
      const texto = $el.text().toLowerCase();
      expect(texto).to.include(this.busq.terminoValido.toLowerCase());
    });
  });

  it('al limpiar la busqueda debe recargar la lista completa', function (this: Cypress.ThisContext) {
    cy.get('[data-cy=search-input]').clear().type(this.busq.terminoValido);
    cy.get('[data-cy=btn-buscar]').click();
    cy.wait('@buscarReq');

    cy.get('[data-cy=search-input]').clear();
    cy.get('[data-cy=btn-buscar]').click();

    cy.wait('@listaLibros').its('response.statusCode').should('eq', 200);
  });

  it('debe dejar la tabla vacia cuando el termino no existe', function (this: Cypress.ThisContext) {
    cy.get('[data-cy=search-input]').clear().type(this.busq.terminoSinResultados);
    cy.get('[data-cy=btn-buscar]').click();

    // El backend puede responder 200 con [] o 404; cubrimos ambos
    cy.wait('@buscarReq');
    cy.get('[data-cy=libro-row]').should('have.length', 0);
  });
});

// ---------------------------------------------------------------------------

describe('CP-CY-LIB-002 — Consulta de disponibilidad / detalle de libro', () => {
  beforeEach(() => {
    cy.fixture('credenciales').then((creds) => {
      cy.loginBibliotecario(creds.validas.usuario, creds.validas.contrasena);
    });

    cy.intercept('GET', '/libro').as('listaLibros');

    cy.visit('/biblioteca/libros');
    cy.wait('@listaLibros');
  });

  it('cada fila debe mostrar titulo y precio visibles', () => {
    cy.get('[data-cy=libro-row]').should('have.length.at.least', 1);
    cy.get('[data-cy=libro-titulo]').first().should('not.be.empty');
    cy.get('[data-cy=libro-precio]').first().should('not.be.empty');
  });

  it('debe abrir el modal de detalle al hacer click sobre una fila', () => {
    cy.get('[data-cy=libro-row]').first().click();

    cy.get('[data-cy=libro-detalle]').should('be.visible');
    cy.get('[data-cy=detalle-titulo]').should('not.be.empty');
    cy.get('[data-cy=detalle-autor]').should('not.be.empty');
  });

  it('el detalle debe ofrecer la accion de generar prestamo', () => {
    cy.get('[data-cy=libro-row]').first().click();
    cy.get('[data-cy=btn-generar-prestamo]').should('be.visible').and('not.be.disabled');
  });

  it('el listado debe renderizar en menos de 2 segundos tras login', () => {
    const inicio = performance.now();
    cy.get('[data-cy=lista-libros]')
      .should('be.visible')
      .then(() => {
        const transcurrido = performance.now() - inicio;
        expect(transcurrido).to.be.lessThan(2000);
      });
  });
});
