package com.se.scramble.domain.dto.issues

class IssuesResponseDto(
    val issues_id: Long,
    val content: String,
    val deadline: String,
    val storyPoint: Int,
    val category: String
) {
}