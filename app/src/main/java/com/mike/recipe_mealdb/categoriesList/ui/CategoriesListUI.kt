package com.mike.recipe_mealdb.categoriesList.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.mike.recipe_mealdb.Screen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoriesUI(
    navHostController: NavHostController,
    modifier: Modifier = Modifier) {
    var scrollState = rememberScrollState(0)
    // mvvm.getRandomMeal()
    val newVM: MVVM_Categories = viewModel()  // not needed, vm invokation in the composable
    val categories = newVM.categories.collectAsState().value

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
            (categories.isLoading) -> CircularProgressIndicator(
                modifier = modifier
                    .padding(16.dp).align(Alignment.Center)
            )

            (categories.error.isNotEmpty()) ->
                Text(
                    text = categories.error,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )

            (categories.categories.isEmpty()) ->
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
                    items(categories.categories) { cat ->
                        cat?.let {
                            Column(
                                modifier = modifier
                                    .fillMaxSize()
                                    .padding(8.dp).combinedClickable(
                                        onClick = {
                                            navHostController.navigate("filter/${it.strCategory}")
                                        },
                                        onLongClick = {
                                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                                "category",
                                                it
                                            ) // serialize the object
                                            /**
                                            IsolatedCategory(
                                            category = it,
                                            isLoading = false,
                                            error = ""
                                            )
                                             *  */
                                            navHostController.navigate(Screen.CategoryDetails.rout)
                                        }

                                    ),
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
                                        it.strCategoryThumb,
                                    ),
                                    contentDescription = null,
                                    modifier = modifier
                                        .fillMaxSize()
                                        .aspectRatio(1f)
                                )
                                Text(
                                    text = it.strCategory,
                                    modifier = modifier
                                        .fillMaxSize()
                                        .padding(2.dp),
                                    maxLines = 1,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineSmall
                                )


                            }
                        }}
                    }


                }
        } }

    }
}