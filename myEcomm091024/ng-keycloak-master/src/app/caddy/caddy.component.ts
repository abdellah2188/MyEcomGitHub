import { Component, OnInit } from '@angular/core';
import {CatalogueService} from '../services/catalogue.service';
import {Product} from '../model/product.model';
import {Router} from '@angular/router';
import {ItemProduct} from '../model/item-product.model';
import {Caddy} from '../model/caddy.model';
import {AuthService} from "../auth/service/auth.service";
import { CaddyService } from '../services/caddy.service';

@Component({
  selector: 'app-caddy',
  templateUrl: './caddy.component.html',
  styleUrls: ['./caddy.component.css']
})
export class CaddyComponent implements OnInit {
  public px: number=0;
  public qt: number=0;

  public caddy!: Caddy;
  public nom: any;
  private loggedIn: any;

  constructor(private catService:CatalogueService, private router:Router,
              public caddyService:CaddyService, private authService:AuthService) { }

  ngOnInit(){
    this.caddy = this.caddyService.getCurrentCaddy();
  }
  /*async ngOnInit() {
    this.loggedIn = await this.authService.isLogged();

    if (!this.loggedIn) {
      this.authService.login();
    } else {
      this.caddy = this.caddyService.getCurrentCaddy();
    }
  }*/
  calculate(p:any ,q: any){
    console.log("VB", p*q);
    return p * q;
  }
  onRemoveProductFromCaddy(p: ItemProduct) {
    this.caddyService.removeProduct(p.product?.id);
  }

  getTotal() {
      return this.caddyService.getTotalCurrentCaddy();
  }
  getTotalP() {
    let t:number=0;
    let qtt = document.getElementsByName("qt");
    console.log("NNMM", qtt)

    let px = document.getElementById("px");
    let qt = document.getElementById("qt");

    console.log("JKJK",px, "rr", qt?.innerHTML);
    let p: any= px?.innerHTML;
    let q: any= qt?.innerHTML;
    if(p && q){
         t= p * q ;
    }
    console.log("EEEEEEEEE", t)

    return t;
  }

  onNewOrder() {
    this.router.navigateByUrl("/customer");
  }

  onAddCaddy() {

    let size=this.caddyService.listCaddies.length;
    let index:number=this.caddyService.listCaddies[size-1].num;
    this.caddyService.addNewCaddy({num:index+1,name:"Caddy"+(index+1)});
  }

  onSelectCaddy(c: { num: number; name: string }) {
    //console.log("QQQQ", c.num,"QQQ", c.name);
    this.caddyService.currentCaddyName=c.name;
//    if(this.caddyService.currentCaddyName=="Caddy1"){
    if(this.caddyService.currentCaddyName!=null){

        this.caddy=this.caddyService.getCurrentCaddy();
    }else{
      this.caddy=  new Caddy(this.caddyService.currentCaddyName);

    }
    console.log(this.caddyService.currentCaddyName, "OOOOOO", this.caddy);
  }
}
