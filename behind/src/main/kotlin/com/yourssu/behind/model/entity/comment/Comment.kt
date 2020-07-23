package com.yourssu.behind.model.entity.comment

import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.user.User
import org.springframework.data.jpa.repository.Temporal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Comment(

        @Id @GeneratedValue
        val id: Long? = null,

        @Lob
        var content: String,

        @Temporal
        val createdAt: LocalDateTime = LocalDateTime.now(),

        val deleteComment: Boolean = false,

        @ManyToOne
        val user: User,

        @ManyToOne
        val post: Post
) {}