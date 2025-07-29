package com.example.philm.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.philm.presentation.home.HomeScreen
import com.example.philm.presentation.home.HomeViewModel

@Composable
fun PhilmNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = "home"
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){
        composable<NavigationItem.Home> {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                uiState = homeViewModel.uiState,
                navigateToMovieScreen = {id->
                    navController.navigate("movie/$id")
                },
                navigateToTvScreen = {id->
                    navController.navigate("tv/$id")
                },
                navigateToSearchScreen = {
                    navController.navigate("search")
                }
            )
        }
    }
}