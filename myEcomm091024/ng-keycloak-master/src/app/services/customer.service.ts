import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
//import { KeycloakSecurityService } from './keycloak-security.service';
import { throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import {Customer} from "../model/customer.model";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }
//  constructor(private http: HttpClient) { }


  public getCustomers() {
    console.log("KKKKK");
    return this.http.get<Customer[]>("http://localhost:8080/customer-service/api/customer")
   // return this.http.get<Product[]>('http://localhost:8080/api/product', {headers: new HttpHeaders({'Authorization':'Bearer '+this.keycloakSecurityService.kc?.token})})
      .pipe(
        catchError(this.handleError),
        // tap(data => console.log('data', data))
      );
  }

  private handleError(errorRes: HttpErrorResponse) {
    console.log('errorRes', errorRes)
    let errorMessage = 'an unknown error occured';
    if (!errorRes.error || !errorRes.error.error || !errorRes.error.message) {
      return throwError(errorMessage);
    }
    switch (errorRes.error.message) {
      case 'Forbidden':
        errorMessage = 'Vous n\'avez pas les droits ! ou vous n\'êtes pas logger';
        break;
      case 'EMAIL_NOT_FOUND':
        errorMessage = 'this Email does not exist';
        break;
      case 'INVALID_PASSWORD':
        errorMessage = 'this password is not correct';
        break;

    }
    return throwError(errorMessage);
  }
}
