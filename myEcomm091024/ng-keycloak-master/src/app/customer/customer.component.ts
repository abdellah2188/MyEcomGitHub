import { Component, OnInit } from '@angular/core';
import {OrderService} from '../services/order.service';
//import {AuthenticationService} from '../services/authentication.service';
import {CaddyService} from '../services/caddy.service';
import {Router} from '@angular/router';
import {Order} from "../model/Order.model";
import {CustomerService} from "../services/customer.service";
import {AuthService} from "../auth/service/auth.service";
import {Customer} from "../model/customer.model";
import {Observable} from "rxjs";
import {tap} from "rxjs/operators";

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {
  public mode:number=0;

  panelStyle:string= "panel-default";
  private myData: any;
  public customers: any;
  private loggedIn: boolean= false;
  public mobile: any;
  public adress: any;
  constructor(public customerService:CustomerService,public orderService:OrderService,
              public authService:AuthService,
              public caddyService:CaddyService,
              private router:Router) {

  }

  async ngOnInit() {
    this.loggedIn = await this.authService.isLogged();
  //  console.log("MMMMMMMMMMMQQQQX", this.authService.loadUserProfile());

    if (!this.loggedIn) {
      this.authService.login();
    } else {
    // console.log("WWWWWWWWx",  this.authService.getUserInfos()!.attributes.mobile[0]!);
   //  this.mobile= this.authService.getUserInfos()!.attributes.mobile[0]!
   //  this.adress= this.authService.getUserInfos()!.attributes.adress[0]!
      console.log("MMMMMMMMMMMMXX");

      //this.router.navigateByUrl('/login');
      this.customerService.getCustomers().subscribe(
        data => {
          console.log("MMMMMMMMMMMM", data);
          this.customers = data;
        },
        err => {
          console.log('errorrr ! ', err)
        }
      );
    }
  }
  onSaveCustomer(customer:Customer) {
    this.orderService.order.id= null;
   // customer.username=this.authService.userName;
    this.orderService.setCustomer(customer);
    this.caddyService.setCustomer(customer);
    this.orderService.loadProductsFromCaddy();
    this.mode=1;
  }

  onOrder() {

    this.orderService.order.id=null;
    this.orderService.submitOrder().subscribe(data=>{
      this.myData= data;
      console.log("NNNNNNNNNNN", this.myData);

      this.orderService.order.id=this.myData.id;
      this.orderService.order.date=this.myData.date;
      this.panelStyle="panel-success";

    },err=>{
      console.log(err);
    });
  }

  onPayOrder() {
    console.log("RRRR", this.orderService.order.id);
    this.router.navigateByUrl("/payment/"+this.orderService.order.id);
  }
}
