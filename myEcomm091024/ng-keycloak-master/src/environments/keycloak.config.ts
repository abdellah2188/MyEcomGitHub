/**
 * Here you can add the configuration related to keycloak
 * So we can use this common config for diffrent environment
 */
import { KeycloakConfig } from 'keycloak-js';

const keycloakConfig: KeycloakConfig = {
  //url: 'http://localhost:8180',
  url: 'http://localhost:7080',
  realm: 'microservices-realm',
  clientId: 'controle-jee-client',
};

export default keycloakConfig;
