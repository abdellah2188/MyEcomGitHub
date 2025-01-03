import {Customer} from '../model/customer.model';
import {ItemProduct} from '../model/item-product.model';
import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {CatalogueService} from './catalogue.service';
import {Order} from '../model/Order.model';
import {Observable} from 'rxjs';
import { CaddyService } from './caddy.service';
@Injectable({
  providedIn:'root'
})
export class OrderService {

  public host:string="http://localhost:8080/order-serviceb/api/order";

  public order:Order=new Order();

  constructor(private caddyService:CaddyService,
              private httpClient:HttpClient,
              private catalService:CatalogueService){}

  public setCustomer(customer:Customer){
    this.order.customer=customer;
  }
  public loadProductsFromCaddy(){
    this.order.products=[];
    /*for(let key in this.caddyService.getCurrentCaddy().items){
      console.log("IIIII", key);
     this.order.products.push(this.caddyService?.getCurrentCaddy()?.items?[key]);
    }*/
   for(let p of this.caddyService!.getCurrentCaddy().items.values()!){
      console.log("IIIII",p.product!);
     this.order.products.push(p);
   }
  }
  public getTotal():number{
    let total:number=0;
    this.order.products?.forEach(pi=>{
      let p=pi.price;
      let q=pi.quantity;
      if(p && q){
        total+=p*q ;
        }
    });
    return total;
  }

  submitOrder() {
    console.log("OOORRRR", this.order);
    return this.httpClient.post(this.host+"/add",this.order);
  }

  public getOrder(id:number):Observable<Order>{
    return this.httpClient.get<Order>(this.host+"/"+id);
  }
}
