package com.yourssu.behind.model.entity.post

import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.user.User
import org.springframework.data.jpa.repository.Temporal
import org.springframework.lang.Nullable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Post(
        @Id @GeneratedValue
        val id: Long? = null,

        @Enumerated(EnumType.STRING)
        var type: PostType,

        var title: String,

        @Nullable
        var imgUrl: String?,

        @Lob
        var content: String,

        @Temporal
        var createdAt: LocalDateTime = LocalDateTime.now(),

        var deletePost: Boolean = false,

        @OneToMany(mappedBy = "post")
        var comments: MutableList<Comment> = mutableListOf(),

        @ManyToOne
        var lecture: Lecture,

        @ManyToOne
        var user: User,

        @ManyToMany(mappedBy = "scrapPost")
        var scrapUser: MutableList<User> = mutableListOf(),

        @ManyToMany(mappedBy = "likePost")
        var likeUser: MutableList<User> = mutableListOf()
)
