package com.mike.recipe_mealdb.recipeDetails.ui

import com.mike.recipe_mealdb.common.model.Meal

data class RecipeDetailsState(
    var meal: Meal? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)