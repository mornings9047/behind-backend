package com.yourssu.behind.model.entity.post

import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Post(
        @Id @GeneratedValue
        val id: Long? = null,

        @Enumerated(EnumType.STRING)
        var type: PostType,

        var title: String,

        @Lob
        var content: String,

        var createdAt: LocalDateTime = LocalDateTime.now(),

        var isDeleted: Boolean = false,

        @ManyToOne
        var lecture: Lecture,

        @ManyToOne
        var user: User,

        @ManyToMany(mappedBy = "scrapPost")
        var scrapUser: MutableList<User> = mutableListOf<User>(),

        @ManyToMany(mappedBy = "likePost")
        var likeUser: MutableList<User> = mutableListOf<User>()
) {}