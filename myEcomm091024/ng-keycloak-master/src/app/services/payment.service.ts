import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  public host:string="http://localhost:8080/payment-service/api/payment";

  constructor(private http: HttpClient) {}

  addPayment({data}: { data: any }) {
    
    return this.http.post(this.host, data);
  }
}
