package com.mike.recipe_mealdb.categoriesList.data

import com.mike.recipe_mealdb.common.model.CategoriesResponse
import com.mike.recipe_mealdb.common.model.SpecificCategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoriesService {

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoriesResponse>

    @GET("filter.php")
    suspend fun getFilteredCategories(@Query("c") category: String): Response<SpecificCategoryResponse>
}