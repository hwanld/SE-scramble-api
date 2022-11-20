package com.se.scramble.service

import com.se.scramble.domain.dto.projects.ProjectsResponseDto
import com.se.scramble.domain.dto.users.UsersLoginRequestDto
import com.se.scramble.domain.dto.users.UsersResponseDto
import com.se.scramble.domain.dto.users.UsersSaveRequestDto
import com.se.scramble.domain.entity.Projects
import com.se.scramble.domain.entity.Users
import com.se.scramble.domain.repository.UsersRepository
import com.se.scramble.exception.FailToLoginException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UsersService(
    private final val usersRepository: UsersRepository
) {

    @Transactional
    fun save(usersSaveRequestDto: UsersSaveRequestDto): UsersResponseDto {
        val saveUsers: Users = usersSaveRequestDto.toEntity()
        usersRepository.save(saveUsers)
        return findById(saveUsers.users_id!!)
    }

    private fun UsersSaveRequestDto.toEntity(): Users =
        Users(users_id, password, nickname)

    fun findById(users_id: String): UsersResponseDto {
        val users = findUsers(users_id)
        return UsersResponseDto(users.users_id!!, users.nickname)
    }

    fun findUsers(users_id: String): Users =
        usersRepository.findById(users_id).orElseThrow {
            IllegalArgumentException("Error raise at UsersRepository.findById $users_id")
        } as Users

    fun idCanUsable(users_id: String): Boolean =
        usersRepository.findAll().checkIdCanUsable(users_id)

    private fun List<Users>.checkIdCanUsable(users_id: String): Boolean {
        for (users: Users in this)
            if (users.users_id == users_id)
                return false
        return true
    }

    fun login(usersLoginRequestDto: UsersLoginRequestDto): Boolean {
        for (users: Users in usersRepository.findAll())
            if (users.users_id == usersLoginRequestDto.users_id)
                if (users.password == usersLoginRequestDto.password)
                    return true

        throw FailToLoginException("Unable to login with given information")
    }

    fun getProjectsList(users_id: String): MutableList<ProjectsResponseDto> {
        val projectsList: MutableList<ProjectsResponseDto> = ArrayList()
        val users = findUsers(users_id)
        for (projects: Projects in users.projects)
            projectsList.add(ProjectsResponseDto(projects.projects_id!!, projects.name))
        projectsList.sortBy { projectsResponseDto -> projectsResponseDto.projects_id }
        return projectsList
    }
}