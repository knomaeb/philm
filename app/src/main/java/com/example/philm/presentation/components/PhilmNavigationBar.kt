package com.example.philm.presentation.components

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.philm.ui.theme.ExtendedTheme

@Composable
fun PhilmNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavigationBar(
        modifier = modifier,
        containerColor = ExtendedTheme.colors.neutralGrayDark2,
        contentColor = ExtendedTheme.colors.neutralWhite,
        tonalElevation = 0.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route
    }
}