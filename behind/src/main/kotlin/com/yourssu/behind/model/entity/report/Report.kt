package com.yourssu.behind.model.entity.report

import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Report(
        @Id @GeneratedValue
        val id: Long? = null,
        @ManyToOne val user: User,
        @ManyToOne val comment: Comment?,
        @ManyToOne val post: Post?,
        val reportDate: LocalDateTime = LocalDateTime.now()
)
