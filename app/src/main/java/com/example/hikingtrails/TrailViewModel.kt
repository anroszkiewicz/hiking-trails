package com.example.hikingtrails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TrailViewModel(private val repository: TrailRepository) : ViewModel() {
    var trail by mutableStateOf(Trail(0, "", "", null, null, 0, 0))
    val allTrails : LiveData<List<Trail>> = repository.allTrails.asLiveData()
    fun getTrail(id: Int): LiveData<Trail> {
        val livetrail : LiveData<Trail> = repository.getTrail(id).asLiveData()
        return livetrail
    }
}

class TrailViewModelFactory(private val repository: TrailRepository) : ViewModelProvider.Factory {
    override fun <T :ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrailViewModel::class.java)) {
            return TrailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}