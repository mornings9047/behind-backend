package com.yourssu.behindbackend.model.post

import com.yourssu.behindbackend.model.lecture.Lecture
import com.yourssu.behindbackend.model.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Post(user: User, type: PostType, lecture: Lecture, title: String, content: String) {

    @Id
    @GeneratedValue
    val id: Long? = null

    @Column(name = "type", nullable = false)
    var type: PostType = type

    @Column(name = "title", nullable = false)
    @Enumerated(EnumType.STRING)
    var title: String = title

    @Column(name = "content", nullable = false)
    @Lob
    var content: String = content

    @Column(name = "createdAt")
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "delFlag")
    var delFlag: Boolean = false

    @ManyToOne
    var lecture: Lecture = lecture

    @ManyToOne
    var user: User = user

    @ManyToMany(mappedBy = "scrapPost")
    var scrapUser: MutableList<User> = mutableListOf<User>()

}