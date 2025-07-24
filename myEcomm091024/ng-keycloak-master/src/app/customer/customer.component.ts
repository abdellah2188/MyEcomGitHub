
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
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css'],
  standalone: false
})
export class CustomerComponent implements OnInit {
  public mode:number=0;
  public mode3:number=0;
  
  panelStyle:string= "panel-default";
  private myData: any;
  public customers: any;
  private loggedIn: boolean= false;
  public mobile: any;
  public adress: any;
  constructor(public  route:ActivatedRoute, private customerService:CustomerService,public orderService:OrderService,
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
		
	//	let userDetails = await this.keycloakService.loadUserProfile();
	//	console.log("TTTTTTT", userDetails.attributes);
		
     console.log("WWWWWWWWx",  this.authService);
   //  this.mobile= this.authService.getUserInfos()!.attributes.mobile[0]!
   //  this.adress= this.authService.getUserInfos()!.attributes.adress[0]!
      console.log("MMMMMMMMMMMMZz");

      //this.router.navigateByUrl('/login');
	  let mode3=(this.route.snapshot.params.mode);
	      console.log('hhhhhhh', mode3);
	  if (mode3==3){
	      this.customerService.getCustomers().subscribe(
	        data => {
	          console.log("MMMMMMMMMMMMbb", data);
	          this.customers = data;
			 this.mode3=3;
			 this.mode=3;
	        },
	        err => {
	          console.log('errorrr ! ', err)
	        }
	      );
	  }
	} 	
  }
  
  /*public getCustomers() {
      this.customerService.getCustomers()
        .subscribe(data=>{
          this.customers=data;
          console.log("LLLLLLLLL", this.customers);
        },err=>{
          console.log(err);
        })
  }*/
	
  onSaveCustomer(customer:Customer) {
    this.orderService.order.id= null;
    customer.username=this.authService.username;
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
