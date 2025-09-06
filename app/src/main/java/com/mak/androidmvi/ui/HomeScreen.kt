package com.mak.androidmvi.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.mak.androidmvi.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier, scrollBehavior: TopAppBarScrollBehavior?, viewModel: ViewModel) {

    val items = listOf(
        Pair(R.drawable.sharp_delivery_truck_speed_24, "Category 1"),
        Pair(R.drawable.sharp_delivery_truck_speed_24, "Category 2"),
        Pair(R.drawable.sharp_delivery_truck_speed_24, "Category 3"),
        Pair(R.drawable.sharp_delivery_truck_speed_24, "Category 4"),
        Pair(R.drawable.sharp_delivery_truck_speed_24, "Category 5"),
        Pair(R.drawable.sharp_delivery_truck_speed_24, "Category 6"),
    )


    LazyColumn(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(
                Color.White , RoundedCornerShape(8.dp)
            )
            .then(
                if (scrollBehavior != null) Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                else Modifier
            )
    ) {

        // 1️⃣ Grid section
        item {
            Text(
                text = "Categories",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .height(250.dp) // adjust height to fit all rows
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                userScrollEnabled = false // disable nested scroll
            ) {

                items(items.size) { index ->
                    val (imageRes, title) = items[index]

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White // background color of card
                        ),
                        shape = RoundedCornerShape(4.dp), // optional
                        elevation = CardDefaults.cardElevation(2.dp) // optional
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = title,
                                modifier = Modifier
                                    .size(60.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        // 2️⃣ Lazy list section
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Recent Items",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(20) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // background color of card
                ),
                shape = RoundedCornerShape(4.dp), // optional
                elevation = CardDefaults.cardElevation(2.dp) // optional
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                  /*  Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(MaterialTheme.colorScheme.primary)
                    )*/

                    Image(
                        painter = painterResource(id = R.drawable.sharp_delivery_truck_speed_24),
                        contentDescription = "Image",
                        modifier = Modifier
                            .size(50.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))
                    Text("List Item ${index + 1}")
                }
            }
        }
    }
}