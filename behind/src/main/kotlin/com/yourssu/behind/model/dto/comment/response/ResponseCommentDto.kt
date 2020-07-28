package com.yourssu.behind.model.dto.comment.response

import com.yourssu.behind.model.entity.comment.Comment
import java.time.LocalDateTime

class ResponseCommentDto(comment: Comment) {
    var postOwner: Boolean = comment.postOwner
    val content: String = comment.content
    val createdAt: LocalDateTime = comment.createdAt
    val reComment: List<ResponseCommentDto>? = comment.children.map { ResponseCommentDto(it) }
}