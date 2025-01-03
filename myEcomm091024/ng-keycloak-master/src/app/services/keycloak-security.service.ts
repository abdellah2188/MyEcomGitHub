import { Injectable } from '@angular/core';
import { KeycloakInstance } from 'keycloak-js';
declare var Keycloak: any;

@Injectable({
  providedIn: 'root'
})
export class KeycloakSecurityService {

   public kc: KeycloakInstance | undefined;
  // public keycloak: Keyc;

  constructor() { }

  public  init() {
    console.log('INIT : Service keycloak security1 ');

    return new Promise((resolve, reject) => {
      console.log('INIT : Service keycloak security2 ');
      this.kc= new Keycloak({
        url: 'http://localhost:8180',
        realm: 'microservices-realm',
        clientId: 'controle-jee-client'
      });
      this.kc?.init({
         onLoad: 'login-required'
      //  onLoad: 'check-sso'
        //promiseType: `native`
      }).then((authenticated) => {
         console.log('authenticated', authenticated);
         console.log('token: ', this.kc?.token);
        resolve({ auth:authenticated, token: this.kc?.token })
      }).catch(err => {
        reject(err);
      });
   });

  }

  /*   public async init() {
      console.log('INIT : Service keycloak security ');
      this.kc = new Keycloak({
        url: 'http://localhost:8180',
        realm: 'microservices-realm',
        clientId: 'myEcomFront'
      });
      await this.kc?.init({
         onLoad: 'login-required'
       // onLoad: 'check-sso'
        // promiseType: 'native'

      })
      console.log('token: ', this.kc?.token)
    }*/
}
