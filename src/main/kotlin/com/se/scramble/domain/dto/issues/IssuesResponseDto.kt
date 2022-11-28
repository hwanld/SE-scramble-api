package com.se.scramble.domain.dto.issues

class IssuesResponseDto(
    val issues_id: String,
    val content: String,
    val deadline: String,
    val storyPoint: Int,
    val category: String,
    val importance: String
) {
}