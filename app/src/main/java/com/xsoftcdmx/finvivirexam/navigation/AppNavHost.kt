package com.xsoftcdmx.finvivirexam.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.xsoftcdmx.gmaps.navigation.GMAPS_GRAPH_ROUTE
import com.xsoftcdmx.gmaps.navigation.gmapsScreen

@Composable
fun AppNavHost(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = GMAPS_GRAPH_ROUTE
    ) {
        gmapsScreen()
    }
}
