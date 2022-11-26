package com.se.scramble.domain.dto.issues

class IssuesListWithCategoryDto(
    val category: String,
    val issuesList: MutableList<IssuesResponseDto> = ArrayList()
) {
}