package com.example.hikingtrails

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TrailDetails(
    modifier: Modifier = Modifier,
    navController: NavController,
    index: Int,
    viewModel: TrailViewModel
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        val trail = viewModel.getTrail(index).observeAsState().value
        if (trail != null) {
            DetailCard(trail)
        }
    }
}

@Composable
fun DetailCard(trail: Trail) {
    Column (modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth()) {
        Text(trail.name, modifier = Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        Text(trail.type, modifier = Modifier.padding(10.dp))
        trail.description?.let { Text(it, textAlign = TextAlign.Justify) }
        Text("Etapy:", modifier = Modifier.padding(10.dp))
        trail.stages?.let { Text(it, modifier = Modifier.padding(10.dp))}
        Text("Trudność: " + trail.difficulty.toString(), modifier = Modifier.padding(10.dp))
        Text("Czas: " + trail.time.toString() + " minut", modifier = Modifier.padding(10.dp))
    }
}