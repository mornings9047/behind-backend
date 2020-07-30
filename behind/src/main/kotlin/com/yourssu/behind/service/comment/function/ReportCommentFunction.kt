package com.yourssu.behind.service.comment.function

import com.yourssu.behind.exception.comment.CommentNotExistException
import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.repository.comment.CommentRepository

class ReportCommentFunction(val commentRepository: CommentRepository) {

    fun reportComment(commentId: Long) {
        var comment: Comment = commentRepository.findById(commentId).orElseThrow { CommentNotExistException() }

        if (comment.reportNum >= 2)
            comment.deleteComment = true
        else
            comment.reportNum++

        commentRepository.save(comment)
    }
}