package com.se.scramble.domain.dto.users

import com.se.scramble.domain.entity.Users

class UsersSaveRequestDto(
    private val users_id: String,
    private val password: String,
    private val nickname: String
) {
    fun toEntity() = Users(
        users_id = users_id,
        password = password,
        nickname = nickname
    )
}