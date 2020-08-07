package com.yourssu.behind.repository.comment

import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.user.User
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByUserAndPost(user: User, post: Post, page: Pageable): List<Comment>
    fun findByPostAndParentIsNull(post: Post): List<Comment>
    fun findAllByUserAndDeleteCommentIsFalse(user: User): List<Comment>
    fun findAllByDeleteCommentIsFalseAndParent(parent: Comment): List<Comment> //대댓글 가져오는 쿼리
    fun countByPostAndDeleteCommentIsFalse(post: Post): Int
}
