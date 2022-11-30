package com.se.scramble.domain.dto.issues

class IssuesUpdateRequestDto(
    val issues_id: String,
    val content: String,
    val deadline: String,
    val storyPoint: Int,
    val category: String,
    val importance: String
) {
}