package com.yourssu.behind.service.comment

import com.yourssu.behind.exception.comment.CommentNotExistsException
import com.yourssu.behind.exception.post.PostNotExistsException
import com.yourssu.behind.model.dto.comment.request.CreateOrUpdateRequestCommentDto
import com.yourssu.behind.model.dto.comment.response.ResponseCommentDto
import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.repository.comment.CommentRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.service.auth.JwtService
import com.yourssu.behind.service.comment.function.CommentFunction
import com.yourssu.behind.service.comment.function.ReportCommentFunction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService @Autowired constructor(private val postRepository: PostRepository,
                                            private val commentRepository: CommentRepository,
                                            val jwtService: JwtService) {
    private val commentFunction = CommentFunction(commentRepository, postRepository)
    private val reportFunction = ReportCommentFunction(commentRepository)

    @Transactional
    fun createComment(postId: Long, createOrUpdateRequestCommentDto: CreateOrUpdateRequestCommentDto) {
        val commentUser = jwtService.getUser()
        val targetPost = postRepository.findById(postId).orElseThrow { PostNotExistsException() }
        val newComment = Comment(user = commentUser, post = targetPost, content = createOrUpdateRequestCommentDto.content, parent = null)

        if (commentUser == targetPost.user)
            newComment.postOwner = true

        commentFunction.createComment(newComment)
    }

    @Transactional
    fun createRecomment(postId: Long, createOrUpdateRequestCommentDto: CreateOrUpdateRequestCommentDto, parentCommentId: Long) {
        val commentUser = jwtService.getUser()
        val targetPost = postRepository.findById(postId).orElseThrow { PostNotExistsException() }
        val comment = commentRepository.findById(parentCommentId).orElseThrow { CommentNotExistsException() }
        val reComment = Comment(content = createOrUpdateRequestCommentDto.content, user = commentUser, post = targetPost, parent = comment)

        if (commentUser == targetPost.user)
            reComment.postOwner = true
        comment.reCommentNum++
        commentRepository.save(reComment)
    }

    @Transactional
    fun getComment(postId: Long): List<ResponseCommentDto> {
        return commentFunction.getComment(postId)
    }

    @Transactional
    fun reportComment(commentId: Long) {
        return reportFunction.reportComment(commentId)
    }

    @Transactional
    fun deleteComment(commentId: Long) {
        return commentFunction.deleteComment(commentId)
    }
}
