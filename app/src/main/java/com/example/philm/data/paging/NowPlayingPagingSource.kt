package com.example.philm.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.philm.core.utils.AppConstants.NETWORK_PAGE_SIZE
import com.example.philm.data.network.NetworkService
import com.example.philm.core.utils.AppConstants.STARTING_PAGE_INDEX
import java.io.IOException
import com.example.philm.data.network.dto.movie.MovieResult
import retrofit2.HttpException

class NowPlayingPagingSource(private val apiService: NetworkService) : PagingSource<Int, MovieResult>(){

    override fun getRefreshKey(state: PagingState<Int, MovieResult>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResult> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val data = apiService.getNowPlayingMovie(position)
            val nextKey = if (data.results?.isEmpty() == true) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            val prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1
            LoadResult.Page(
                data = data.results!!,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}