import {Product} from "./product.model";

export class ItemProduct{
  id!:number;
  public product: Product | undefined;
  public price: number | undefined;
  public quantity!:number;
  public name!: string;
}
