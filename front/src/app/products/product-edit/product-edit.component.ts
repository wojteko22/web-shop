import {Component} from '@angular/core';
import {AddProductComponent} from '../add-product/add-product.component';
import {CategoriesService} from '../../categories/categories.service';
import {CredentialsService} from '../../-services/credentials.service';
import {FormBuilder} from '@angular/forms';
import {ProductService} from '../../-services/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Product} from '../../-models/Product';


@Component({
  selector: 'app-product-edit',
  templateUrl: '../add-product/add-product.component.html',
  styleUrls: ['../add-product/add-product.component.css'],
  providers: [CategoriesService],
})
export class ProductEditComponent extends AddProductComponent {

  submitText = 'Nadpisz dane produktu';
  private id: number;

  constructor(fb: FormBuilder,
              categoriesService: CategoriesService,
              productService: ProductService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
    super(fb, categoriesService, productService);

    this.activatedRoute.paramMap.subscribe((paramMap) => {
      this.id = Number(paramMap.get('id'));
      this.productService.getProduct(this.id).subscribe((product) => this.form.patchValue(product));
    });
  }

  protected useService(product: Product) {
    this.productService.editProduct(this.id, product).subscribe(() =>
      this.router.navigate(['/product', this.id])
    );
  }
}
