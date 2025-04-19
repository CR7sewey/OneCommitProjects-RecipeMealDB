package com.mike.recipe_mealdb.randomRecipes.data

import android.util.Log
import com.mike.recipe_mealdb.common.model.Meal

class MealDBRepository(private val mealDBService: MealDBService): IMealDBRepository {

    override suspend fun getRandomMeal(): Result<Meal?> {
        return try {
            val response = mealDBService.getRandomMeal()
            if (response.isSuccessful) {
                val meal = response.body()?.meals?.get(0)
                if (meal == null) {
                    return Result.failure(Exception("Error: Meal is null"))
                }
                Log.d("MealDBRepository", "getRandomMeal: $meal")
                Result.success(meal)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }


}