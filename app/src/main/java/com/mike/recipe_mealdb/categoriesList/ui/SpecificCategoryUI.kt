package com.mike.recipe_mealdb.categoriesList.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun SpecificCategoryUI(category: String, navHostController: NavHostController, modifier: Modifier = Modifier) {
    var viewModel: MVVM_Categories = viewModel()
    viewModel.getFilteredCategories(category)
    var specificCategory = viewModel.specificCategory.collectAsState().value

    when {
        specificCategory.isLoading -> {
            // Show loading indicator
        }

        specificCategory.error.isNotEmpty() -> {
            // Show error message
        }

        specificCategory.specificCategory.isNotEmpty() -> {
            // Show the list of meals in the specific category
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        //movieDetailVM.cleanMovieId()
                        navHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back Button"
                        )
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = modifier.fillMaxSize()
                ) {
                    items(specificCategory.specificCategory) { meal ->


                        Card(
                            modifier = modifier
                                .padding(8.dp).fillMaxSize()
                                .shadow(4.dp).clickable(
                                    onClick = {
                                        navHostController.navigate("lookup/${meal?.idMeal}")
                                    }
                                ),
                        ) {
                            // Display meal image and name
                            AsyncImage(
                                model = meal?.strMealThumb,
                                contentDescription = meal?.strMeal,
                                modifier = modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = meal?.strMeal ?: "",
                                modifier = modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}