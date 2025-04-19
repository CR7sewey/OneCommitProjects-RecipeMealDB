package com.mike.recipe_mealdb.randomRecipes.ui

import com.mike.recipe_mealdb.common.model.Meal

data class MealState(
    var meal: List<Meal?> = emptyList<Meal>(),
    val isLoading: Boolean = false,
    val error: String = ""
)
