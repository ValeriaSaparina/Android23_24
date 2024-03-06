package com.example.homework6.domain.debug.useCase

import com.example.homework6.domain.debug.mapper.DebugDataUiModelMapper
import com.example.homework6.domain.debug.repository.DebugDataRepository
import com.example.homework6.ui.debug.model.DebugDataUiModel

class GetDebugDataUseCase(
    private val repository: DebugDataRepository,
    private val mapper: DebugDataUiModelMapper,
) {
    fun invoke() : DebugDataUiModel {
        val data = repository.getDebugData()
        return mapper.mapDomainToUiModel(data)
    }
}