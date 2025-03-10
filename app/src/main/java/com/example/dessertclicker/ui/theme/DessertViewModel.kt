package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.DessertUiState
import com.example.dessertclicker.data.Datasource.dessertList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {

    private val _dessertUiState = MutableStateFlow(DessertUiState())
    val dessertUiState: StateFlow<DessertUiState> = _dessertUiState.asStateFlow()

    // Xử lý click vào bánh 1
    fun onDessert1Clicked() {
        _dessertUiState.update { currentState ->
            val dessertsSold1 = currentState.dessertsSold1 + 1
            val totalDessertsSold = currentState.dessertsSold + 1
           // val nextDessertIndex1 = determineDessertIndex(dessertsSold1)

            currentState.copy(
                dessertsSold1 = dessertsSold1,
                dessertsSold = totalDessertsSold,
                revenue = currentState.revenue + currentState.currentDessertPrice1,
//                currentDessertIndex1 = nextDessertIndex1,
//                currentDessertImageId1 = dessertList[nextDessertIndex1].imageId,
//                currentDessertPrice1 = dessertList[nextDessertIndex1].price
            )
        }
    }

    // Xử lý click vào bánh 2
    fun onDessert2Clicked() {
        _dessertUiState.update { currentState ->
            val dessertsSold2 = currentState.dessertsSold2 + 1
            val totalDessertsSold = currentState.dessertsSold + 1
           // val nextDessertIndex2 = determineDessertIndex(dessertsSold2)

            currentState.copy(
                dessertsSold2 = dessertsSold2,
                dessertsSold = totalDessertsSold,
                revenue = currentState.revenue + currentState.currentDessertPrice2,


            )
        }
    }

    // Xác định chỉ số bánh tiếp theo dựa vào tổng số bánh đã bán
    private fun determineDessertIndex(totalDessertsSold: Int): Int {
        var dessertIndex = 0
        for (index in dessertList.indices) {
            if (totalDessertsSold >= dessertList[index].startProductionAmount) {
                dessertIndex = index
            } else {
                break
            }
        }
        return dessertIndex
    }
}
