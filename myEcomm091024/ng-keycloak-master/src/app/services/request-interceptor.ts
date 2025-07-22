import { Injectable } from '@angular/core';
import { KeycloakSecurityService } from './keycloak-security.service';
import { HttpRequest, HttpHandler, HttpParams, HttpInterceptor, HttpEvent } from '@angular/common/http';
import { take, exhaustMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { Observable } from 'rxjs';
import {AuthService} from "../auth/service/auth.service";

@Injectable({
  providedIn: 'root'
})
export class RequestInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = (window.sessionStorage.getItem('token'));

    console.log( "BBBBBBBBBBBBBCC", token);

    const modifiedReq = req.clone(
          {
              setHeaders: {
                Authorization: 'Bearer ' + token
              }
            }
          );
          console.log( "BBBBBBBBBBBBBNNN", (modifiedReq));

          return next.handle(modifiedReq);


  }
}
