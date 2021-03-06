package com.example.webshop.service

import com.example.webshop.entity.Category
import com.example.webshop.dto.CategoryDto
import com.example.webshop.dto.CategorySimpleDto
import com.example.webshop.dto.CreateCategoryDto
import com.example.webshop.dto.UpdateCategoryDto
import com.example.webshop.repository.CategoryRepository
import com.example.webshop.repository.ShopRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository, private val shopRepository: ShopRepository) {

    fun save(category: CreateCategoryDto) {
        val categoryToSave = categoryToSave(category)
        categoryRepository.save(categoryToSave)
    }

    private fun categoryToSave(createCategoryDto: CreateCategoryDto): Category {
        val shop = shopRepository.findById(createCategoryDto.shopId)
        val category = Category(createCategoryDto.name, shop!!)
        return if (createCategoryDto.parentCategoryId != null) {
            val parentCategory: Category = categoryRepository.getOne(createCategoryDto.parentCategoryId)
            category.copy(parentCategory = parentCategory)
        } else {
            category
        }
    }

    @CacheEvict("categories")
    fun deleteById(id: Long) {
        val category: Category = categoryRepository.findById(id) ?: handleLackOfResource(id)
        if (category.subcategories.isNotEmpty()) {
            error("Cannot delete category with subcategories")
        }
        categoryRepository.delete(category)
    }

    @CachePut("categories")
    fun update(id: Long, dto: UpdateCategoryDto) {
        val category = categoryRepository.findById(id) ?: handleLackOfResource(id)
        val newParentOrNull = newParentOrNull(dto.parentId, category)
        val updated = category.copy(name = dto.newName, parentCategory = newParentOrNull)
        categoryRepository.save(updated)
    }

    private fun handleLackOfResource(id: Long): Nothing = throw NoSuchElementException("No category with id $id")

    private fun newParentOrNull(parentCategoryId: Long?, category: Category): Category? =
            if (parentCategoryId == null) {
                null
            } else {
                newParent(parentCategoryId, category)
            }

    private fun newParent(parentCategoryId: Long, category: Category): Category {
        val newParent = categoryRepository.findById(parentCategoryId)
                ?: throw IllegalArgumentException("Invalid parent category")
        val forbiddenCategories = getForbiddenParentCategories(category)
        if (forbiddenCategories.contains(newParent)) {
            error("Cannot set parent category to subcategory")
        }
        return newParent
    }

    private fun getForbiddenParentCategories(category: Category): Set<Category> =
            category.subcategories + category.subcategories.flatMap { getForbiddenParentCategories(it) }

    @Cacheable("shop_categories")
    fun findByShopId(shopId: Long): Iterable<CategoryDto> {
        val shop = shopRepository.findById(shopId)!!
        val categories = categoryRepository.findByShop(shop)
        return toCategoryDto(categories)
    }

    private fun toCategoryDto(categories: Iterable<Category>): MutableList<CategoryDto> {
        val categoriesDto = mutableSetOf<CategoryDto>()
        categories.forEach { categoriesDto.add(CategoryDto(it.name, it.parentCategory?.id, it.id, mutableSetOf())) }

        for (categoryDto1 in categoriesDto) {
            for (categoryDto2 in categoriesDto) {
                if (categoryDto1.parentCategory == categoryDto2.id) {
                    categoryDto2.subcategories.add(categoryDto1)
                }
            }
        }
        val categoryDtoDistinct = mutableListOf<CategoryDto>()
        categoriesDto.forEach { if (it.parentCategory == null) categoryDtoDistinct.add(it) }
        return categoryDtoDistinct
    }

    fun findSubcategoriesByCategoryId(category_id: Long): MutableSet<CategorySimpleDto> {
        val parentCategory: Category = categoryRepository.findById(category_id)!!
        val categories = this.categoryRepository.findByParentCategory(parentCategory)
        val categoriesDto = mutableSetOf<CategorySimpleDto>()
        categories.forEach { categoriesDto.add(it.toSimpleDto()) }
        return categoriesDto
    }
}
