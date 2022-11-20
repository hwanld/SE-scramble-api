package com.se.scramble.domain.repository

import com.se.scramble.domain.entity.Projects
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectsRepository : JpaRepository<Projects, Long> {
}