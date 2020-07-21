package com.yourssu.behind.model.entity.comment

import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Comment(

        @Id @GeneratedValue
        val id: Long? = null,

        @Lob
        var content: String,

        val createdAt: LocalDateTime = LocalDateTime.now(),

        val isDeleted: Boolean = false,

        @ManyToOne
        val user: User,

        @ManyToOne
        val post: Post
) {}