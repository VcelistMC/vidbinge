package com.example.vidbinge.details.ui.states

import androidx.compose.ui.graphics.Color
import com.example.vidbinge.details.data.model.Cast
import com.example.vidbinge.common.ui.State
import com.example.vidbinge.details.data.model.TvShowDetails

data class TvShowDetailsScreenState(
    val castList: List<Cast> = emptyList(),
    val isAddedToWatchList: Boolean = false,
    val isLoading: Boolean = false,
    val isCastListLoading: Boolean = false,
    val errorMessage: String? = null,
    val tvShowDetails: TvShowDetails? = null,
    val ambientScreenColor: Color = Color.White
): State
