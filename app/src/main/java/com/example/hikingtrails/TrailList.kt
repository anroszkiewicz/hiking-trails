package com.example.hikingtrails

import android.util.DisplayMetrics
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch


@Composable
fun TrailList(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: TrailViewModel,
    category: String,
    itemIndex: Int?
    ) {
        Surface(modifier = modifier, color = MaterialTheme.colorScheme.background) {
            if(category=="all") { //get all trails
                val trails by viewModel.allTrails.observeAsState(listOf())
                Grid(navController, trails, tabIndex =0, category ="all", itemIndex)
            }
            else { //get trails by type
                val trails by viewModel.getTrailsByType(category).observeAsState(listOf())
                if(category=="Tatry Zachodnie") Grid(navController, trails, tabIndex=1, category = category, itemIndex)
                else Grid(navController, trails, tabIndex=2, category = category, itemIndex)
            }
        }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabScreen(
    navController: NavController,
    tabIndexInt: Int,
    itemIndex: Int?
) {
    var tabIndex by remember { mutableStateOf(tabIndexInt) }
    val tabs = listOf("Wszystkie szlaki", "Tatry Zachodnie", "Tatry Wysokie")
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { tabs.size }
    )
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = index == pagerState.currentPage,
                    onClick = {
                        tabIndex = index
                        if (itemIndex != null) {
                            if (tabIndex == 0) scope.launch { navController.navigate(route = "ListAndDetails/all/$itemIndex") }
                            else if (tabIndex == 1) scope.launch { navController.navigate(route = "ListAndDetails/Tatry Zachodnie/$itemIndex") }
                            else scope.launch { navController.navigate(route = "ListAndDetails/Tatry Wysokie/$itemIndex") }
                        }
                        else{
                            if (tabIndex == 0) scope.launch { navController.navigate(route = "ListAndDetails/all") }
                            else if (tabIndex == 1) scope.launch { navController.navigate(route = "ListAndDetails/Tatry Zachodnie") }
                            else scope.launch { navController.navigate(route = "ListAndDetails/Tatry Wysokie") }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Grid(navController: NavController, trails: List<Trail>, tabIndex: Int, category: String, itemIndex: Int?) {

    val displayMetrics: DisplayMetrics = LocalContext.current.getResources().getDisplayMetrics()
    val dpHeight = (displayMetrics.heightPixels / displayMetrics.density).toInt()
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density

    Column() {
        TabScreen(navController, tabIndex, itemIndex)
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
                            navController.navigate(route = "ListAndDetails/$category/$itemIndex")
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
