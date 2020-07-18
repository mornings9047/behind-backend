package com.yourssu.behindbackend.model.user

import com.yourssu.behindbackend.model.comment.Comment
import com.yourssu.behindbackend.model.lecture.Lecture
import com.yourssu.behindbackend.model.post.Post
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class User(userName: String, schoolId: String, password: String) {

    @Id
    @GeneratedValue
    val id: Long? = null

    @Column(name = "userName", unique = true, nullable = false)
    val userName: String = userName

    @Column(name = "schoolId", unique = true, length = 8, nullable = false)
    val schoolId: String = schoolId

    @Column(name = "password", nullable = false)
    var password: String = password

    @Column(name = "regDate")
    val regDate: LocalDateTime = LocalDateTime.now()

    @OneToMany(mappedBy = "user")
    var posts: MutableList<Post> = mutableListOf<Post>()

    @OneToMany(mappedBy = "user")
    var comments: MutableList<Comment> = mutableListOf<Comment>()

    @ManyToMany
    @JoinTable(name = "courseList")
    var cours: MutableList<Lecture> = mutableListOf<Lecture>()

    @ManyToMany
    @JoinTable(name = "scrap")
    var scrapPost: MutableList<Post> = mutableListOf<Post>()
}