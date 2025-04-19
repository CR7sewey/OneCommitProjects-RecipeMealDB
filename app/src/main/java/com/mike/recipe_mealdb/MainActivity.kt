package com.mike.recipe_mealdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.mike.recipe_mealdb.categoriesList.ui.CategoriesUI
import com.mike.recipe_mealdb.categoriesList.ui.MVVM_Categories
import com.mike.recipe_mealdb.randomRecipes.ui.MVVM_RandomRecipes
import com.mike.recipe_mealdb.randomRecipes.ui.RandomRecipesUI
import com.mike.recipe_mealdb.ui.theme.Recipe_MealDBTheme

class MainActivity : ComponentActivity() {
    //private val mvvm_RandomRecipes by viewModels<MVVM_RandomRecipes>() //{ RandomRecipesViewModel.Factory }
   // private val mvvm_Categories by viewModels<MVVM_Categories>() //{ CategoriesViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Recipe_MealDBTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(navController,
                            modifier = Modifier
                        )
                    },
                    ) { innerPadding ->
                    App(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
