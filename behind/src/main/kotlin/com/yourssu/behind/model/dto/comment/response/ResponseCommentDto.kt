package com.yourssu.behind.model.dto.comment.response

import com.yourssu.behind.model.entity.comment.Comment

class ResponseCommentDto(comment: Comment) {
    val commentId = comment.id
    val userId = comment.user.id
    val postId = comment.post.id
    val content = comment.content
    val createdAt = comment.content
}
