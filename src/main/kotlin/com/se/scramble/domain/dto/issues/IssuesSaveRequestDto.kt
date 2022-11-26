package com.se.scramble.domain.dto.issues

class IssuesSaveRequestDto(
    val content: String = "",
    val deadline: String = "",
    val storyPoint: Int = 0,
    val category: String = "예정",
    val author: String,
    val projects_id: Long,
    val importance: String = "0"
) {
}