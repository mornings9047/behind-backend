package com.yourssu.behind.model.entity.user

import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.post.Post
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class User(
        @Id @GeneratedValue val id: Long?,

        val schoolId: String,

        val password: String,

        val regDate: LocalDateTime = LocalDateTime.now(),

        @ManyToMany
        @JoinTable(name = "courses")
        var lectures: MutableList<Lecture> = mutableListOf<Lecture>(),

        @OneToMany(mappedBy = "user")
        var posts: MutableList<Post> = mutableListOf<Post>(),

        @OneToMany(mappedBy = "user")
        var comments: MutableList<Comment> = mutableListOf<Comment>(),

        @ManyToMany
        @JoinTable(name = "scrap")
        var scrapPost: MutableList<Post> = mutableListOf<Post>(),

        @ManyToMany
        @JoinTable(name = "thumbsUp")
        var likePost: MutableList<Post> = mutableListOf<Post>()

) {}