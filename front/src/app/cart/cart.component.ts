import {Component, OnInit} from '@angular/core';
import {CartService} from './cart.service';
import {Shop} from '../shops/shop';
import {CartPosition} from './cart-position';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cartPositions: Set<CartPosition>;
  shops = new Set<Shop>();

  constructor(private cartService: CartService) {
  }

  ngOnInit() {
    this.init();
  }

  private init() {
    this.cartPositions = this.cartService.cartPositions;
    this.cartPositions.forEach(it => this.shops.add(it.shop));
  }

  getGivenShopPositions(shop: Shop): CartPosition[] {
    return Array.from(this.cartPositions).filter(it => it.shop === shop);
  }

  getOverallPrice(): number {
    return Array.from(this.cartPositions)
      .reduce((prev, curr) => prev + curr.product.price * curr.amount, 0);
  }

  getPriceForShop(shop: Shop): number {
    return Array.from(this.cartPositions)
      .filter(position => position.shop === shop)
      .reduce((prev, curr) => prev + curr.product.price * curr.amount, 0);
  }

  onSubmit(shop: Shop = null) {
    if (shop == null) {
      this.shops.forEach(it => this.cartService.postOrder(it));
    } else {
      this.cartService.postOrder(shop);
    }
    this.init();
  }
}
