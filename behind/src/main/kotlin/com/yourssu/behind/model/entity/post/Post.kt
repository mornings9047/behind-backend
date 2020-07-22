package com.yourssu.behind.model.entity.post

import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.user.User
import org.springframework.data.jpa.repository.Temporal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Post(
        @Id @GeneratedValue
        val id: Long? = null,

        @Enumerated(EnumType.STRING)
        var type: PostType,

        var title: String,

        var imgUrl: String,

        @Lob
        var content: String,

        @Temporal
        var createdAt: LocalDateTime = LocalDateTime.now(),

        var isDeleted: Boolean = false,


        @OneToMany(mappedBy = "post")
        var comments: MutableList<Comment> = mutableListOf<Comment>(),

        @ManyToOne
        var lecture: Lecture,

        @ManyToOne
        var user: User,

        @ManyToMany(mappedBy = "scrapPost")
        var scrapUser: MutableList<User> = mutableListOf<User>(),

        @ManyToMany(mappedBy = "likePost")
        var likeUser: MutableList<User> = mutableListOf<User>()
) {}