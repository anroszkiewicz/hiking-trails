package com.example.hikingtrails

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

enum class TrailScreen() {
    TrailList,
    TrailDetails
}

@Composable
fun TrailApp(
             navController: NavHostController = rememberNavController()) {
    // Get current back stack entry
    //val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    //val currentScreen = TrailScreen.valueOf(backStackEntry?.destination?.route ?: TrailScreen.Start.name)

    Scaffold(
        /*
        topBar = {
            TrailAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
        */
    ) { innerPadding ->
        //val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = TrailScreen.TrailList.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = TrailScreen.TrailList.name) {
                TrailList(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)))
            }
            composable(route = TrailScreen.TrailDetails.name) {
                TrailDetails(modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)))
            }
        }
    }
}