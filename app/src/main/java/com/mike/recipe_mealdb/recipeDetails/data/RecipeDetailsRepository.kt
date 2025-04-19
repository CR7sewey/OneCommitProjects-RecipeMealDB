package com.mike.recipe_mealdb.recipeDetails.data

import com.mike.recipe_mealdb.common.model.Meal

class RecipeDetailsRepository(private val recipeDetailsService: RecipeDetailsService): IRecipeDetailsRepository  {

    override suspend fun getRecipeDetails(recipeId: String): Result<Meal?> {
        return try {
            val response = recipeDetailsService.getRecipeDetails(recipeId)
            if (response.isSuccessful) {
                val mealResponse = response.body()
                if (mealResponse != null && mealResponse.meals.isNotEmpty()) {
                    Result.success(mealResponse.meals[0])
                } else {
                    Result.failure(Exception("No meals found"))
                }
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}