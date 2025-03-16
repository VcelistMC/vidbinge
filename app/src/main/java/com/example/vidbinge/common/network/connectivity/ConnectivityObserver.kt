package com.example.vidbinge.common.network.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<ConnectivityStatus>

    enum class ConnectivityStatus {
        AVAILABLE, UNAVAILABLE, LOST
    }
}