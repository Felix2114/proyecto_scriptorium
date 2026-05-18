/**
 * Suite Cypress — Modulo Prestamos
 * Casos: CP-CY-PRES-001, CP-CY-PRES-002, CP-CY-PRES-003
 */

describe('CP-CY-PRES-001 — Gestion de Prestamos', () => {
  beforeEach(() => {
    cy.fixture('credenciales').then((creds) => {
      cy.loginBibliotecario(creds.validas.usuario, creds.validas.contrasena);
    });

    cy.intercept('GET', '**/Prestamo').as('listaPrestamos');
    cy.intercept('GET', '**/Prestamo/buscar_prestamo*').as('buscarPrestamos');

    cy.visit('/biblioteca/prestamos');
    cy.wait('@listaPrestamos');
  });

  it('debe mostrar la pagina de gestion de prestamos con la barra de busqueda', () => {
    cy.get('h2').should('contain', 'Prestamos');
    cy.get('.barra-busqueda').should('be.visible');
    cy.get('.barra-busqueda input').should('be.visible');
    cy.get('.barra-busqueda button').should('be.visible');
    cy.get('table').should('be.visible');
  });

  it('debe mostrar la tabla con columnas esenciales', () => {
    cy.get('th').should('contain', 'ID Prestamo');
    cy.get('th').should('contain', 'Ficha');
    cy.get('th').should('contain', 'ID usuario');
    cy.get('th').should('contain', 'ID libro');
    cy.get('th').should('contain', 'Multado');
    cy.get('th').should('contain', 'Devuelto');
    cy.get('th').should('contain', 'Estado Prestamo');
  });

  it('debe mostrar al menos 1 fila de prestamo en la tabla', () => {
    cy.get('table tbody tr').should('have.length.at.least', 1);
  });

  it('debe permitir buscar prestamos por nombre del usuario', () => {
    cy.get('.barra-busqueda input').clear().type('a');
    cy.get('.barra-busqueda button').click();
    cy.wait('@buscarPrestamos').its('response.statusCode').should('eq', 200);
    cy.get('table').should('be.visible');
  });
});

describe('CP-CY-PRES-002 — Devolucion de Libros', () => {
  beforeEach(() => {
    cy.fixture('credenciales').then((creds) => {
      cy.loginBibliotecario(creds.validas.usuario, creds.validas.contrasena);
    });

    cy.intercept('GET', '**/Prestamo').as('listaPrestamos');
    cy.intercept('PUT', '**/Prestamo/devolver/*').as('devolverLibroReq');

    cy.visit('/biblioteca/prestamos');
    cy.wait('@listaPrestamos');
  });

  it('debe abrir modal de detalle al seleccionar un prestamo', () => {
    cy.get('table tbody tr').first().click();
    cy.get('.modal-container, .mat-mdc-dialog-container').should('be.visible');
  });

  it('debe mostrar opcion de confirmar accion en modal de devolucion', () => {
    cy.get('table tbody tr').first().click();
    cy.get('button').contains('Confirmar acción').should('be.visible');
  });

  it('debe mostrar select de estado cuando aplica devolucion', () => {
    cy.get('table tbody tr').first().click();
    cy.get('body').then(($body) => {
      if ($body.find('select#estadoLibro').length > 0) {
        cy.get('select#estadoLibro').should('be.visible');
      }
    });
  });
});

describe('CP-CY-PRES-003 — Gestion de Multas en Prestamos', () => {
  beforeEach(() => {
    cy.fixture('credenciales').then((creds) => {
      cy.loginBibliotecario(creds.validas.usuario, creds.validas.contrasena);
    });

    cy.intercept('GET', '**/Prestamo').as('listaPrestamos');
    cy.intercept('POST', '**/Prestamo/monto-multa').as('montoMultaReq');
    cy.intercept('PUT', '**/Prestamo/pagar-multa/*').as('pagarMultaReq');

    cy.visit('/biblioteca/prestamos');
    cy.wait('@listaPrestamos');
  });

  it('debe mostrar la columna multado en la tabla de prestamos', () => {
    cy.get('table th').should('contain', 'Multado');
    cy.get('table td').then(($tds) => {
      const texto = $tds.text();
      expect(texto.includes('true') || texto.includes('false')).to.equal(true);
    });
  });

  it('debe solicitar el monto de la multa al abrir un prestamo', () => {
    cy.get('table tbody tr').first().click();
    cy.wait('@montoMultaReq').its('response.statusCode').should('eq', 200);
  });

  it('debe mostrar el monto de la multa cuando el prestamo esta multado', () => {
    cy.contains('td', 'true').then(($cell) => {
      if ($cell.length > 0) {
        cy.wrap($cell).closest('tr').click();
        cy.get('.modal-container, .mat-mdc-dialog-container').should('be.visible');
        cy.get('body').then(($body) => {
          if ($body.find('.multa-info').length > 0) {
            cy.get('.multa-info').should('contain', 'Multa pendiente');
          }
        });
      }
    });
  });
});
