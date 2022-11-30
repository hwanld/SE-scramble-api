package com.se.scramble.domain.dto.issues

class IssuesDragAndDropDto(
    val issues_id: Long,
    val past_index: Int,
    val past_droppableId: String,
    val cur_index: Int,
    val cur_droppableId: String
) {
}