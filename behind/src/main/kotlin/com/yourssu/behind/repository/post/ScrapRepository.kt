package com.yourssu.behind.repository.post

import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.Scrap
import com.yourssu.behind.model.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
interface ScrapRepository : JpaRepository<Scrap, Long> {
    fun findByScrapUserAndScrapPost(user: User, post: Post): Optional<Scrap>

    @Transactional
    fun deleteByScrapUserAndScrapPost(user: User, post: Post)
}