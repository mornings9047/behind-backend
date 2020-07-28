package com.yourssu.behind.service.comment

import com.yourssu.behind.exception.comment.CommentNotExistException
import com.yourssu.behind.exception.post.PostNotExistException
import com.yourssu.behind.exception.user.UserNotExistsException
import com.yourssu.behind.model.dto.comment.request.CreateOrUpdateRequestCommentDto
import com.yourssu.behind.model.dto.comment.response.ResponseCommentDto
import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.model.entity.comment.CommentPage
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.repository.comment.CommentRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.user.UserRepository
import com.yourssu.behind.service.comment.function.CommentFunction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService @Autowired constructor(val postRepository: PostRepository, val userRepository: UserRepository, val commentRepository: CommentRepository) {

    private val commentFunction = CommentFunction(commentRepository)

    fun createComment(postId: Long, createOrUpdateRequestCommentDto: CreateOrUpdateRequestCommentDto) {
        val commentUser = userRepository.findBySchoolId(createOrUpdateRequestCommentDto.schoolId).orElseThrow { UserNotExistsException() }
        val targetPost = postRepository.findById(postId).orElseThrow { PostNotExistException() }
        val newComment = Comment(user = commentUser, post = targetPost, content = createOrUpdateRequestCommentDto.content, parent = null)

        if (commentUser == targetPost.user)
            newComment.postOwner = true

        commentFunction.createComment(newComment)
    }

    @Transactional
    fun createRecomment(postId: Long, createOrUpdateRequestCommentDto: CreateOrUpdateRequestCommentDto, parentCommentId: Long) {

        val commentUser = userRepository.findBySchoolId(createOrUpdateRequestCommentDto.schoolId).orElseThrow { UserNotExistsException() }
        val targetPost = postRepository.findById(postId).orElseThrow { PostNotExistException() }
        var comment = commentRepository.findById(parentCommentId).orElseThrow { CommentNotExistException() }
        val reComment = Comment(content = createOrUpdateRequestCommentDto.content, user = commentUser, post = targetPost, parent = comment)

        commentRepository.save(reComment)
    }

    @Transactional
    fun getComment(postId: Long, page: Int): List<ResponseCommentDto> {
        val post: Post = postRepository.findById(postId).orElseThrow { PostNotExistException() }
        return commentRepository.findByPostAndParentIsNull(post, CommentPage(page)).map { ResponseCommentDto(it) }
    }
}