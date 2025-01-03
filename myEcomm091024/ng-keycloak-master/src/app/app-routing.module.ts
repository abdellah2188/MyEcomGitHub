import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';
import {CustomerComponent} from "./customer/customer.component";
import {ProductsComponent} from "./products/products.component";
import {PaymentComponent} from "./payment/payment.component";
import {CaddyComponent} from "./caddy/caddy.component";
import {ProductComponent} from "./product/product.component";

//const p2=atob('p2');
const routes: Routes = [

 // {path:'products/:p1/:p2',component:ProductsComponent},
  {path:'products/:p1/:p2',component:ProductsComponent},

  {path:'',redirectTo:'products/1/0',pathMatch:'full'},
/*
  {path:'login', component:LoginComponent},
*/
  {path:'caddy', component:CaddyComponent,
    /*canActivate: [AuthGuard],
    data: { roles: ["USER"] }*/
  },

  {
    path: 'customer', component: CustomerComponent,
      canActivate: [AuthGuard],
     data: { roles: ["USER"] }
  },
/*
  {path:'customer', component:CustomerComponent},
*/
  {path:'product/:url', component:ProductComponent},
  {path:'product/product', component:ProductComponent},
  {path:'payment/:orderID', component:PaymentComponent,
    canActivate: [AuthGuard],
    data: { roles: ["USER"] }}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
