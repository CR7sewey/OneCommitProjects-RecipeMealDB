package com.mike.recipe_mealdb.common.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class CategoriesResponse(
    val categories: List<Category>
)

@Parcelize
data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
): Parcelable

data class SpecificCategory(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String
)

data class SpecificCategoryResponse(
    val meals: List<SpecificCategory>
)