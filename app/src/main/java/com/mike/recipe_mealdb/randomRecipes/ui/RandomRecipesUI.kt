package com.mike.recipe_mealdb.randomRecipes.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

@Composable
fun RandomRecipesUI(
    mvvm: NavHostController,
    modifier: Modifier = Modifier) {
    var scrollState = rememberScrollState(0)
    // mvvm.getRandomMeal()
    val newVM: MVVM_RandomRecipes = viewModel()  // not needed, vm invokation in the composable
    val meal = newVM.mealList.collectAsState().value

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
        // .verticalScroll(scrollState)
    ) {
        /*repeat(10) {
            Text(
                text = "Hello $it!",
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }*/

        when {
            (meal.isLoading) -> CircularProgressIndicator(
                modifier = modifier
                    .padding(16.dp).align(Alignment.Center)
            )

            (meal.error.isNotEmpty()) ->
                Text(
                    text = meal.error,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )

            (meal.meal.isEmpty()) ->
                Text(
                    text = "No meals found",
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )

            else ->
                Column {


                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    items(meal.meal) { meal ->
                        meal?.let {
                            Column(
                                modifier = modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Card(
                                    modifier = modifier
                                        .fillMaxSize()
                                        .padding(8.dp).shadow(4.dp).background(
                                            MaterialTheme.colorScheme.primaryContainer
                                        ),
                                    shape = MaterialTheme.shapes.large,

                                    ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            it.strMealThumb,
                                        ),
                                        contentDescription = null,
                                        modifier = modifier
                                            .fillMaxSize()
                                            .aspectRatio(1f)
                                    )
                                    Text(
                                        text = it.strMeal,
                                        modifier = modifier
                                            .fillMaxSize()
                                            .padding(2.dp),
                                        style = MaterialTheme.typography.headlineSmall
                                    )


                                }
                            }
                        }
                    }


                }
        }}

    }
}