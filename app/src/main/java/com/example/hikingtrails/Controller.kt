package com.example.hikingtrails

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import kotlinx.coroutines.launch
import java.security.AccessController.getContext
import androidx.compose.runtime.livedata.observeAsState

enum class Screen() {
    TrailList,
    TrailDetails
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrailAppBar(
    //currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    drawerState: DrawerState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    TopAppBar(

        title = { Text("Szlaki turystyczne") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "menu"
                )
            }
        }
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TrailApp(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    trailViewModel: TrailViewModel
) {
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Szlaki turystyczne", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Ekran główny") },
                    selected = false,
                    onClick = {
                        navController.navigate(route="HomeScreen")
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Zobacz wszystkie szlaki") },
                    selected = false,
                    onClick = {
                        navController.navigate(route="TrailList")
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Szlaki w Tatrach Zachodnich") },
                    selected = false,
                    onClick = {
                        navController.navigate(route="TrailList")
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Szlaki w Tatrach Wysokich") },
                    selected = false,
                    onClick = {
                        navController.navigate(route="TrailList")
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TrailAppBar(
                    //currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    drawerState = drawerState
                )
            }
        ) { innerPadding ->
            //val uiState by viewModel.uiState.collectAsState()
            NavHost(
                navController = navController,
                startDestination = "HomeScreen",
                enterTransition = {EnterTransition.None},
                exitTransition = { ExitTransition.None},
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
            ) {
                //route to trail list with no arguments
                composable(route = Screen.TrailList.name) {
                    TrailList(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.padding_medium)),
                        navController = navController,
                        viewModel = trailViewModel,
                        category = "all")
                }
                //route to home screen
                composable(route = "HomeScreen") {
                    HomeScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.padding_medium)),
                        navController = navController
                    )
                }
                //route to details screen with arguments
                composable(route = "TrailDetails/{index}",
                    arguments = listOf(
                        navArgument(name = "index") {
                            type = NavType.IntType
                        }
                    )
                ) {index ->
                    index.arguments?.getInt("index")?.let {
                        TrailDetails(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(dimensionResource(R.dimen.padding_medium)),
                            navController = navController,
                            index = it,
                            viewModel = trailViewModel
                            //trailDao = trailDao
                            //trails = trails
                        )
                    }
                }
            }
        }
    }
}
