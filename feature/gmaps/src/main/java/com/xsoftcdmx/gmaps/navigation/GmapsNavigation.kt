package com.xsoftcdmx.gmaps.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.xsoftcdmx.gmaps.GmapsScreen

const val GMAPS_GRAPH_ROUTE = "gmaps_graph"
const val GMAPS_ROUTE = "gmaps_route"

fun NavGraphBuilder.gmapsScreen() {
    composable(
        route = GMAPS_GRAPH_ROUTE
    ) {
        GmapsScreen()
    }
}