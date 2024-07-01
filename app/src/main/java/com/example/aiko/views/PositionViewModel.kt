package com.example.aiko.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aiko.data.model.Position
import com.example.aiko.data.repository.PositionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PositionViewModel() : ViewModel() {

    private val repository = PositionRepository()

    private val _position = MutableLiveData<Position>()
    val position: MutableLiveData<Position> = _position

    fun fetchPosition() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getPosition()
            _position.postValue(response)
        }
    }
}
