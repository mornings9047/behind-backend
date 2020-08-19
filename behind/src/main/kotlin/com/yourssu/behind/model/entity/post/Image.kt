package com.yourssu.behind.model.entity.post

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Image(
        @Id @GeneratedValue val id: Long? = null,
        val URL: String,
        @ManyToOne
        val post: Post
) {
}