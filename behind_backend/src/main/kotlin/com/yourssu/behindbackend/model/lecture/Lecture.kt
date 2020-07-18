package com.yourssu.behindbackend.model.lecture

import com.yourssu.behindbackend.model.post.Post
import com.yourssu.behindbackend.model.user.User
import javax.persistence.*

@Entity
class Lecture(major: String, year: String, semester: Int, courseName: String, professor: String) {
    @Id
    @GeneratedValue
    val id: Long? = null

    @Column(name = "major")
    val major: String = major

    @Column(name = "year", length = 4)
    val year: String = year

    @Column(name = "semester")
    val semester = semester

    @Column(name = "courseName")
    val courseName: String = courseName

    @Column(name = "professor")
    val professor: String = professor

    @OneToMany(mappedBy = "lecture")
    var posts: MutableList<Post> = mutableListOf<Post>()

    @ManyToMany(mappedBy = "cours")
    var users: MutableList<User> = mutableListOf<User>()


}