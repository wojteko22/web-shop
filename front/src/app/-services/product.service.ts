import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Product} from '../-models/Product';
import {environment} from '../../environments/environment';

@Injectable()
export class ProductService {

  productEndpoint = environment.apiUrl + '/products';

  constructor(private http: HttpClient) {

  }

  addProduct(product: Product) {
    return this.http.post(this.productEndpoint, product);
  }

  getShopProducts(shopId: Number){
    return this.http.get<Product[]>(this.productEndpoint+"/shop/"+shopId);
  }

  getProduct(productId: Number){
    return this.http.get<Product>(this.productEndpoint+'/'+productId);
  }

}