package com.example.hikingtrails

import android.util.DisplayMetrics
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hikingtrails.TabScreen


@Composable
fun TrailList(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: TrailViewModel,
    category: String,

    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            if(category=="all") { //get all trails
                val trails by viewModel.allTrails.observeAsState(listOf())
                Grid(navController, trails, tabIndex=0)
            }
            else { //get trails by type
                val trails by viewModel.getTrailsByType(category).observeAsState(listOf())
                if(category=="Tatry Zachodnie") Grid(navController, trails, tabIndex=1)
                else Grid(navController, trails, tabIndex=2)
            }
        }
}

@Composable
fun TabScreen(
    navController: NavController,
    tabIndexInt: Int
) {
    var tabIndex by remember { mutableStateOf(tabIndexInt) }

    val tabs = listOf("Wszystkie szlaki", "Tatry Zachodnie", "Tatry Wysokie")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = {
                        tabIndex = index
                        if(tabIndex==0) navController.navigate(route="TrailList")
                        else if(tabIndex==1) navController.navigate(route="TrailList/Tatry Zachodnie")
                        else navController.navigate(route="TrailList/Tatry Wysokie")
                    }
                )
            }
        }
    }
}

@Composable
fun Grid(navController: NavController, trails: List<Trail>, tabIndex: Int) {

    val displayMetrics: DisplayMetrics = LocalContext.current.getResources().getDisplayMetrics()
    val dpHeight = (displayMetrics.heightPixels / displayMetrics.density).toInt()
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density

    Column() {
        TabScreen(navController, tabIndex)
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.height(dpHeight.dp) //necessary so the size is not infinite
        ) {
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
                            navController.navigate(route = "TrailDetails/$itemIndex")
                        }
                    //.fillMaxSize()
                ) {
                    Box() {
                        val imageID: Int = LocalContext.current.resources.getIdentifier("image" + it.id.toString(), "drawable", "com.example.hikingtrails")
                        val context = LocalContext.current
                        val drawableId = remember(imageID) {
                            context.resources.getIdentifier(
                                "image" + it.id.toString(),
                                "drawable",
                                context.packageName
                            )
                        }
                        Image(
                            painterResource(id = imageID),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = it.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color(0xFFFF5050),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}
