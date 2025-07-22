import { NgModule, inject, provideAppInitializer } from '@angular/core';
import { KeycloakService, KeycloakAngularModule } from 'keycloak-angular';
import { AuthGuard } from './auth.guard';
import { initializer } from './keycloak-initializer';
import { AuthService } from './service/auth.service';
import { HTTP_INTERCEPTORS } from "@angular/common/http";
import {RequestInterceptor} from "../services/request-interceptor";
@NgModule({
  declarations: [],
  imports: [KeycloakAngularModule],
  providers: [  provideAppInitializer(() => {
        const initializerFn = (initializer)(inject(KeycloakService));
        return initializerFn();
      }),
    AuthGuard,
    AuthService
    //,{ provide: HTTP_INTERCEPTORS, useClass: RequestInterceptor, multi: true }
  ]
})
export class AuthModule { }
