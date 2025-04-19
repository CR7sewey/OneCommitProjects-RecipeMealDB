package com.mike.recipe_mealdb.recipeDetails.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

@Composable
fun RecipeDetailsUI(id: String, navHostController: NavHostController, modifier: Modifier = Modifier) {
    var mvvm_RecipeDetails: MVVM_RecipeDetails = viewModel()
    mvvm_RecipeDetails.getMeal(id)
    val meal = mvvm_RecipeDetails.meal.collectAsState().value

    var rememberScroll = rememberScrollState(0)

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when {
            (meal.isLoading) -> CircularProgressIndicator(
                modifier = modifier
                    .padding(16.dp)
            )

            (meal.error.isNotEmpty()) ->
                Text(
                    text = meal.error,
                    modifier = modifier
                        .padding(16.dp)
                )

            (meal.meal != null) -> {
                val mealDetails = meal.meal
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back Button"
                        )
                    }
                    Text(
                        text = mealDetails?.strMeal ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = modifier
                            .padding(16.dp)
                            .weight(1f)
                    )
                }

                Image(
                    painter = rememberAsyncImagePainter(mealDetails?.strMealThumb),
                    contentDescription = null,
                    modifier = modifier
                        .wrapContentSize(Alignment.Center)
                        .aspectRatio(1f)
                        .padding(8.dp),
                )


                    Text(
                        text = mealDetails?.strInstructions ?: "",
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = modifier
                            .padding(8.dp).verticalScroll(rememberScroll)
                    )
                }

            }
        }
    }


    
