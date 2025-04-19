package com.mike.recipe_mealdb.recipeDetails.data

import com.mike.recipe_mealdb.common.model.Meal

interface IRecipeDetailsRepository {
    suspend fun getRecipeDetails(recipeId: String): Result<Meal?>
}