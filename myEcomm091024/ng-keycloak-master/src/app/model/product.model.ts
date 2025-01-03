export interface Product{
  id:number;
  name:string;
  description:string;
  price:number;
  promotion:boolean;
  selected:boolean;
  available:boolean ;
  photoName:string;
  quantity:number;
  stock: number;
  _links:{
    self:{
      href:string;
    },
    product:{
      href:string;
    },
    category:{
      href:string
    }
  }

}
