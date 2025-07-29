package com.example.philm.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.philm.R

sealed class NavigationItem(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val selectedIcon: Int?,
    @DrawableRes val unSelectedIcon: Int?
) {
    object Home : NavigationItem(
        "/home",
        R.string.title_home,
        selectedIcon = R.drawable.home_icon_default_24px,
        unSelectedIcon = R.drawable.home_icon_default_24px
    )

    object Search :
        NavigationItem(
            "/search",
            R.string.title_search,
            selectedIcon = R.drawable.search_icon_default_24px,
            unSelectedIcon = R.drawable.search_icon_default_24px
        )

    object Settings : NavigationItem(
        "/settings",
        R.string.title_settings,
        selectedIcon = R.drawable.settings_icon_default_24px,
        unSelectedIcon = R.drawable.settings_icon_default_24px
    )
    object Details : NavigationItem(
        "/details/{movieId}",
        R.string.title_details,
        null,null
        )
}