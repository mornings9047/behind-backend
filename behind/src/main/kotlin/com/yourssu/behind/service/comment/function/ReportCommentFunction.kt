package com.yourssu.behind.service.comment.function

import com.yourssu.behind.exception.comment.CommentNotExistsException
import com.yourssu.behind.exception.post.PostNotContainsCommentException
import com.yourssu.behind.exception.post.PostNotExistsException
import com.yourssu.behind.exception.report.CommentAlreadyReportedException
import com.yourssu.behind.model.entity.report.Report
import com.yourssu.behind.repository.comment.CommentRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.report.ReportRepository
import com.yourssu.behind.service.auth.JwtService

class ReportCommentFunction(private val postRepository: PostRepository,
                            private val commentRepository: CommentRepository,
                            private val reportRepository: ReportRepository,
                            private val jwtService: JwtService) {

    fun reportComment(postId: Long, commentId: Long) {
        val user = jwtService.getUser()
        val post = postRepository.findById(postId).orElseThrow { PostNotExistsException() }
        val comment = commentRepository.findById(commentId).orElseThrow { CommentNotExistsException() }
        if (!post.comments.contains(comment))
            throw PostNotContainsCommentException()
        if (reportRepository.existsByCommentAndUser(comment, user))
            throw CommentAlreadyReportedException()
        if (comment.reportNum >= 2) {
            comment.deleteComment = true
            post.commentNum--
        }

        reportRepository.save(Report(user = user, comment = comment, post = null))
        comment.reportNum++
        commentRepository.save(comment)
    }
}
