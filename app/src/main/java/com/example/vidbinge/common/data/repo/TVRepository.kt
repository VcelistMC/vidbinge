package com.example.vidbinge.common.data.repo

import com.example.vidbinge.common.data.models.tvshow.TvShow
import com.example.vidbinge.common.domain.mapper.TvMapper
import com.example.vidbinge.common.network.client.TVRetrofitClient
import com.example.vidbinge.details.data.model.TvShowDetails
import com.example.vidbinge.details.domain.mapper.TvShowDetailsMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TVRepository @Inject constructor(
    private val tvRetrofitClient: TVRetrofitClient,
    private val tvMapper: TvMapper,
    private val tvShowDetailsMapper: TvShowDetailsMapper
) {
    fun getNowAiringShows(): Flow<List<TvShow>>{
        return tvRetrofitClient.doRequest({ tvRetrofitClient.service.getAiringTodayShows() }){
            it.results.subList(0, 6).map { dto -> tvMapper.mapToModel(dto) }
        }
    }

    fun getPopularShows(): Flow<List<TvShow>> {
        return tvRetrofitClient.doRequest({ tvRetrofitClient.service.getPopularShows() }) {
            it.results.map { dto -> tvMapper.mapToModel(dto) }
        }
    }

    fun getTopRatedShows(): Flow<List<TvShow>> {
        return tvRetrofitClient.doRequest({ tvRetrofitClient.service.getTopRatedShows() }) {
            it.results.map { dto -> tvMapper.mapToModel(dto) }
        }
    }

    fun getTvShowDetails(tvShowId: Int): Flow<TvShowDetails>{
        return tvRetrofitClient.doRequest({ tvRetrofitClient.service.getTvShowDetails(tvShowId) }){
            tvShowDetailsMapper.mapToModel(it)
        }
    }
}