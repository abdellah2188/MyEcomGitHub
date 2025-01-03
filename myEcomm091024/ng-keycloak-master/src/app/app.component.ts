import { Component } from '@angular/core';
import { KeycloakProfile } from 'keycloak-js';
import { AuthService } from './auth/service/auth.service';
import {Router} from "@angular/router";
import {CatalogueService} from "./services/catalogue.service";
import {CaddyService} from "./services/caddy.service";
import {CustomerService} from "./services/customer.service";
import {AuthGuard} from "./auth/auth.guard";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'ng-keycloak';
  public loggedIn: boolean = false;
  public userProfile: KeycloakProfile = {};
  public currentCategorie: any;
  public categories: any;
  public customers: any;
  private log!: boolean;
  private cat!: any ;

  constructor(private  router:Router, public authService: AuthService, public catService:CatalogueService,
              public caddyService: CaddyService, private customerService: CustomerService, authgard: AuthGuard) {}

  async ngOnInit(): Promise<void> {
    this.getCategories();

    //console.log("VVV", (<boolean> await this.authService.isLogged()));

    if( <boolean> await this.authService.isLogged()) {

      console.log("QQSS", this.authService.isLogged());
      this.caddyService.loadCaddyFromLocalStorage();

      this.loggedIn! = <boolean> await this.authService.isLogged();
    }
    /*if (this.loggedIn) {
        //this.userProfile = await this.authService.loadUserProfile();
    }*/
  }

  public login(): void {
    this.authService.login();
  }

  public logout(): void {
    this.authService.logout();
  }
  managedAccount() {
    this.authService.manageAccount();
  }

  onSelectedProducts() {
    this.currentCategorie=undefined;
    this.router.navigateByUrl("/products/1/0");
  }

  onProductsPromo() {
    this.currentCategorie=undefined;
    this.router.navigateByUrl("/products/3/0");
  }

  onProductsDispo() {
    this.currentCategorie=undefined;
    this.router.navigateByUrl("/products/4/0");
  }

  onSearch(value: string){
    console.log("AAZZ", value);
    this.currentCategorie=undefined;
    this.router.navigateByUrl("/products/5/"+value);
  }

  onAddProduct(){
    this.currentCategorie=undefined;
    this.router.navigateByUrl("/product/product");
  }

  getProductsByCat(c:any) {
	
    this.currentCategorie=c;
    console.log("NNNNY", c);
    const id=c.id;
    const nm=c.name;
    this.cat={id:c.id, name:c.name};
    //this.router.navigateByUrl('/products/2/'+c.id);
	
    this.router.navigateByUrl('/products/2/'+ btoa(JSON.stringify(this.cat)));
   // this.router.navigateByUrl('/products/'+ btoa('2')+'/' +btoa(JSON.stringify(this.cat)));

  }

  private getCategories() {
    this.catService.getResource( this.catService.host + "/categories")
      .subscribe(data=>{
        this.categories=data;

      },err=>{
        console.log(err);
      })
  }

  public getCustomers() {
    this.customerService.getCustomers()
      .subscribe(data=>{
        this.customers=data;
        console.log("LLLLLLLLL", this.customers);
      },err=>{
        console.log(err);
      })
  }

}
