package com.yourssu.behind.service.comment.function

import com.yourssu.behind.exception.comment.CommentNotExistException
import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.repository.comment.CommentRepository

class CommentFunction(private val commentRepository: CommentRepository) {

    fun createComment(comment: Comment) {
        commentRepository.save(comment)
    }

        fun deleteComment(commentId: Long) {
            var comment: Comment = commentRepository.findById(commentId).orElseThrow { CommentNotExistException() }
        comment.deleteComment = true

        commentRepository.save(comment)
    }

}
