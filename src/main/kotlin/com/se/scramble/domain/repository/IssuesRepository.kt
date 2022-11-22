package com.se.scramble.domain.repository

import com.se.scramble.domain.entity.Issues
import org.springframework.data.jpa.repository.JpaRepository

interface IssuesRepository : JpaRepository<Issues, Long> {
}