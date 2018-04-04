import {Component, OnInit} from '@angular/core';
import {Category} from './Category';
import {CategoriesService} from "./categories.service";
import {ITreeOptions} from "angular-tree-component";
import {UserService} from "../user/user.service";

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css'],
  providers: [CategoriesService, UserService],
})
export class CategoriesComponent implements OnInit {


  categories: Category[];
  modifiedCategories: Category[];

  options: ITreeOptions = {
    idField: 'id',
    displayField: 'name',
    childrenField: 'subcategories',
    allowDrag: true,
    allowDrop: true
  };

  constructor(private categoryService: CategoriesService) {
  }

  ngOnInit() {
    this.getCategories()
  }

  getCategories(): void {
    this.categoryService.getCategories().subscribe(categories => this.categories = categories)
  }


}
