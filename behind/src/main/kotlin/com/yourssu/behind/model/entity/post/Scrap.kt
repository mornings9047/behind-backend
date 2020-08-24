package com.yourssu.behind.model.entity.post

import com.yourssu.behind.model.entity.user.User
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Scrap(
        @Id @GeneratedValue
        val id: Long? = null,

        @ManyToOne
        val scrapUser: User,

        @ManyToOne
        val scrapPost: Post
)
