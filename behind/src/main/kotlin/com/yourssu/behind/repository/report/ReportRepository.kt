package com.yourssu.behind.repository.report

import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.report.Report
import com.yourssu.behind.model.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReportRepository : JpaRepository<Report, Long> {
    fun existsByCommentAndUser(comment: Comment, user: User): Boolean
    fun existsByPostAndUser(post: Post, user: User): Boolean
}