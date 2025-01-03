import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile, KeycloakTokenParsed } from 'keycloak-js';
import { from, Observable } from 'rxjs';

@Injectable()
export class AuthService {
  private keycloak: | undefined;
  private userDetails: any;
  public firstName: any;
  public lastName: any;
  public email: any;
  public mobile: any;
  public adress: any;
  public userName: any;
  public token!: string;

  constructor(private keycloakService: KeycloakService) {
    this.getUserInfos();
    this.getLoggedUser();

    const token = this.keycloakService.getKeycloakInstance().idToken;
   // console.log("EEEEX", token!);
    window.sessionStorage.setItem('token', JSON.stringify(token));
  //  const token = window.sessionStorage.getItem('token');
   // console.log("DDDDX", token);
    //this.loadUserProfile();
    this.loadProfile().then(user => {
      //console.log("IIIII", user.attributes.mobile['0']);
      this.userName= user.username;
      this.firstName    = user.firstName;
	  this.lastName     = user.lastName
     // console.log("IIIII", this.name);
      this.mobile  = user.attributes.mobile['0'];
      this.adress  = user.attributes.adress['0']
      this.email   = user.email;
    })
  }
  loadProfile(): Promise<any>{
    return new Promise<any>(async (resolve, reject) => {
      if (await this.isLogged()) {
        this.keycloakService.loadUserProfile()
          .then(data => resolve(data))
          .catch(error => console.log(error))
      } else {
        console.log('user not logged in')
      }
    })
  }

  hasRoleIn(roles: Array<string>): boolean {
    let userRoles = this.keycloakService.getUserRoles();
    //console.log("RRRRR", userRoles);
    for (let role of roles) {
      if (userRoles.includes(role)) return true;
    }
    return false;
  }
  public getLoggedUser(): KeycloakTokenParsed | undefined {
    try {
      const userDetails: KeycloakTokenParsed | undefined = this.keycloakService.getKeycloakInstance();
     // console.log( "UUUUUUUUUUUUUUUU", userDetails);
      return userDetails;
    } catch (e) {
      console.error("Exception", e);
      return undefined;
    }
  }

  public isLogged() {
   // console.log("fffffffffffff", this.keycloakService.isLoggedIn());
    return this.keycloakService.isLoggedIn();
  }

  /*public loadUserProfile() : Promise<KeycloakProfile> {
    console.log("PPPPPPx", this.keycloakService.getUsername());
    return this.keycloakService.loadUserProfile();
  }*/
  public getUserInfos() {

    /*this.keycloakService.loadUserProfile().then(profile => {
      this.userDetails= profile;
      return profile;
    })*/
   // console.log(this.userDetails, "PPPPPPyy",  this.keycloakService.getKeycloakInstance().idToken);

    return this.userDetails;

  }

  public login() : void {
    this.keycloakService.login();
  }

  public logout() : void {
   // console.log("AAAAAAAA", this.keycloakService.getUserRoles());
    this.keycloakService.logout(window.location.origin);
  }

  manageAccount() {
    this.keycloakService.getKeycloakInstance().accountManagement()
  }

  public redirectToProfile(): void {
    this.keycloakService.getKeycloakInstance().accountManagement();
  }

  public getRoles(): string[] {

    //console.log("AAAAAAAA", this.keycloakService.getUserRoles());
    return this.keycloakService.getUserRoles();
  }

}
