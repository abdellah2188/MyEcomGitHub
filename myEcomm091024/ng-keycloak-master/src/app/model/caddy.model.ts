import {ItemProduct} from './item-product.model';
import {Customer} from './customer.model';

export class Caddy{
  public customer: Customer|undefined;

  public name:string;
  public items:Map<number,ItemProduct>=new Map();

  constructor(name:string){
    this.name=name;
  }
}
