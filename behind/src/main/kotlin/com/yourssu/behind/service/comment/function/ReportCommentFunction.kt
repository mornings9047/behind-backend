package com.yourssu.behind.service.comment.function

import com.yourssu.behind.exception.comment.CommentNotExistsException
import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.repository.comment.CommentRepository

class ReportCommentFunction(private val commentRepository: CommentRepository) {

    fun reportComment(commentId: Long) {
        val comment: Comment = commentRepository.findById(commentId).orElseThrow { CommentNotExistsException() }

        if (comment.reportNum >= 2)
            comment.deleteComment = true
        else
            comment.reportNum++

        commentRepository.save(comment)
    }
}
