package com.yourssu.behind.service

import com.yourssu.behind.exception.user.UserNotExistsException
import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostSearch
import com.yourssu.behind.repository.comment.CommentRepository
import com.yourssu.behind.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(val userRepository: UserRepository, val commentRepository: CommentRepository) {

    fun findUserRelatedPost(userId: Long, type: PostSearch): Collection<ResponsePostsDto> {
        val user = userRepository.findById(userId).orElseThrow { UserNotExistsException() }

        return when (type) {
            PostSearch.SCRAP -> {
                user.posts.map { ResponsePostsDto(it) }
            }
            PostSearch.COMMENT -> {
                val returnPost: MutableSet<Post> = mutableSetOf()
                commentRepository.findByUser(user).map { returnPost.add(it.post) }
                returnPost.map { ResponsePostsDto(it) }
            }
            PostSearch.POST -> {
                user.posts.map { ResponsePostsDto(it) }
            }
        }

    }
}