package com.mike.recipe_mealdb.recipeDetails.data

import com.mike.recipe_mealdb.common.model.MealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeDetailsService {

    @GET("lookup.php")
    suspend fun getRecipeDetails(@Query("i") recipeId: String): Response<MealResponse>

}