package com.yourssu.behind.service.comment

import com.yourssu.behind.exception.post.PostNotExistException
import com.yourssu.behind.exception.user.UserNotExistsException
import com.yourssu.behind.model.dto.comment.request.CreateOrUpdateRequestCommentDto
import com.yourssu.behind.model.entity.comment.Comment
import com.yourssu.behind.repository.comment.CommentRepository
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService @Autowired constructor(val postRepository: PostRepository, val userRepository: UserRepository, val commentRepository: CommentRepository) {
    fun createComment(postId: Long, createOrUpdateRequestCommentDto: CreateOrUpdateRequestCommentDto) {
        var commentUser = userRepository.findBySchoolId(createOrUpdateRequestCommentDto.schoolId).orElseThrow { UserNotExistsException() }
        var targetPost = postRepository.findById(postId).orElseThrow { PostNotExistException() }

        commentRepository.save(Comment(
                user = commentUser,
                post = targetPost,
                content = createOrUpdateRequestCommentDto.content))
    }
}