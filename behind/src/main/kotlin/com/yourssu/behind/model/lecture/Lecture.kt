package com.yourssu.behind.model.lecture

import com.yourssu.behind.model.post.Post
import com.yourssu.behind.model.professor.Professor
import com.yourssu.behind.model.user.User
import javax.persistence.*

@Entity
data class Lecture(
        @Id
        @GeneratedValue
        val id: Long? = null,

        val major: String,

        val year: String,

        @Enumerated(EnumType.STRING)
        val semester: LectureSemester,

        val courseName: String,

        @ManyToOne
        val professor: Professor,

        @OneToMany(mappedBy = "lecture")
        var posts: MutableList<Post> = mutableListOf<Post>(),

        @ManyToMany(mappedBy = "lectures")
        var users: MutableList<User> = mutableListOf<User>()
) {}