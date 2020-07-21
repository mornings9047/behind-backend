package com.yourssu.behind.repository.post

import com.yourssu.behind.model.entity.post.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {

}