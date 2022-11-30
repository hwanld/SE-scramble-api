package com.se.scramble.controller

import com.se.scramble.domain.RestAPIMessages
import com.se.scramble.domain.dto.issues.IssuesDragAndDropDto
import com.se.scramble.domain.dto.issues.IssuesSaveRequestDto
import com.se.scramble.domain.dto.issues.IssuesUpdateRequestDto
import com.se.scramble.service.IssuesService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class IssuesController(
    final val issuesService: IssuesService
) : BaseController() {
    @PostMapping("api/issues/save")
    fun save(@RequestBody issuesSaveRequestDto: IssuesSaveRequestDto): ResponseEntity<RestAPIMessages> =
        sendResponseHttpByJson("Issues is saved well", issuesService.save(issuesSaveRequestDto))

    @GetMapping("api/issues/findById/{issues_id}")
    fun findById(@PathVariable issues_id: Long): ResponseEntity<RestAPIMessages> =
        sendResponseHttpByJson("Get issues' information with id : $issues_id", issuesService.findById(issues_id))

    @PutMapping("api/issues/update")
    fun update(@RequestBody issuesUpdateRequestDto: IssuesUpdateRequestDto): ResponseEntity<RestAPIMessages> =
        sendResponseHttpByJson("Issues is updated well", issuesService.update(issuesUpdateRequestDto))

    @PutMapping("api/issues/dragAndDrop")
    fun dragAndDrop(@RequestBody issuesDragAndDropDto: IssuesDragAndDropDto): ResponseEntity<RestAPIMessages> =
        sendResponseHttpByJson("Drag and drop issues", issuesService.dragAndDrop(issuesDragAndDropDto))
}
