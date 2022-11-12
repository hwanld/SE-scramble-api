package com.se.scramble.domain.repository

import com.se.scramble.domain.entity.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UsersRepository : JpaRepository<Users, String> {
}