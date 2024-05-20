package com.example.hikingtrails

import android.os.SystemClock
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TrailViewModel(private val repository: TrailRepository) : ViewModel() {
    //database operations
    val allTrails : LiveData<List<Trail>> = repository.allTrails.asLiveData()
    fun getTrail(id: Int): LiveData<Trail> {
        return repository.getTrail(id).asLiveData()
    }
    fun getTrailsByType(category: String): LiveData<List<Trail>> {
        return repository.getTrailsByType(category).asLiveData()
    }
    //timer
    var startTime: Long = 0
    val timer: MutableLiveData<Long> by lazy { MutableLiveData<Long>(0) }
    val isTimerRunning: MutableLiveData<Boolean> by lazy {MutableLiveData<Boolean>(false)}
    val walkingSpeed: MutableLiveData<Int> by lazy {MutableLiveData<Int>(1)}
}

class TrailViewModelFactory(private val repository: TrailRepository) : ViewModelProvider.Factory {
    override fun <T :ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrailViewModel::class.java)) {
            return TrailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}