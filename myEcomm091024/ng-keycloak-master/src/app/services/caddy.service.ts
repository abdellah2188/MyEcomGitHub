import {ItemProduct} from '../model/item-product.model';
import {Injectable} from '@angular/core';
import {Product} from '../model/product.model';
import {AuthenticationService} from './authentication.service';
import {Caddy} from '../model/caddy.model';
import {Customer} from '../model/customer.model';
import {AuthService} from "../auth/service/auth.service";

@Injectable({
  providedIn: 'root'
})
export  class CaddyService{

   public currentCaddyName:string="Caddy1";
   public listCaddies:Array<{num:number,name:string}>=[{num:1,name:'Caddy1'}];
   public caddies:Map<string,Caddy>=new Map()  ;
   public customer?: Customer;
  //private loggedIn: boolean=false;
  //private loggedIn =  this.authService.isLoggedIn();
  //private userInfos: any;
  private compt: number=0;

  constructor(private authService: AuthService, private authServiceX: AuthenticationService) {

    let caddies = localStorage.getItem("caddy");

    if (caddies) {
      this.caddies = JSON.parse(caddies);
    } else {
      let caddy = new Caddy(this.currentCaddyName);
      this.caddies.set(this.currentCaddyName, caddy);
    }
    // console.log("BBBB", this.caddies);
    //   if(this.authService.isAuthenticated()) {
   // if (!this.loggedIn) {
    if  (!this.authService.isLogged()){
      let caddy = new Caddy(this.currentCaddyName);
    } else {
      let caddy = new Caddy(this.currentCaddyName);
    }
  }
  public addProductToCaddy(product: Product): void{
   // console.log(this.currentCaddyName,"222222", product);

    let caddy=this.caddies.get(this.currentCaddyName);
    if(!caddy){
      let caddy= null;
       caddy = new Caddy(this.currentCaddyName);
      this.caddies.set(this.currentCaddyName, caddy);
       caddy=this.caddies.get(this.currentCaddyName);
       console.log("8888888888888888888888888888888888888",caddy);
    }
   // console.log("888",caddy);
    if(caddy)  {
      console.log("999999999999999999999999999999999999999");

      let itemProduct = caddy.items.get(product.id);
      if(itemProduct) {
        console.log("3333333333");
        if(product.quantity&&itemProduct.quantity){
        itemProduct.quantity+=product.quantity;
        }
      }
      else {
        console.log("55555555", product);

        itemProduct=new ItemProduct();
        itemProduct.price=product.price;
        itemProduct.quantity=product.quantity;
        itemProduct.product=product;
        caddy.items.set(product.id, itemProduct);
        //console.log("6666666", caddy)
        //caddy.items[product.id]=itemProduct;
        this.saveCaddy();
      }
     }else{
        let caddy= null;
        caddy = new Caddy(this.currentCaddyName);
        this.caddies.set(this.currentCaddyName, caddy);
        caddy=this.caddies.get(this.currentCaddyName);
       // console.log("888",caddy);
        console.log("55555555", product);
        let itemProduct = caddy?.items.get(product.id);
        if(itemProduct) {
          //console.log("3333333333");
          if(product.quantity&&itemProduct.quantity){
            itemProduct.quantity+=product.quantity;
          }
        }
        else {
          console.log("55555555", product);

          itemProduct=new ItemProduct();
          itemProduct.price=product.price;
          itemProduct.quantity=product.quantity;
          itemProduct.product=product;
          caddy?.items.set(product.id, itemProduct);
          //console.log("6666666", caddy)
          //caddy.items[product.id]=itemProduct;
          this.saveCaddy();
        }
    }
  }

  public getCurrentCaddy(){
  //  this.compt= this.compt + 1
   // console.log(this.compt, "CCCCCC");

  //  if (this.compt>5) {alert ("Stoppp"); return  ;}

    if(this.currentCaddyName!=null)   {
      let caddy=this.caddies.get(this.currentCaddyName);
      // console.log(this.currentCaddyName, "JJJJJJJJJ", caddy, new Caddy(this.currentCaddyName));
       if(caddy)  {
        // caddy.client?.email!='';
        // caddy.client?.name!='';
         //caddy.client?.phoneNumber!='';


         //console.log("KKK", caddy);

         return  caddy;
       }
       else return new Caddy(this.currentCaddyName)
    }
    else   {
      let caddy= new Caddy(this.currentCaddyName);
      this.caddies.set(this.currentCaddyName, caddy);
     // caddy= this.caddies.get(this.currentCaddyName);

     // console.log(this.currentCaddyName, "GGGGGGGG", caddy);
      return  caddy;
    }
   // if(caddy) {return caddy;}

  }
  public removeProduct(id: number | undefined):void{
    let caddy=this.caddies.get(this.currentCaddyName);
    if (caddy) {
      if(id) {
     // console.log(id,"RRRRRRRRRRRRRRRRRRRR", caddy.items.get(id));
       caddy.items.delete(id);
      }
    }
    //if(caddy && id) delete caddy.items[id];
     this.saveCaddy();
  }

