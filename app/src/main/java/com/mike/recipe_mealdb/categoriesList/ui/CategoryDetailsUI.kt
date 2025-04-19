package com.mike.recipe_mealdb.categoriesList.ui

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
import com.mike.recipe_mealdb.common.model.Category
import com.mike.recipe_mealdb.recipeDetails.ui.MVVM_RecipeDetails

@Composable
fun CategoryDetailsUI(specificCat: IsolatedCategory?, category: String, navHostController: NavHostController,modifier: Modifier = Modifier) {

    //var mvvm_CategoryDetails: MVVM_Categories = viewModel()
    //val categories = mvvm_CategoryDetails.categories.collectAsState().value

    //val specificCat = categories.categories.filter { it?.strCategory == category }.getOrNull(0)

    var rememberScroll = rememberScrollState(0)

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when {
            (specificCat?.isLoading == true) -> CircularProgressIndicator(
                modifier = modifier
                    .padding(16.dp)
            )

            (specificCat?.error?.isNotEmpty() == true) ->
                Text(
                    text = specificCat.error,
                    modifier = modifier
                        .padding(16.dp)
                )

            (specificCat != null) -> {
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
                        text = specificCat.category?.strCategory ?: "Category",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = modifier
                            .padding(16.dp)
                            .weight(1f)
                    )
                }

                Image(
                    painter = rememberAsyncImagePainter(specificCat.category?.strCategoryThumb),
                    contentDescription = null,
                    modifier = modifier
                        .wrapContentSize(Alignment.Center)
                        .aspectRatio(1f)
                        .padding(8.dp),
                )


                Text(
                    text = specificCat.category?.strCategoryDescription ?: "Description",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = modifier
                        .padding(8.dp).verticalScroll(rememberScroll)
                )
            }

        }
    }
}



