package com.example.hikingtrails

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun TrailDetails(
    modifier: Modifier = Modifier,
    navController: NavController,
    index: Int,
    //trailDao: TrailDao
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Text(index.toString())
        //val trail = getData(trailDao, index)
        //DetailCard(trail)
    }
}

@Composable
fun DetailCard(trail: Trail) {
    Column {
        Text(trail.name)
        Text(trail.type)
        trail.description?.let { Text(it) }
        Text("Difficulty: " + trail.difficulty.toString())
        Text("Time: " + trail.time.toString() + "minutes")
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun getData(trailDao: TrailDao, index: Int): Trail {

    val scope = rememberCoroutineScope()
    var trail: Trail? = null
    scope.launch() {
        //trail = trailDao.getDetails(index)
    }
    return trail!!
}