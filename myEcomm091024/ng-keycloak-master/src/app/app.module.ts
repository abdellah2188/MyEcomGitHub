import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthModule } from './auth/auth.module';
import {CatalogueService} from "./services/catalogue.service";
import { provideHttpClient, withInterceptorsFromDi } from "@angular/common/http";
import {CustomerComponent} from "./customer/customer.component";
import {PaymentComponent} from "./payment/payment.component";
import {CaddyComponent} from "./caddy/caddy.component";
import {ProductComponent} from "./product/product.component" ;
import {ProductsComponent} from "./products/products.component" ;
import {FormsModule} from "@angular/forms";
import {NgxCurrencyModule} from "ngx-currency";

@NgModule({ declarations: [
        AppComponent, CustomerComponent, PaymentComponent, CaddyComponent, ProductComponent, ProductsComponent
    ],
    bootstrap: [AppComponent], imports: [BrowserModule,
        AppRoutingModule,
        AuthModule, FormsModule], providers: [CatalogueService, provideHttpClient(withInterceptorsFromDi())] })
export class AppModule { }
