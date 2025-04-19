package com.mike.recipe_mealdb.categoriesList.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.recipe_mealdb.categoriesList.data.CategoriesRepository
import com.mike.recipe_mealdb.categoriesList.data.CategoriesService
import com.mike.recipe_mealdb.common.Retrofit
import com.mike.recipe_mealdb.common.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MVVM_Categories: ViewModel() {


    private val categoriesRepository: CategoriesRepository
        get() {
            return CategoriesRepository(
                Retrofit.retrofit.create(CategoriesService::class.java)
            )
        }

    private var categoriesState: MutableStateFlow<CategoriesState> = MutableStateFlow(CategoriesState())
    val categories: StateFlow<CategoriesState> = categoriesState

    private var specificCategoryState: MutableStateFlow<SpecificCategoryState> = MutableStateFlow(SpecificCategoryState())
    val specificCategory: StateFlow<SpecificCategoryState> = specificCategoryState

    private var isolatedCategoryState: MutableStateFlow<IsolatedCategory> = MutableStateFlow(IsolatedCategory())
    val isolatedCategory: StateFlow<IsolatedCategory> = isolatedCategoryState

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) { // Courotine context - launch the coroutine scope to launch suspend function
            categoriesState.value = categoriesState.value.copy(isLoading = true)
            val result = categoriesRepository.getCategories()
            if (result.isSuccess) {
                categoriesState.value = categoriesState.value.copy(
                    categories = result.getOrNull()?.categories ?: emptyList(),
                    isLoading = false
                )
            } else {
                categoriesState.value = categoriesState.value.copy(
                    error = result.exceptionOrNull()?.message ?: "Unknown error",
                    isLoading = false
                )
            }

        }

    }

    fun getFilteredCategories(category: String) {
        viewModelScope.launch {
            specificCategoryState.value = specificCategoryState.value.copy(isLoading = true)
            val result = categoriesRepository.getFilteredCategories(category)
            if (result.isSuccess) {
                specificCategoryState.value = specificCategoryState.value.copy(
                    specificCategory = result.getOrNull()?.meals ?: emptyList(),
                    isLoading = false
                )
            } else {
                specificCategoryState.value = specificCategoryState.value.copy(
                    error = result.exceptionOrNull()?.message ?: "Unknown error",
                    isLoading = false
                )
            }
        }
    }

    fun setCategory(category: Category?) {
        viewModelScope.launch {
            isolatedCategoryState.value = isolatedCategoryState.value.copy(isLoading = true)

            isolatedCategoryState.value = isolatedCategoryState.value.copy(
                    category = category,
                    isLoading = false
                )
            }

    }

}