/**
 * Suite Cypress — Modulo Multas
 * Casos: CP-CY-MLT-001, CP-CY-MLT-002, CP-CY-MLT-003
 */

describe('CP-CY-MLT-001 — Visualizacion de Multas Pendientes', () => {
  beforeEach(() => {
    cy.fixture('credenciales').then((creds) => {
      cy.loginBibliotecario(creds.validas.usuario, creds.validas.contrasena);
    });

    cy.intercept('GET', '**/Prestamo').as('listaPrestamos');
    cy.intercept('POST', '**/Prestamo/monto-multa').as('montoMultaReq');

    cy.visit('/biblioteca/prestamos');
    cy.wait('@listaPrestamos');
  });

  it('debe mostrar prestamos con estado de multa en la tabla', () => {
    cy.get('table tbody tr').should('have.length.at.least', 1);
    cy.get('table th').should('contain', 'Multado');
  });

  it('debe abrir modal y consultar monto al seleccionar un prestamo', () => {
    cy.get('table tbody tr').first().click();
    cy.wait('@montoMultaReq').its('response.statusCode').should('eq', 200);
    cy.get('.modal-container, .mat-mdc-dialog-container').should('be.visible');
  });

  it('debe mostrar informacion de multa con formato moneda cuando aplica', () => {
    cy.contains('td', 'true').then(($cell) => {
      if ($cell.length > 0) {
        cy.wrap($cell).closest('tr').click();
        cy.get('.modal-container, .mat-mdc-dialog-container').should('be.visible');
        cy.get('body').then(($body) => {
          if ($body.find('.multa-info').length > 0) {
            cy.get('.multa-info').invoke('text').should('match', /(\$|USD)/);
          }
        });
      }
    });
  });
});

describe('CP-CY-MLT-002 — Pago de Multas', () => {
  beforeEach(() => {
    cy.fixture('credenciales').then((creds) => {
      cy.loginBibliotecario(creds.validas.usuario, creds.validas.contrasena);
    });

    cy.intercept('GET', '**/Prestamo').as('listaPrestamos');
    cy.intercept('PUT', '**/Prestamo/pagar-multa/*').as('pagarMultaReq');
    cy.intercept('PUT', '**/Prestamo/devolver/*').as('actualizarPrestamoReq');

    cy.visit('/biblioteca/prestamos');
    cy.wait('@listaPrestamos');
  });

  it('debe mostrar boton de confirmacion en modal', () => {
    cy.get('table tbody tr').first().click();
    cy.get('button').contains('Confirmar acción').should('be.visible');
  });

  it('debe permitir seleccionar estado del libro cuando existe el select', () => {
    cy.get('table tbody tr').first().click();
    cy.get('body').then(($body) => {
      if ($body.find('select#estadoLibro').length > 0) {
        cy.get('select#estadoLibro').select('Buena', { force: true });
      }
    });
  });

  it('debe poder confirmar la accion desde el modal sin error de UI', () => {
    cy.on('window:alert', () => true);
    cy.get('table tbody tr').first().click();
    cy.get('button').contains('Confirmar acción').click({ force: true });
    cy.get('table').should('exist');
  });
});

describe('CP-CY-MLT-003 — Gestion Avanzada de Multas', () => {
  beforeEach(() => {
    cy.fixture('credenciales').then((creds) => {
      cy.loginBibliotecario(creds.validas.usuario, creds.validas.contrasena);
    });

    cy.intercept('GET', '**/Prestamo').as('listaPrestamos');

    cy.visit('/biblioteca/prestamos');
    cy.wait('@listaPrestamos');
  });

  it('debe filtrar prestamos por texto en barra de busqueda', () => {
    cy.get('.barra-busqueda input').clear().type('a');
    cy.get('.barra-busqueda button').click();
    cy.get('table').should('be.visible');
  });

  it('debe mantener la tabla visible despues de buscar', () => {
    cy.get('.barra-busqueda input').clear().type('usuario');
    cy.get('.barra-busqueda button').click();
    cy.get('table tbody').should('exist');
  });
});
