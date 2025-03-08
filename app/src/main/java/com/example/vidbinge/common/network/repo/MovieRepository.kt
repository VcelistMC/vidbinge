package com.example.vidbinge.common.network.repo

import com.example.vidbinge.common.data.models.Movie
import com.example.vidbinge.common.domain.mapper.MovieMapper
import com.example.vidbinge.common.network.client.MovieRetrofitClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieRetrofitClient: MovieRetrofitClient,
    private val movieMapper: MovieMapper
) {
    fun getNowPlayingMovies(): Flow<List<Movie>>{
        return movieRetrofitClient.doRequest({ movieRetrofitClient.service.getNowPlayingMovies() }){
            it.results.subList(0, 6).map { movieDto ->  movieMapper.mapToModel(movieDto) }
        }
    }

    fun getPopularMovies(): Flow<List<Movie>>{
        return movieRetrofitClient.doRequest({ movieRetrofitClient.service.getPopularMovies() }){
            it.results.map { movieDto ->  movieMapper.mapToModel(movieDto) }
        }
    }

    fun getTopRatedMovies(): Flow<List<Movie>>{
        return movieRetrofitClient.doRequest({ movieRetrofitClient.service.getTopRatedMovies() }){
            it.results.map { movieDto ->  movieMapper.mapToModel(movieDto) }
        }
    }

    fun getUpcomingMovies(): Flow<List<Movie>>{
        return movieRetrofitClient.doRequest({ movieRetrofitClient.service.getUpcomingMovies() }){
            it.results.map { movieDto ->  movieMapper.mapToModel(movieDto) }
        }
    }
}