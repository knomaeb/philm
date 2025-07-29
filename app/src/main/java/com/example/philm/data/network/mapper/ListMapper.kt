package com.example.philm.data.network.mapper

import com.example.philm.data.local.entities.list.UserListEntity
import com.example.philm.data.network.dto.list.ListResult
import com.example.philm.domain.model.list.UserList

fun ListResult.toUserListEntity(): UserListEntity =
    UserListEntity(
        listId = id,
        mediaType = mediaType,
        posterPath = posterPath,
    )

fun UserListEntity.toUserList(): UserList =
    UserList(
        id = listId,
        mediaType = mediaType,
        posterPath = posterPath,
    )