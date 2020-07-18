package com.yourssu.behindbackend.model.comment

import com.yourssu.behindbackend.model.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Comment(user: User, content: String) {

    @Id
    @GeneratedValue
    val id: Long? = null

    @Column(name = "content",nullable = false)
    @Lob
    var content: String = content

    @Column(name = "createdAt",nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "delFlag",nullable = false)
    val delFlag: Boolean = false

    @ManyToOne
    val user: User = user
}