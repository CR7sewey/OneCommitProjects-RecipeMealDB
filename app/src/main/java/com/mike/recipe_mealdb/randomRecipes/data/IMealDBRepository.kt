package com.mike.recipe_mealdb.randomRecipes.data

import com.mike.recipe_mealdb.common.model.Meal

interface IMealDBRepository {
    suspend fun getRandomMeal(): Result<Meal?>
}