package com.se.scramble.service

import com.se.scramble.domain.dto.issues.IssuesDragAndDropDto
import com.se.scramble.domain.dto.issues.IssuesResponseDto
import com.se.scramble.domain.dto.issues.IssuesSaveRequestDto
import com.se.scramble.domain.dto.issues.IssuesUpdateRequestDto
import com.se.scramble.domain.entity.Issues
import com.se.scramble.domain.repository.IssuesRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class IssuesService(
    private val issuesRepository: IssuesRepository,
    private val usersService: UsersService,
    private val projectsService: ProjectsService
) {
    @Transactional
    fun save(issuesSaveRequestDto: IssuesSaveRequestDto): IssuesResponseDto {
        val saveIssues: Issues = issuesSaveRequestDto.toEntity()
        val author = usersService.findUsers(issuesSaveRequestDto.author)
        val projects = projectsService.findProjects(issuesSaveRequestDto.projects_id)

        var index: Long = 0
        projects.issues.forEach { issues: Issues ->
            if (issues.category.equals(issuesSaveRequestDto.category)) index++
        }
        saveIssues.index = index

        author.issues.add(saveIssues)
        projects.issues.add(saveIssues)
        saveIssues.users = author
        saveIssues.projects = projects

        issuesRepository.save(saveIssues)

        return findById(saveIssues.issues_id!!)
    }

    private fun IssuesSaveRequestDto.toEntity(): Issues =
        Issues(
            content = content,
            deadline = deadline,
            storyPoint = storyPoint,
            category = category,
            importance = importance
        )

    fun findById(issues_id: Long): IssuesResponseDto {
        val issues = findIssues(issues_id)
        return issues.toResponseDto()
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

    fun findIssues(issues_id: Long): Issues =
        issuesRepository.findById(issues_id).orElseThrow {
            IllegalArgumentException("Error raise at IssuesRepository")
        }

    fun dragAndDrop(issuesDragAndDropDto: IssuesDragAndDropDto): Boolean {
        val issues = findIssues(issuesDragAndDropDto.issues_id)
        TODO("Drag and Drop, After making index")
        return true
    }

    fun update(issuesUpdateRequestDto: IssuesUpdateRequestDto): IssuesResponseDto {
        val updateIssues = findIssues(issuesUpdateRequestDto.issues_id.toLong())
        updateIssues.update(issuesUpdateRequestDto)
        return findById(updateIssues.issues_id!!)
    }

}