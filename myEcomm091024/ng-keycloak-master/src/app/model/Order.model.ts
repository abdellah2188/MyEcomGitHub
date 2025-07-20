import {Customer} from './customer.model';
import {ItemProduct} from './item-product.model';

export class Order {
  public id?:null;
  public customer?:Customer={firstName:"",lastName:"",adress:"",mobile:"",email:"",username:""};
  public products?:Array<ItemProduct>=[];
  public totalAmount?:number;
  public date?:Date;
}
