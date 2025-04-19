package com.mike.recipe_mealdb.categoriesList.data

import com.mike.recipe_mealdb.common.model.CategoriesResponse
import com.mike.recipe_mealdb.common.model.SpecificCategoryResponse

class CategoriesRepository(
    private val categoriesService: CategoriesService
) : ICategoriesRepository {
    /**
     * Fetches the list of categories from the API.
     *
     * @return A [Result] containing either the [CategoriesResponse] or an error.
     */
    override suspend fun getCategories(): Result<CategoriesResponse> {
        return try {
            val response = categoriesService.getCategories()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFilteredCategories(category: String): Result<SpecificCategoryResponse> {
        return try {
            val response = categoriesService.getFilteredCategories(category)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}