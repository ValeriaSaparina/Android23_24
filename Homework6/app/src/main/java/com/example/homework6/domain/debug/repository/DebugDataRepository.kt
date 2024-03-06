package com.example.homework6.domain.debug.repository

import com.example.homework6.domain.debug.model.DebugDataDomainModel

interface DebugDataRepository {

    fun getDebugData() : DebugDataDomainModel

}