/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.dessertclicker

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dessertclicker.data.DessertUiState
import com.example.dessertclicker.ui.DessertViewModel
import com.example.dessertclicker.ui.theme.DessertClickerTheme

// tag for logging
private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate Called")

        setContent {
            DessertClickerTheme {
                DessertClickerApp()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }
}

/**
 * Share desserts sold information using ACTION_SEND intent
 */
private fun shareSoldDessertsInformation(intentContext: Context, dessertsSold: Int, revenue: Int) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            intentContext.getString(R.string.share_text, dessertsSold, revenue)
        )
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)

    try {
        startActivity(intentContext, shareIntent, null)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(
            intentContext,
            intentContext.getString(R.string.sharing_not_available),
            Toast.LENGTH_LONG
        ).show()
    }
}

@Composable
private fun DessertClickerApp(
    viewModel: DessertViewModel = viewModel()
) {
    val uiState by viewModel.dessertUiState.collectAsState()

    DessertClickerScreen(
        revenue = uiState.revenue,
        dessertsSold = uiState.dessertsSold,
        dessertImageId1 = uiState.currentDessertImageId1,
        dessertImageId2 = uiState.currentDessertImageId2,
        onDessert1Clicked = viewModel::onDessert1Clicked,
        onDessert2Clicked = viewModel::onDessert2Clicked
    )
}
@Composable
private fun DessertClickerApp(
    uiState: DessertUiState,
    onDessert1Clicked: () -> Unit,
    onDessert2Clicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            val intentContext = LocalContext.current
            AppBar(
                onShareButtonClicked = {
                    shareSoldDessertsInformation(
                        intentContext = intentContext,
                        dessertsSold = uiState.dessertsSold,
                        revenue = uiState.revenue
                    )
                }
            )
        }
    ) { contentPadding ->
        DessertClickerScreen(
            revenue = uiState.revenue,
            dessertsSold = uiState.dessertsSold,
            dessertImageId1 = uiState.currentDessertImageId1,
            dessertImageId2 = uiState.currentDessertImageId2,
            onDessert1Clicked = onDessert1Clicked,
            onDessert2Clicked = onDessert2Clicked,
            modifier = Modifier.padding(contentPadding)
        )
    }
}


@Composable
private fun AppBar(
    onShareButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
            //.background(MaterialTheme.colors.primary),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier.padding(start = 16.dp),
//            color = MaterialTheme.colors.onPrimary,
//            style = MaterialTheme.typography.h6,
        )
        IconButton(
            onClick = onShareButtonClicked,
            modifier = Modifier.padding(end = 16.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(R.string.share),
               // tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
fun DessertClickerScreen(
    revenue: Int,
    dessertsSold: Int,
    @DrawableRes dessertImageId1: Int,
    @DrawableRes dessertImageId2: Int,
    onDessert1Clicked: () -> Unit,
    onDessert2Clicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        // Ảnh nền
        Image(
            painter = painterResource(R.drawable.bakery_back),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize(), // Chiếm toàn bộ màn hình
                contentAlignment = Alignment.Center // Căn giữa theo cả chiều ngang và dọc
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {
                    // Phần trên trống để bánh luôn ở giữa
                    Spacer(modifier = Modifier.weight(1f))

                    // Hàng chứa 2 bánh
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Bánh 1
                        Image(
                            painter = painterResource(dessertImageId1),
                            contentDescription = "Dessert 1",
                            modifier = Modifier
                                .width(120.dp)
                                .height(120.dp)
                                .clickable { onDessert1Clicked() },
                            contentScale = ContentScale.Fit
                        )

                        // Bánh 2
                        Image(
                            painter = painterResource(dessertImageId2),
                            contentDescription = "Dessert 2",
                            modifier = Modifier
                                .width(120.dp)
                                .height(120.dp)
                                .clickable { onDessert2Clicked() },
                            contentScale = ContentScale.Fit
                        )
                    }

                    // Spacer đảm bảo bánh luôn ở giữa khi xoay màn hình
                    Spacer(modifier = Modifier.weight(1f))

                    // Hiển thị thông tin doanh thu và số bánh
                    TransactionInfo(revenue = revenue, dessertsSold = dessertsSold)
                }
            }

            // Thông tin doanh thu và số bánh
            TransactionInfo(revenue = revenue, dessertsSold = dessertsSold)
        }
    }
}



@Composable
private fun TransactionInfo(
    revenue: Int,
    dessertsSold: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.White),
    ) {
        DessertsSoldInfo(dessertsSold)
        RevenueInfo(revenue)
    }
}

@Composable
private fun RevenueInfo(revenue: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.total_revenue),
           // style = MaterialTheme.typography.h4
        )
        Text(
            text = "$${revenue}",
            textAlign = TextAlign.Right,
          //  style = MaterialTheme.typography.h4
        )
    }
}

@Composable
private fun DessertsSoldInfo(dessertsSold: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.dessert_sold),
          // style = MaterialTheme.typography.h6
        )
        Text(
            text = dessertsSold.toString(),
          //  style = MaterialTheme.typography.h6
        )
    }
}

@Preview
@Composable
fun MyDessertClickerAppPreview() {
    DessertClickerTheme {
        DessertClickerApp(
            uiState = DessertUiState(
                currentDessertImageId1 = R.drawable.cupcake,
                currentDessertImageId2 = R.drawable.donut
            ),
            onDessert1Clicked = {},
            onDessert2Clicked = {}
        )
    }
}