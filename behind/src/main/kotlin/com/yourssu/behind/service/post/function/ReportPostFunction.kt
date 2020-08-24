package com.yourssu.behind.service.post.function

import com.yourssu.behind.exception.post.PostNotExistsException
import com.yourssu.behind.exception.report.PostAlreadyReportedException
import com.yourssu.behind.model.entity.report.Report
import com.yourssu.behind.model.entity.user.User
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.post.ScrapRepository
import com.yourssu.behind.repository.report.ReportRepository

class ReportPostFunction(private val postRepository: PostRepository,
                         private val scrapRepository: ScrapRepository,
                         private val reportRepository: ReportRepository) {
    fun reportPost(user: User, postId: Long) {
        val post = postRepository.findById(postId).orElseThrow { PostNotExistsException() }
        if (reportRepository.existsByPostAndUser(post, user))
            throw PostAlreadyReportedException()

        if (post.reportNum >= 2) {
            post.deletePost = true
            scrapRepository.deleteAllByScrapPost(post)
            post.comments.map { it.deleteComment = true }
        }

        reportRepository.save(Report(user = user, post = post, comment = null))
        post.reportNum++
        postRepository.save(post)
    }
}
