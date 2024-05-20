package com.example.hikingtrails

import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun ListAndDetails(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: TrailViewModel,
    category: String?,
    index: Int?
) {
    Log.d("category","$category")
    Log.d("index","$index")
    val displayMetrics: DisplayMetrics = LocalContext.current.getResources().getDisplayMetrics()
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density

    Row(modifier = Modifier.fillMaxSize()) {
        if(dpWidth <= 400) { //vertical phone
            if (index != null) TrailDetails( //index selected - display only details
                navController = navController,
                modifier = Modifier,
                index = index,
                viewModel = viewModel
            )
            else if (category == null) TrailList( //no index and no category - display all trails
                navController = navController,
                modifier = Modifier.fillMaxWidth(),
                viewModel = viewModel,
                category = "all",
                itemIndex = index
            )
            else TrailList( //display trails by category
                navController = navController,
                modifier = Modifier.fillMaxWidth(),
                viewModel = viewModel,
                category = category,
                itemIndex = index
            )
        }
        else { //tablet or horizontal phone
            if (category == null) TrailList( //display all trails on the left
                navController = navController,
                modifier = Modifier,
                viewModel = viewModel,
                category = "all",
                itemIndex = index
            )
            else TrailList( //display trails by category on the left
                navController = navController,
                modifier = Modifier.fillMaxWidth(0.5f),
                viewModel = viewModel,
                category = category,
                itemIndex = index
            )
            if (index != null) TrailDetails( //details on the right
                navController = navController,
                modifier = Modifier.fillMaxWidth(0.5f),
                index = index,
                viewModel = viewModel,
            )
        }
    }
}