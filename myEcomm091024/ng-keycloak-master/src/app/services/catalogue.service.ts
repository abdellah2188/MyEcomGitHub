import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import {observable, Observable} from 'rxjs';
import {Order} from '../model/Order.model';
import {Product} from "../model/product.model";

@Injectable({
  providedIn: 'root'
})
export class CatalogueService {

  public host:string="http://localhost:8080/product-serviceb";

  constructor(private http:HttpClient) { }

  public getResource(url: string){
     console.log("nnnnnnnnnnnnnnnn", url);

     return (this.http.get(url));
  }
  
  public getProduct({url}: { url: any }):Observable<Product> {
    console.log("MMMMMMMMMMMMMMM", url);
    return this.http.get<Product>(url);

  }

  uploadPhotoProduct(file: File, idProduct: any): Observable<HttpEvent<{}>> {
    //console.log("XXXXXXXXX");

    let formdata: FormData = new FormData();
    formdata.append('file', file);
    console.log(file,"FFFDDDFD",formdata);

    const req = new HttpRequest('POST', this.host+'/api/product/uploadPhoto/'+idProduct, formdata, {
      reportProgress: true,
      responseType: 'text'
    });

    return this.http.request(req);
  }


  public patchResource(url:string, data:Product){
    console.log("YYYYYYYYYYY", url,'yyyy', data);
    return this.http.patch(url ,data);
  }


  
}
