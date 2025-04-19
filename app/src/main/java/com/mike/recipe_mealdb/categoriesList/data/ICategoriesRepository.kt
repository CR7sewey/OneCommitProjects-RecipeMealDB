package com.mike.recipe_mealdb.categoriesList.data

import com.mike.recipe_mealdb.common.model.CategoriesResponse
import com.mike.recipe_mealdb.common.model.SpecificCategoryResponse

interface ICategoriesRepository {
    suspend fun getCategories(): Result<CategoriesResponse>
    suspend fun getFilteredCategories(category: String): Result<SpecificCategoryResponse>
}