package com.mike.recipe_mealdb.randomRecipes.data

import com.mike.recipe_mealdb.common.model.MealResponse
import retrofit2.Response
import retrofit2.http.GET

interface MealDBService {

    @GET("random.php")
    suspend fun getRandomMeal(): Response<MealResponse>

}