  public addProduct(product:Product){
    this.addProductToCaddy(product)
    this.saveCaddy();
  }

  public loadCaddyFromLocalStorage(){

    let myCaddiesList=localStorage.getItem("ListCaddies_"+this.authService.userName);

//    let myCaddiesList=localStorage.getItem("ListCaddies_"+this.authService.authenticatedUser.username);
       this.listCaddies=myCaddiesList==undefined?[{num:1,name:'Caddy1'}]:JSON.parse(myCaddiesList);
       this.listCaddies.forEach(c=>{
        // console.log("EEEEE", c);
         let cad=localStorage.getItem("myCaddy_"+this.authService.userName+"_"+c.name);

       //  let cad=localStorage.getItem("myCaddy_"+this.authService.authenticatedUser.username+"_"+c.name);
        // this.caddies?[c.name]=cad==undefined?new Caddy(c.name):JSON.parse(cad);
         //this.caddies?[c.name]=new Caddy(c.name);

       })
  }


  saveCaddy() {
    let caddy=this.caddies.get(this.currentCaddyName);
   // console.log("SSSSSSSS", caddy);
  //  localStorage.setItem("myCaddy_"+this.authService.authenticatedUser.username+"_"+this.currentCaddyName,JSON.stringify(caddy));
    localStorage.setItem("myCaddy",JSON.stringify(caddy));

  }

  getSize(){
    let caddy: any;
   // console.log("CCCCCCRRR", this.currentCaddyName);
    if(this.currentCaddyName!=null) {
    //if(this.currentCaddyName) {
      caddy=this.getCurrentCaddy();
      caddy = this.caddies.get(this.currentCaddyName);

      //console.log(caddy,"YYYYYY2222", this.caddies);

    }else{
      caddy = new Caddy(this.currentCaddyName);
      //this.caddies?[this.currentCaddyName]=caddy                     ;
      this.caddies.set(this.currentCaddyName, caddy);
      caddy = this.caddies.get(this.currentCaddyName);

      //console.log( this.currentCaddyName, "YYYY00000",caddy,"YYYYYY3333", this.caddies);

      //console.log("KKKKKKKKKK", Object.keys(caddy.items).length);
     // console.log(caddy.items.size, "HHHH222222222",caddy);
    }
    console.log("SSSSZZZZ", caddy.items);
    return caddy.items.size;
    //return Object.keys(caddy.items).length;
  }

  emptyCaddy(){
    this.caddies=new Map();
    this.listCaddies=[];
  }

  getTotalCurrentCaddy(): number {
    //let caddy=new Caddy(this.currentCaddyName);
    let caddy =this.caddies.get(this.currentCaddyName);
    let total:number=0;
    if(caddy) {
      for (let pi of caddy.items.values()) {
        let p=pi.price;
        let q=pi.quantity;
        if(p && q){
          total+= p* q;
        };
      }
    }
    return total;
  }
  /*getTotalCurrentCaddyP() {
    let caddy =this.caddies.get(this.currentCaddyName);
    console.log("LMLM", caddy?.items.);
    let total:number=0;
    if(caddy) {
      for (let pi of caddy.items.values()) {
        let p=pi.price;
        let q=pi.quantity;
        if(p && q){
          total+= p* q;
        };
      }
    }
    return total;
  }*/

  addNewCaddy(c: { num: number; name: string }) {
    this.listCaddies.push(c);

   // this.caddies[c.name]=new Caddy(c.name);
    localStorage.setItem("ListCaddies_"+this.authService.userName,JSON.stringify(this.listCaddies));
  //  localStorage.setItem("ListCaddies_"+this.authService.authenticatedUser.username,JSON.stringify(this.listCaddies));
  }

  setCustomer(customer: Customer) {
    this.getCurrentCaddy()!.customer!=customer;
    this.saveCaddy();
  }
}
