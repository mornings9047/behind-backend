package com.yourssu.behind.service.comment.function

import com.yourssu.behind.exception.comment.CommentNotExistsException
import com.yourssu.behind.exception.report.AlreadyReportCommentException
import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.report.Report
import com.yourssu.behind.model.entity.user.User
import com.yourssu.behind.repository.comment.CommentRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.report.ReportRepository
import com.yourssu.behind.repository.user.UserRepository

class ReportCommentFunction(private val postRepository: PostRepository,private val commentRepository: CommentRepository, private val reportRepository: ReportRepository) {

    fun reportComment(user: User, commentId: Long) {
        var comment: Comment = commentRepository.findById(commentId).orElseThrow { CommentNotExistsException() }
        var post : Post = postRepository.findByComments(comment).orElseThrow { CommentNotExistsException() }

        if (reportRepository.existsByCommentAndUser(comment, user))
            throw AlreadyReportCommentException()
        if (comment.reportNum >= 2)
        {
            comment.deleteComment = true
            post.commentNum--
        }

        reportRepository.save(Report(user = user, comment = comment, post = null))
        comment.reportNum++
        commentRepository.save(comment)
    }
}