package com.mike.recipe_mealdb.randomRecipes.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.recipe_mealdb.randomRecipes.data.IMealDBRepository
import com.mike.recipe_mealdb.common.model.Meal
import com.mike.recipe_mealdb.randomRecipes.data.MealDBRepository
import com.mike.recipe_mealdb.randomRecipes.data.MealDBService
import com.mike.recipe_mealdb.common.Retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MVVM_RandomRecipes: ViewModel() {

    private val mealDBRepository: IMealDBRepository
        get() {
            return MealDBRepository(
                Retrofit.retrofit.create(MealDBService::class.java)
            )
        }

    private var _meal: MutableStateFlow<Meal?> = MutableStateFlow(null)
    val meal: StateFlow<Meal?> = _meal


    private var _mealList: MutableStateFlow<MealState> = MutableStateFlow(MealState())
    val mealList: StateFlow<MealState> = _mealList



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

    init {
            getRandomMeal()

    }


    fun getRandomMeal() {
        viewModelScope.launch(Dispatchers.IO) { // Courotine context - launch the coroutine scope to launch suspend function
            _mealList.value = _mealList.value.copy(isLoading = true)
            for (i in 0..20) {

            val result = mealDBRepository.getRandomMeal()
            if (result.isSuccess) {
                _meal.value = result.getOrNull()
                _mealList.value.meal = _mealList.value.meal + _meal.value

            } else {
                _meal.value = null
                _mealList.value = _mealList.value.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message ?: "Unknown error"
                )
            }
            }
            _mealList.value = _mealList.value.copy(isLoading = false)
            Log.d("MVVM_RandomRecipes", "getRandomMeal: ${_mealList.value}")
        }
    }


}