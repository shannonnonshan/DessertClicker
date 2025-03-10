package com.example.dessertclicker.data

import androidx.annotation.DrawableRes
import com.example.dessertclicker.data.Datasource.dessertList

data class DessertUiState(
    val dessertsSold1: Int = 0,
    val dessertsSold2: Int = 0,
    val dessertsSold: Int = 0,
    val revenue: Int = 0,

    // Chỉ số của từng bánh
    val currentDessertIndex1: Int = 0,
    val currentDessertIndex2: Int = 1,

    // Giá và hình ảnh của từng bánh
    val currentDessertPrice1: Int = dessertList[currentDessertIndex1].price,
    val currentDessertPrice2: Int = dessertList[currentDessertIndex2].price,
    @DrawableRes val currentDessertImageId1: Int = dessertList[currentDessertIndex1].imageId,
    @DrawableRes val currentDessertImageId2: Int = dessertList[currentDessertIndex2].imageId
)
