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
        saveIssues.issueIndex = index

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
            index = this.issueIndex
        )

    fun findIssues(issues_id: Long): Issues =
        issuesRepository.findById(issues_id).orElseThrow {
            IllegalArgumentException("Error raise at IssuesRepository")
        }


    @Transactional
    fun dragAndDrop(issuesDragAndDropDto: IssuesDragAndDropDto): IssuesResponseDto {
        val issues = findIssues(issuesDragAndDropDto.issues_id)
        val projects = issues.projects
        if (issuesDragAndDropDto.past_droppableId == issuesDragAndDropDto.cur_droppableId) {
            if (issuesDragAndDropDto.past_index > issuesDragAndDropDto.cur_index) {
                projects!!.issues.forEach { issues ->
                    if (issues.issueIndex >= issuesDragAndDropDto.cur_index && issues.issueIndex <= issuesDragAndDropDto.past_index && issues.category == issuesDragAndDropDto.past_droppableId)
                        issues.issueIndex++
                }
                issues.issueIndex = issuesDragAndDropDto.cur_index.toLong()
                return findById(issues.issues_id!!)
            }

            projects!!.issues.forEach { issues ->
                if (issues.issueIndex >= issuesDragAndDropDto.past_index && issues.issueIndex <= issuesDragAndDropDto.cur_index && issues.category == issuesDragAndDropDto.cur_droppableId)
                    issues.issueIndex--
            }
            issues.issueIndex = issuesDragAndDropDto.cur_index.toLong()
            return findById(issues.issues_id!!)
        }

        projects!!.issues.forEach { issues ->
            if (issues.category == issuesDragAndDropDto.past_droppableId && issues.issueIndex >= issuesDragAndDropDto.past_index)
                issues.issueIndex--
            if (issues.category == issuesDragAndDropDto.cur_droppableId && issues.issueIndex >= issuesDragAndDropDto.cur_index)
                issues.issueIndex++
        }
        issues.category = issuesDragAndDropDto.cur_droppableId
        issues.issueIndex = issuesDragAndDropDto.cur_index.toLong()

        return findById(issues.issues_id!!)
    }

    @Transactional
    fun update(issuesUpdateRequestDto: IssuesUpdateRequestDto): IssuesResponseDto {
        val updateIssues = findIssues(issuesUpdateRequestDto.issues_id.toLong())
        updateIssues.update(issuesUpdateRequestDto)
        return findById(updateIssues.issues_id!!)
    }

    @Transactional
    fun delete(issues_id: Long): Boolean {
        val targetIssues = findIssues(issues_id)
        val targetIndex = targetIssues.issueIndex
        val projects = targetIssues.projects
        projects!!.issues.forEach { issues ->
            if (issues.category == targetIssues.category && issues.issueIndex >= targetIndex)
                issues.issueIndex--
        }
        targetIssues.projects!!.issues.remove(targetIssues)
        targetIssues.users!!.issues.remove(targetIssues)

        issuesRepository.delete(targetIssues)
        return true
    }
}