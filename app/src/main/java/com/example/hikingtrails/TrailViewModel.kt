package com.example.hikingtrails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData

class TrailViewModel(private val repository: TrailRepository) : ViewModel() {
    val allTrails : LiveData<List<Trail>> = repository.allTrails.asLiveData()
}

class TrailViewModelFactory(private val repository: TrailRepository) : ViewModelProvider.Factory {
    override fun <T :ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrailViewModel::class.java)) {
            return TrailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}