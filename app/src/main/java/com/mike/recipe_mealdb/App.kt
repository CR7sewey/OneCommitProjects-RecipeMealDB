package com.mike.recipe_mealdb

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.mike.recipe_mealdb.categoriesList.ui.CategoriesUI
import com.mike.recipe_mealdb.categoriesList.ui.CategoryDetailsUI
import com.mike.recipe_mealdb.categoriesList.ui.IsolatedCategory
import com.mike.recipe_mealdb.categoriesList.ui.MVVM_Categories
import com.mike.recipe_mealdb.categoriesList.ui.SpecificCategoryUI
import com.mike.recipe_mealdb.common.model.Category
import com.mike.recipe_mealdb.randomRecipes.ui.RandomRecipesUI
import com.mike.recipe_mealdb.recipeDetails.ui.RecipeDetailsUI

data class NavigationItem(
    val title: String,
    val icon: Int,
    val route: String
)

val navigationItems = listOf(
    NavigationItem(
        title = "Categories",
        icon = R.drawable.categories,
        route = Screen.Categories.rout
    ),
    NavigationItem(
        title = "Random",
        icon = R.drawable.food,
        route = Screen.Random.rout
    ),
)

sealed class Screen(val rout: String) {
    object Categories: Screen("categories")
    object Random: Screen("random")
    object Filter: Screen("filter")
    object Lookup: Screen("lookup")
    object CategoryDetails: Screen("categoryDetails")
}


@Composable
fun App(
    navController: NavHostController,
    modifier: Modifier
) {

    val mvvmCategories: MVVM_Categories = viewModel()

    //val navController = rememberNavController()

        val navGraph = navController.createGraph(startDestination = Screen.Categories.rout) {
            composable(Screen.Categories.rout) { CategoriesUI(navController) }
            composable(Screen.Random.rout) { RandomRecipesUI(navController) }
            composable(Screen.Filter.rout + "/{category}", arguments = listOf(navArgument("category") {
                type =
                    NavType.StringType
            })) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: ""
                SpecificCategoryUI(requireNotNull(category), navController)
                // FilterUI(navController, category)
            }
            composable(Screen.Lookup.rout + "/{id}", arguments = listOf(navArgument("id") {
                type =
                    NavType.StringType
            })) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                RecipeDetailsUI(requireNotNull(id), navController)
                // FilterUI(navController, category)
            }
            /*composable(Screen.CategoryDetails.rout + "/{category}", arguments = listOf(navArgument("category") {
                type =
                    NavType.StringType
            })) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: ""
                val categories = mvvmCategories.categories.collectAsState().value

                val specificCat = categories.categories.filter { it?.strCategory == category }.getOrNull(0)
                mvvmCategories.setCategory(specificCat)

                CategoryDetailsUI(mvvmCategories.isolatedCategory.collectAsState().value, requireNotNull(category), navController)
                // FilterUI(navController, category)
            }*/
            composable(Screen.CategoryDetails.rout) {

                val catObj: Category? = navController.previousBackStackEntry?.savedStateHandle?.get<Category>("category") // deserialize
                //val catObj = navController.previousBackStackEntry?.savedStateHandle?.get<Category>("category")
                CategoryDetailsUI(
                    IsolatedCategory(
                        category = catObj,
                        isLoading = false,
                        error = ""
                    ),
                    requireNotNull(
                    catObj?.strCategory ?:"Category"
                ), navController)
            }
        }
        NavHost(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 12.dp),
            navController = navController,
            graph= navGraph,
        )

}

@Composable
fun BottomNavigationBar(navController: NavHostController, modifier: Modifier = Modifier) {
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        containerColor = Color.White
    ) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        modifier = modifier.size(24.dp),

                        contentDescription = null,
                        tint = if (index == selectedNavigationIndex.intValue)
                            Color.White
                        else Color.Gray
                    )
                    //Icon(painter = painterResource(id = item.icon), contentDescription = null)

                },
                label = {
                    Text(
                        item.title,
                        color = if (index == selectedNavigationIndex.intValue)
                            Color.Black
                        else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.surface,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )

            )
        }
    }
}

