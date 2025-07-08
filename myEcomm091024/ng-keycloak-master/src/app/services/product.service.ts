import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest } from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "../model/product.model";
//import { Url } from 'url';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  public host:string="http://localhost:8080/product-serviceb";

  constructor(private http:HttpClient) { }

  uploadPhotoProduct(file: File): Observable<HttpEvent<{}>> {
    console.log("OOOOOOb"+ file);
    let formdata: FormData = new FormData();
    formdata.append('file', file);
    console.log("IIIIIII"+ formdata);

    const req = new HttpRequest('POST', this.host+"/api/product/uploadPhoto/"+formdata, {
      reportProgress: true,
      responseType: 'text'
    });

    return this.http.request(req);
  }
  public addProduct(product: Product, file: File){
    let formdata: FormData = new FormData();
    formdata.append('file', file);
    formdata.append('product', JSON.stringify(product));

    let headers = new Headers();
    /** In Angular 5, including the header Content-Type can invalidate your request */
    headers.append('Content-Type', 'multipart/form-data');
    headers.append('Accept', 'application/json');
    //let options = ({ headers: headers });

    console.log(JSON.stringify(product),"TTTTTTTTTT",formdata);
    let obj= { product,  file};
    console.log("YYYYYYYYYYY", file);
    return this.http.post(this.host+"/api/product/add/",  formdata);
  }

  public upProduct(product: Product){
    let formdata: FormData = new FormData();
   // formdata.append('file', file);
    formdata.append('product', JSON.stringify(product));

    let headers = new Headers();
    /** In Angular 5, including the header Content-Type can invalidate your request */
    headers.append('Content-Type', 'multipart/form-data');
    headers.append('Accept', 'application/json');
    //let options = ({ headers: headers });

    console.log(JSON.stringify(product),"TTTTTTTTTT",formdata);
    let obj= { product};
    //console.log("YYYYYYYYYYY", file);
    return this.http.put(this.host+"/api/product/upProduct",  formdata);
  }

  public upProductB(id: any, product: Product){
       
    console.log(JSON.stringify(product));
    let obj= { product};
    console.log(JSON.stringify(product), "YYYYYYYYYYY", product);
    return this.http.put( this.host+"/api/product/upProduct/"+`${id}`, JSON.stringify(product));
  }

  removeProduct(id: number | undefined) : Observable<any>{
    console.log("bbbbbbbbbbbbb", id, "rrrrrrrrrrrrrrr", this.host+'/api/product/delete'+`/${id}`) ;
    return this.http.delete(this.host + "/api/product/delete"+`/${id}`);
  }
  
  findCategory(id: any | undefined) : Observable<any>{
      console.log("bbbbbbbbbbbbb", id, "rrrrrrrrrrrrrrr", this.host+'/api/product/category'+`/${id}`) ;
      return this.http.get(this.host + "/api/product/products"+`/${id})`);
  }
  
}

