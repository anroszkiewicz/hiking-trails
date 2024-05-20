package com.example.hikingtrails

import android.annotation.SuppressLint
import android.os.SystemClock
import android.util.Log
import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

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
        val isTimerRunning = viewModel.isTimerRunning.observeAsState()
        LaunchedEffect(key1 = isTimerRunning.value){
            while (isTimerRunning.value == true){
                viewModel.timer.value = SystemClock.uptimeMillis() - viewModel.startTime
                delay(10)
            }
        }
        val timer = viewModel.timer.observeAsState()
        if (trail != null) {
            DetailCard(trail, timer, isTimerRunning, viewModel)
        }
    }
}

@SuppressLint("Recycle")
@Composable
fun DetailCard(trail: Trail, timer: State<Long?>, isTimerRunning: State<Boolean?>, viewModel: TrailViewModel) {
    Column (modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth()) {

        val imageID: Int = LocalContext.current.resources.getIdentifier("image" + trail.id.toString(), "drawable", "com.example.hikingtrails")
        val context = LocalContext.current
        val drawableId = remember(imageID) {
            context.resources.getIdentifier(
                "image" + trail.id.toString(),
                "drawable",
                context.packageName
            )
        }
        Image(
            painterResource(id = imageID),
            contentDescription = null
        )

        Text(trail.name, modifier = Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        Text(trail.type, modifier = Modifier.padding(10.dp))
        trail.description?.let { Text(it, textAlign = TextAlign.Justify) }
        Text("Etapy:", modifier = Modifier.padding(10.dp))
        trail.stages?.let { Text(it, modifier = Modifier.padding(10.dp))}
        Text("Trudność: " + trail.difficulty.toString() + "/5", modifier = Modifier.padding(10.dp))
        Text("Czas: " + trail.time.toString() + " minut", modifier = Modifier.padding(10.dp))
        Text("Upłynęło: ${timer.value?.milliseconds}", modifier = Modifier.padding(10.dp))
        Button(onClick = { toggleTimer(viewModel) }) {
            if (isTimerRunning.value == true) Text("Stop")
            else Text("Start")
        }
        Button(onClick = { resetTimer(viewModel) }) {
            Text("Reset")
        }
    }
}

fun toggleTimer(viewModel: TrailViewModel) {
    if (viewModel.timer.value == null) viewModel.timer.value = 0
    if (!viewModel.isTimerRunning.value!!) viewModel.startTime = SystemClock.uptimeMillis() - viewModel.timer.value!!
    viewModel.isTimerRunning.value = !viewModel.isTimerRunning.value!!
}

fun resetTimer(viewModel: TrailViewModel) {
    viewModel.isTimerRunning.value = false
    viewModel.timer.value = 0
}
