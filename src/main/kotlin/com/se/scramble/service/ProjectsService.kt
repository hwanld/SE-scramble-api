package com.se.scramble.service

import com.se.scramble.domain.dto.issues.IssuesListWithCategoryDto
import com.se.scramble.domain.dto.issues.IssuesResponseDto
import com.se.scramble.domain.dto.issues.IssuesResponseDtoComparator
import com.se.scramble.domain.dto.projects.ProjectsResponseDto
import com.se.scramble.domain.dto.projects.ProjectsSaveRequestDto
import com.se.scramble.domain.entity.Issues
import com.se.scramble.domain.entity.Projects
import com.se.scramble.domain.entity.Users
import com.se.scramble.domain.repository.ProjectsRepository
import org.springframework.stereotype.Service
import java.util.*
import java.util.Collections.sort
import javax.transaction.Transactional
import kotlin.collections.ArrayList

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

    fun getIssuesList(projects_id: Long): MutableList<IssuesListWithCategoryDto> {
        var returnList: MutableList<IssuesListWithCategoryDto> = ArrayList()
        val projects = findProjects(projects_id)
        var issuesListWithTodoDto: IssuesListWithCategoryDto = IssuesListWithCategoryDto("예정")
        var issuesListWithInProgressDto: IssuesListWithCategoryDto = IssuesListWithCategoryDto("진행 중")
        var issuesListWithCompletedDto: IssuesListWithCategoryDto = IssuesListWithCategoryDto("완료")

        for (issues: Issues in projects.issues) {
            val category: String = issues.category
            val issuesResponseDto: IssuesResponseDto = issues.toResponseDto()
            when (category) {
                "예정" -> issuesListWithTodoDto.issuesList.add(issuesResponseDto)
                "진행 중" -> issuesListWithInProgressDto.issuesList.add(issuesResponseDto)
                "완료" -> issuesListWithCompletedDto.issuesList.add(issuesResponseDto)
            }
        }

        Collections.sort(issuesListWithTodoDto.issuesList, IssuesResponseDtoComparator())
        Collections.sort(issuesListWithInProgressDto.issuesList, IssuesResponseDtoComparator())
        Collections.sort(issuesListWithCompletedDto.issuesList, IssuesResponseDtoComparator())

        returnList.add(issuesListWithTodoDto)
        returnList.add(issuesListWithInProgressDto)
        returnList.add(issuesListWithCompletedDto)



        return returnList
    }

    fun Issues.toResponseDto(): IssuesResponseDto =
        IssuesResponseDto(
            issues_id = this.issues_id!!.toString(),
            content = this.content,
            deadline = this.deadline,
            storyPoint = this.storyPoint,
            category = this.category,
            importance = this.importance,
            index = this.index
        )
}