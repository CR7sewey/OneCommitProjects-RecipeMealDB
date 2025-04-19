package com.mike.recipe_mealdb.categoriesList.ui

import com.mike.recipe_mealdb.common.model.Category
import com.mike.recipe_mealdb.common.model.SpecificCategory
import kotlinx.serialization.Serializable

data class CategoriesState(
    var categories: List<Category?> = emptyList<Category>(),
    val isLoading: Boolean = false,
    val error: String = ""
)

data class SpecificCategoryState(
    var specificCategory: List<SpecificCategory?> = emptyList<SpecificCategory>(),
    val isLoading: Boolean = false,
    val error: String = ""
)


data class IsolatedCategory(
    var category: Category? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)