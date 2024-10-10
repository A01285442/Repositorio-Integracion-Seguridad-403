package com.example.legalmatch.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel



class SearchViewModel : ViewModel() {
    var searchText by mutableStateOf("")
        private set

    fun updateSearchText(newText: String) {
        searchText = newText
    }
}