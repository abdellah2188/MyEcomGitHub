import { Component, OnInit } from '@angular/core';
import {CatalogueService} from '../services/catalogue.service';
import {ActivatedRoute, NavigationEnd, NavigationStart, Router} from '@angular/router';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import {Product} from '../model/product.model';
import {CaddyService} from '../services/caddy.service';
import {AuthService} from "../auth/service/auth.service";
import { ProductService } from '../services/product.service';
import { Category } from '../model/category.model';
//import {KeycloakSecurityService} from "../services/keycloak-security.service";
@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css'],
  standalone: false
})

export class ProductsComponent implements OnInit {

   public products: any;
   editPhoto: boolean | undefined;
   currentProduct: any;
   selectedFiles: { item: (arg0: number) => any; } | undefined;
   progress: number | undefined;
   currentFileUpload: any;
   title: string | undefined;
   currentRequest: string | undefined;
  private currentTime: number=0;
  public hasRoleA: boolean=false ;
  public hasRoleU: boolean=false;
  //private roles: string[];
 // private keycloak: any;
  private cat!: any;
  private cat2!: any;
  public host: any;
  public size=1;
  public category: Category | undefined;
  constructor(
    public productService: ProductService,
    public catService:CatalogueService,
    private route:ActivatedRoute, private router:Router,
    public caddyService:CaddyService,
    public authService:AuthService) { }

  ngOnInit() {
    this.host= this.productService.host;
   // console.log("SSS", this.host);
  //  this.keycloak = this.keycloakSecurityService.kc;

   // console.log("ZZZZzerty", this.route.snapshot.url);
    this.router.events.subscribe((val) => {
     // console.log("WWWWW", this.route.snapshot.url);

      if (val instanceof NavigationEnd ) {
        let url = val;
      //  console.log('xxxxxxxxxxxxxxxxxxxxx', url);
        this.getNavigation();
      }
    });

    this.getNavigation()
  }
  public getNavigation(){
   // console.log('hhhhhhh');

    let p1=(this.route.snapshot.params.p1);
    console.log('hhhhhhh', p1);

    if(p1==1){
      this.title="Selected products";
      this.currentRequest='/api/product/products/search/selectedProducts';
      this.getProducts({url: this.currentRequest});
    }
    else if (p1==2){
     console.log("AAAAAQQQ");

      this.cat=this.route.snapshot.params.p2;

      this.category= this.cat ;
      console.log("QQQQQQQQQQQQ", (this.category));
      //console.log("QQQQQQQQQQQQ22223333", atob(this.category));

      this.cat2= (JSON.parse(atob(this.cat)));
      console.log("TTTT", this.cat2, "TTT", this.cat2.id);

      this.title= this.cat2.name;

     // this.currentRequest='/categories/'+this.cat2.id+'/products';
      this.currentRequest='/api/product/productsByCategory/'+this.cat2.id; ;

	  console.log("LLL", this.currentRequest);
     this.getProducts({url: this.currentRequest});
    }
    else if (p1==3){
      this.title="Promotional products";
      this.currentRequest='/api/product/products/search/promoProducts';
      this.getProducts({url: this.currentRequest});
    }
    else if (p1==4){
      this.title="Availables products";
      this.currentRequest='/api/product/products/search/dispoProducts';
      this.getProducts({url: this.currentRequest});
    }
    else if (p1==5){
      this.title="Recherche..";
      let mc=this.route.snapshot.params.p2;
      // console.log("MMCC", mc);
      this.currentRequest='/products/search/productsByKeyword?mc='+mc;
      this.getProducts({url: this.currentRequest});
    }
  }

  private getProducts({url}: { url: any }) {
	  console.log("IIIIIIIIIIIIIII", this.catService.host+url);

	  this.products=null;
    //this.catService.getResource(this.catService.host+url)
    this.catService.getResource(this.catService.host+url)
      .subscribe(data=>{
        this.products=data;
        console.log("PPPPPPPPPPPPPPPPxxxxxxxxxxxxxxxxxxx", this.products);
      },err=>{
        console.log(err);
      })
  }
  public refreshUpdatedProduct() {
    console.log("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY00000");
    console.log("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYxx", this.currentProduct);
   // this.catService.getResource(this.currentProduct._links.self.href)
      this.catService.getResource(this.currentProduct)
      .subscribe(data=>{
        console.log("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY-1-1-1", data);
        // @ts-ignore
        this.currentProduct.photoName=data['photoName'];
      },err=>{
        console.log(err);
      })
  }

  onEditPhoto(p: object) {
    this.currentProduct=p;
    this.editPhoto=true;
  }

  onSelectedFile(event: any) {
    this.selectedFiles=event.target.files;
  }

  uploadPhoto() {
    this.progress = 0;
    this.currentFileUpload = this.selectedFiles?.item(0)
    this.catService.uploadPhotoProduct(this.currentFileUpload, this.currentProduct.id).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        console.log("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY1111", this.currentProduct);
         if (event.total) {
            console.log("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY22222", this.currentProduct);
            const total: number = event.total;
            this.progress = Math.round(100 * event.loaded / total);

		        this.refreshUpdatedProduct();
         }
      } else if (event instanceof HttpResponse) {
        console.log("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY33333", this.currentRequest);
        //console.log(this.router.url);
		     this.refreshUpdatedProduct();
         this.getProducts({url: this.currentRequest});
       
         this.currentTime=Date.now();
      }
    },err=>{
      alert("Loading problem ...");
    })



    this.selectedFiles = undefined
  }

  onAddProductToCaddy(p:Product) {
    if(!this.authService.isLogged()){
      this.authService.login();
    }
    else{
      this.size= this.size + p.quantity;
      this.caddyService.addProduct(p);
    }
  }

  getTS() {
    return this.currentTime;
  }

  onProductDetails(p:Product) {
    console.log("PPPPPPP", p);
    //    let url= btoa(p._links.product.href);
    let url=btoa(this.host+"/api/product/products/"+p.id);
    console.log("ppppppp", url);
   // console.log(p._links.category,"BBBBBBBBBBBBBBB", p._links.product.href);

    this.router.navigateByUrl("/product/"+url);
    //  this.router.navigateByUrl(url);
	  this.refreshUpdatedProduct();
  }

  /*hasRoleUser(){
    this.hasRoleU = false;
    if(this.authService.isUser()){
      this.hasRoleU = true;
     // return hasRoleUser;
    }
  }

  public hasRoleAdmin(){
     this.hasRoleA = false;

    if(this.authService.isAdmin()){
      this.hasRoleA = true;
     // return hasRole;
    }
  }*/


  /*isAdmin() {
    this.keycloak!=this.keycloakSecurityService.kc;
    return this.keycloak!.hasRealmRole!('ADMIN');
  }*/
}

