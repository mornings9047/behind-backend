package com.yourssu.behind.model.entity.user

import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.post.Post
import org.springframework.data.jpa.repository.Temporal
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
data class User(
        @Id @GeneratedValue val id: Long? = null,

        val schoolId: String,

        @NotBlank(message = "대소문자, 숫자로 구성")
        @Size(min = 8, max = 20)
        val password: String,

        @Temporal
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