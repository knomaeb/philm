package com.example.philm.domain.use_cases.list

import androidx.paging.PagingData
import androidx.paging.map
import com.example.philm.data.network.mapper.toUserList
import com.example.philm.domain.model.list.UserList
import com.example.philm.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUserListUseCase(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(
        listId: Int,
        language: String = "en-US",
        page: Int = 1,
    ): Flow<PagingData<UserList>> =
        movieRepository.getList(listId, language, page).map { pagingData ->
            pagingData.map {
                it.toUserList()
            }
        }
}