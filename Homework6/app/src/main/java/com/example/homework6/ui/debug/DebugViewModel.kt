package com.example.homework6.ui.debug

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.homework6.di.DataContainer
import com.example.homework6.domain.debug.useCase.GetDebugDataUseCase
import com.example.homework6.ui.debug.model.DebugDataUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DebugViewModel(
    private val getDebugDataUseCase: GetDebugDataUseCase
) : ViewModel() {

    private val _debugData = MutableStateFlow<DebugDataUiModel?>(null)
    val debugData : StateFlow<DebugDataUiModel?> get() = _debugData

    fun getDebugData() {
        viewModelScope.launch {
            getDebugDataUseCase.invoke().also {
                _debugData.emit(it)
            }
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val useCase = DataContainer.getDebugDataUseCase
                if (useCase != null) {
                    DebugViewModel(useCase)
                } else {
                    throw IllegalStateException("useCase is not initialized")
                }
            }
        }
    }

}