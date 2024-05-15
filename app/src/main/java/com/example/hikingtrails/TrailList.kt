package com.example.hikingtrails

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TrailList(
    navController: NavController,
    modifier: Modifier = Modifier,
    trails: List<Trail>,
    //trailDao: TrailDao
    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Grid(navController, trails)
        }
}

@Composable
fun Grid(navController: NavController, trails: List<Trail>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.height(100.dp) //necessary so the size is not infinite
    ) {
        //val itemCount = trails.size
        items(trails) {
            val itemIndex = it.id
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .padding(4.dp)
                    .size(width = 240.dp, height = 200.dp)
                    .clickable {
                        navController.navigate(route="TrailDetails/$itemIndex")
                    }
                //.fillMaxSize()
            ) {
                it.name?.let { it1 ->
                    Text(
                        text = it1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
