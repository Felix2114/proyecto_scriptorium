// Comandos personalizados de Cypress para Scriptorium

/**
 * Inicia sesión como bibliotecario y almacena la sesión
 * para reutilizarla en tests que requieran autenticación previa.
 */
Cypress.Commands.add('loginBibliotecario', (usuario: string, contrasena: string) => {
  cy.session([usuario, contrasena], () => {
    cy.visit('/registro/login');
    cy.get('[data-cy=input-usuario]').type(usuario, { force: true });
    cy.get('[data-cy=input-contrasena]').type(contrasena, { force: true });
    cy.get('[data-cy=btn-login]').click({ force: true });
    cy.url().should('include', '/biblioteca');
  });
});

declare global {
  namespace Cypress {
    interface Chainable {
      loginBibliotecario(usuario: string, contrasena: string): Chainable<void>;
    }
  }
}
