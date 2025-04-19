package com.mike.recipe_mealdb.recipeDetails.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.recipe_mealdb.randomRecipes.data.IMealDBRepository
import com.mike.recipe_mealdb.common.model.Meal
import com.mike.recipe_mealdb.randomRecipes.data.MealDBRepository
import com.mike.recipe_mealdb.randomRecipes.data.MealDBService
import com.mike.recipe_mealdb.common.Retrofit
import com.mike.recipe_mealdb.randomRecipes.ui.MealState
import com.mike.recipe_mealdb.recipeDetails.data.IRecipeDetailsRepository
import com.mike.recipe_mealdb.recipeDetails.data.RecipeDetailsRepository
import com.mike.recipe_mealdb.recipeDetails.data.RecipeDetailsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MVVM_RecipeDetails: ViewModel() {

    private val mealDBRepository: IRecipeDetailsRepository
        get() {
            return RecipeDetailsRepository(
                Retrofit.retrofit.create(RecipeDetailsService::class.java)
            )
        }

    private var _meal: MutableStateFlow<RecipeDetailsState> = MutableStateFlow(RecipeDetailsState())
    val meal: StateFlow<RecipeDetailsState> = _meal



    // Factory method to create an instance of the ViewModel
    // fun factory(): ViewModelProvider.Factory {
    //     return object : ViewModelProvider.Factory {
    //         override fun <T : ViewModel> create(modelClass: Class<T>): T {
    //             if (modelClass.isAssignableFrom(MVVM_RandomRecipes::class.java)) {
    //                 return MVVM_RandomRecipes() as T
    //             }
    //             throw IllegalArgumentException("Unknown ViewModel class")
    //         }
    //     }
    // }



    fun getMeal(id: String) {
        viewModelScope.launch(Dispatchers.IO) { // Courotine context - launch the coroutine scope to launch suspend function
            _meal.value = _meal.value.copy(isLoading = true)

                val result = mealDBRepository.getRecipeDetails(id)
                if (result.isSuccess) {
                    _meal.value.meal = result.getOrNull()

                } else {
                    _meal.value = _meal.value.copy(
                        isLoading = false,
                        error = result.exceptionOrNull()?.message ?: "Unknown error"
                    )

            }
            _meal.value = _meal.value.copy(isLoading = false)
            Log.d("MVVM_RecipeDetails", "getRandomMeal: ${_meal.value.meal}")
        }
    }


}