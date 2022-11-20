package com.se.scramble.controller

import com.se.scramble.domain.RestAPIMessages
import com.se.scramble.domain.dto.projects.ProjectsSaveRequestDto
import com.se.scramble.service.ProjectsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectsController(
    private val projectsService: ProjectsService
) : BaseController() {

    @PostMapping("api/projects/save")
    fun save(@RequestBody projectsSaveRequestDto: ProjectsSaveRequestDto): ResponseEntity<RestAPIMessages> =
        sendResponseHttpByJson("Project is saved well", projectsService.save(projectsSaveRequestDto))

    @GetMapping("api/projects/findById/{projects_id}")
    fun findById(@PathVariable projects_id: Long): ResponseEntity<RestAPIMessages> =
        sendResponseHttpByJson("Get project's information with id :$projects_id", projectsService.findById(projects_id))

    @PutMapping("api/projects/invite/{users_id}/{projects_id}")
    fun invite(@PathVariable users_id: String, @PathVariable projects_id: Long): ResponseEntity<RestAPIMessages> =
        sendResponseHttpByJson(
            "Invite users $users_id to projects $projects_id",
            projectsService.invite(users_id, projects_id)
        )

    @GetMapping("api/projects/getUsersList/{projects_id}")
    fun getUsersList(@PathVariable projects_id: Long): ResponseEntity<RestAPIMessages> =
        sendResponseHttpByJson("Get usersList in projects $projects_id", projectsService.getUsersList(projects_id))

}