package com.yourssu.behind.service.comment.function

import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.repository.comment.CommentRepository

class CommentFunction(private val commentRepository: CommentRepository) {

    fun createComment(comment: Comment) {
        commentRepository.save(comment)
    }

}
