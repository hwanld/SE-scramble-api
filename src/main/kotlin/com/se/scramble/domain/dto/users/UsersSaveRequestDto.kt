package com.se.scramble.domain.dto.users

import com.se.scramble.domain.entity.Users

class UsersSaveRequestDto(
    val users_id: String,
    val password: String,
    val nickname: String
) {}