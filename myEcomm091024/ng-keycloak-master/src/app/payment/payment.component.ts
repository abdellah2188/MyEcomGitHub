import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Order} from '../model/Order.model';
import {OrderService} from '../services/order.service';
import {PaymentService} from "../services/payment.service";
import {Timestamp} from "rxjs/internal-compatibility";

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {
  public paymentAmount?:number;
  public paymentDate?: Date;
  public currentOrder: Order | undefined;
  public mode: number | undefined;
  public pay: boolean=false;

  constructor(private router:Router, private route:ActivatedRoute,
              public orderService:OrderService, private paymentService: PaymentService) { }

  ngOnInit() {
    this.paymentDate= new Date();

    let id=this.route.snapshot.params.orderID
    console.log("QQQQQQQQQQQQX", id);

    this.orderService.getOrder(id).subscribe(data=>{
      this.currentOrder=data;
      console.log("QQQQQQQQQQQQD", this.currentOrder);

    },err=>{
      console.log(err);
    })
  }

  /*onPayOrder({data}: { data: any }) {
    console.log("DDDDDDDDDMMMMMMMM", data);
    this.paymentService.addPayment({data: data}).subscribe(
      data =>{
        console.log("KKKKKKXXX", data);
        this.pay=true;
      alert("Se Agrego con Exito...!!!");

    })
  }*/
  onPayOrder({data}: { data: any }, {customer}: {customer: any}, {order}: {order: any}, {orderItems}: {orderItems: any}) {
    console.log("DDD", data, "DDD", customer,"DDD", order,"DDD", orderItems);
    this.paymentService.addPayment({data: data}).subscribe(
      data =>{
        console.log("KKKKKKXXX", data);
        this.pay=true;
        alert("Se Agrego con Exito...!!!");

      })
  }
  /* Guardar(persona: Persona){
    this.service.createPersona(persona)
      .subscribe(data=>{
        alert("Se Agrego con Exito...!!!");
        this.router.navigate(["listar"]);
      })
  }*/
  onOrder() {
    console.log("VVVVVVVVVVVVVVVVV");

  }
}
