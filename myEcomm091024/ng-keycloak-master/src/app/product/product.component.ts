import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CatalogueService} from '../services/catalogue.service';
//import {AuthenticationService} from '../services/authentication.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import {Product} from '../model/product.model';
import {Category} from '../model/category.model';

import {CaddyService} from '../services/caddy.service';
import {ProductService} from "../services/product.service";
import {NgForm} from "@angular/forms";
import {ItemProduct} from "../model/item-product.model";
import {AuthService} from "../auth/service/auth.service";
//import { CheckboxModule } from 'primeng/checkbox';
@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  @ViewChild("form")
  form?: NgForm;
  //private product = new Product('','','', '', '', '', '', null, null, null);

  public currentProduct?: any;
  private selectedFiles: any;
  progress: number | undefined;
  currentFileUpload: any;
  public currentTime: number | undefined;
  public editPhoto: boolean | undefined;
  public mode: number=0;
  private product!: Product;
  public categories: any;
  public addFileUpload!: any;
  public host!: string;
  public url: any;
  public selectedCategory!: Category;

  constructor(private router:Router, private route:ActivatedRoute,
              public catalService:CatalogueService,
              //public authService:AuthenticationService,
              public authService:AuthService,

              public caddyService:CaddyService,
              private productService: ProductService) { }

  ngOnInit() {
    this.host= this.catalService.host;
    let url=atob(this.route.snapshot.params.url);

    let idProduct: string = url.substring(35, 50);

    console.log("SSSSSSSSSSSSSS",idProduct);
    this.url=this.host+"/products/"+idProduct;
    
    console.log(url, "UUUUUUUUUUUxx",  this.url);
    let pr = this.route.snapshot.params.url;
    console.log("RRLL", atob(pr));
    


    if(pr=="product") {
      this.mode = 2;
      console.log("YYYYYYYYYYYYYYYYY", this.mode);
      //console.log("LLRRLL", this.mode);
      //this.addProduct();
    }else {
      console.log("RXXXXXXXXXXXXXXXXX", this.mode);

      this.catalService.getProduct({url: this.url}).subscribe(
        data => {
          this.currentProduct = data;
              console.log("TTTTTTTTT", data.id);
        }, err => {
          console.log(err);
        })
    }

    this.getCategories();

  }

  private getCategories() {
    this.catalService.getResource(  "http://localhost:8080/product-serviceb/categories")
      .subscribe(data=>{
        this.categories=data;
        console.log("CCCCTTT", this.categories)
      },err=>{
        console.log(err);
      })
  }

  onEditPhoto({p}: { p: any }) {
    this.currentProduct=p;
    this.editPhoto=true;
  }

  onEditPhotoUp({p}: { p: any }) {
      this.currentProduct=p;
      this.editPhoto=true;
	  this.mode=1;
    }
  onSelectedFile({event}: { event: any }) {
    this.selectedFiles=event.target.files;
    this.addFileUpload = this.selectedFiles.item(0)
    console.log("FPFPPPPPPPPPPPPP", this.addFileUpload);

  }
  
  onSelectedFileUpdate({event}: { event: any }) {
      this.selectedFiles=event.target.files;
      this.addFileUpload = this.selectedFiles.item(0)
      console.log("FPFPPPPPPPPPPPPP", this.addFileUpload);

    }


  uploadPhoto() {
    this.progress = 0;
    console.log("YYY", this.selectedFiles.item(0));
    this.currentFileUpload = this.selectedFiles.item(0)
    this.catalService.uploadPhotoProduct(this.currentFileUpload, this.currentProduct?.id).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {

        this.progress = Math.round(100 * event.loaded / event.total!);
      } else if (event instanceof HttpResponse) {
        //console.log(this.router.url);
        //this.getProducts(this.currentRequest);
        //this.refreshUpdatedProduct();
        this.currentTime=Date.now();
        this.editPhoto=false;
      }
    },err=>{
      alert("Problème de chargement");
    })
    this.selectedFiles = undefined
  }
  
  uploadPhotoUp() {
      this.progress = 0;
      console.log(this.currentProduct, "YYY", this.selectedFiles.item(0));
      this.currentFileUpload = this.selectedFiles.item(0)
      this.catalService.uploadPhotoProduct(this.currentFileUpload, this.currentProduct?.id).subscribe(event => {
        if (event.type === HttpEventType.UploadProgress) {

          this.progress = Math.round(100 * event.loaded / event.total!);
        } else if (event instanceof HttpResponse) {
          //console.log(this.router.url);
          //this.getProducts(this.currentRequest);
          //this.refreshUpdatedProduct();
          this.currentTime=Date.now();
          this.editPhoto=false;
        }
      },err=>{
        alert("Problème de chargementUpppppp");
      })
      this.selectedFiles = undefined
    }

  uploadPhotoAdd() {
    this.progress = 0;
    console.log("HHHH", this.selectedFiles.item(0));
    this.currentFileUpload = this.selectedFiles.item(0)
    this.productService.uploadPhotoProduct(this.currentFileUpload).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {

        this.progress = Math.round(100 * event.loaded / event.total!);
      } else if (event instanceof HttpResponse) {
        //console.log(this.router.url);
        //this.getProducts(this.currentRequest);
        //this.refreshUpdatedProduct();
        this.currentTime=Date.now();
        this.editPhoto=false;
      }
    },err=>{
      alert("Problème de chargement Add");
    })
    this.selectedFiles = undefined
  }

  onAddProductToCaddy(p:Product) {
    if(!this.authService.isLogged()){
      this.router.navigateByUrl("/login");
    }
    else{
      this.caddyService.addProduct(p);
    }
  }

  getTS() {
    return this.currentTime;
  }

  /*onProductDetails(p: Product) {
    let url= btoa(p._links.product.href);
    this.router.navigateByUrl("/product/"+url);
    //this.router.navigateByUrl("/product/"+p.id);
  }*/

  onEditProduct(p: any) {
    console.log("HHHHHH", p);
    this.currentProduct=p;
	this.productService.findCategory(p.id)
	      .subscribe(cat=>{
	        
			
	        this.selectedCategory=cat;
			
			console.log(cat, "HHHHHHXXXXXXXXXX",this.selectedCategory);
	      },err=>{
	        console.log(err);
	      })
	
	
    this.mode=1;
  }
  onAddProduct(product: Product){
   // console.log(this.addFileUpload,"DDDDDD", product);
    this.productService.addProduct(product, this.addFileUpload)
      .subscribe(d=>{
        //this.product=d;
        this.router.navigateByUrl("/products/1/0");
        //this.mode=0;
      },err=>{
        console.log(err);
      })
  }

  onDeleteProduct(id: number) {
    //console.log("gggggggg", id);
    this.productService.removeProduct(id).
    subscribe(
      res =>{
        this.router.navigateByUrl("/products/1/0");

      }
    );
  }

  onUpdateProduct( product: Product ) {
    console.log("ppppmmmm",product);
    // this.product= data;
     let url=this.currentProduct?._links.self.href;
    // console.log("uuuuu", url);

     let idProduct: string = url.substring(35, 50);

    // console.log("GGGGGGGGGGGG",this.product);
     this.url=this.host+"/products/"+idProduct;
    
     console.log(product, "OOOOOOOOOOOOOOOOOOO",  this.url);

    this.productService.upProduct( product)
      .subscribe(d=>{
        this.currentProduct=d;
        console.log("hhhhhhhhhh",this.currentProduct);
        this.mode=1;
      },err=>{
        console.log(err);
      })
  }
  onUpdateProductB( p: Product ) {
    //console.log("ppppmmmm",data);
    this.product= p;
    let url=this.currentProduct?._links.self.href;
    console.log("uuuuu", url);

    let idProduct: string = url.substring(35, 50);

    console.log("GGGGGGGGGGGG",this.product);
    this.url=this.host+"/products/"+idProduct;
    
    console.log(this.product, "OOOOOOOOOOOOOOOOOOO",  this.url);

    this.catalService.patchResource(this.url,   this.product)
      .subscribe(d=>{
        this.currentProduct=d;
        console.log("hhhhhhhhhh",this.currentProduct);
        this.mode=0;
      },err=>{
        console.log(err);
      })
  }
  isAdmin() {
    console.log("ZZZZZZZZa", this.authService.hasRoleIn(['ADMIN']));
    return  this.authService.hasRoleIn(['ADMIN']);
   // return this.authService.isAdmin();
  }
}
