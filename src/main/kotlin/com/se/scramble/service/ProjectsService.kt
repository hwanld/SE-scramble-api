package com.se.scramble.service

import com.se.scramble.domain.dto.projects.ProjectsResponseDto
import com.se.scramble.domain.dto.projects.ProjectsSaveRequestDto
import com.se.scramble.domain.entity.Projects
import com.se.scramble.domain.entity.Users
import com.se.scramble.domain.repository.ProjectsRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ProjectsService(
    private val projectsRepository: ProjectsRepository,
    private val usersService: UsersService
) {
    @Transactional
    fun save(projectsSaveRequestDto: ProjectsSaveRequestDto): ProjectsResponseDto {
        val saveProjects: Projects = projectsSaveRequestDto.toEntity()
        val author: Users = usersService.findUsers(projectsSaveRequestDto.author)
        projectsRepository.save(saveProjects)
        saveProjects.users.add(author)
        author.projects.add(saveProjects)
        return findById(saveProjects.projects_id!!)
    }

    private fun ProjectsSaveRequestDto.toEntity(): Projects =
        Projects(name = this.name)

    @Transactional
    fun invite(users_id: String, projects_id: Long): Boolean {
        val users = usersService.findUsers(users_id)
        val projects = findProjects(projects_id)
        projects.users.add(users)
        users.projects.add(projects)
        return true
    }

    fun findById(projects_id: Long): ProjectsResponseDto {
        val projects = findProjects(projects_id)
        return ProjectsResponseDto(projects.projects_id!!, projects.name)
    }

    fun findProjects(projects_id: Long): Projects =
        projectsRepository.findById(projects_id).orElseThrow {
            IllegalArgumentException("Error raise at ProjectsRepository")
        }

    fun getUsersList(projects_id: Long): MutableList<String> {
        val usersList: MutableList<String> = ArrayList()
        val projects = findProjects(projects_id)
        for (users: Users in projects.users)
            usersList.add(users.users_id!!)
        return usersList
    }
}