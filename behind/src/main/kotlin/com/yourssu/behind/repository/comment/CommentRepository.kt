package com.yourssu.behind.repository.comment

import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByUserAndPost(user: User, post: Post): Optional<List<Comment>>
    fun findAllByPostIdAndDeleteCommentIsFalse(postId: Long): List<Comment>
}
