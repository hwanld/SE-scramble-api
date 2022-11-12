package com.se.scramble.domain.dto.users

import com.se.scramble.domain.entity.Users

class UsersResponseDto(
    val users_id: String,
    val nickname: String
) {
    constructor(entity: Users) : this(
        users_id = entity.users_id!!,
        nickname = entity.nickname
    )
}