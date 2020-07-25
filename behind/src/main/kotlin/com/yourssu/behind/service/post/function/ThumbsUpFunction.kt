package com.yourssu.behind.service.post.function

import com.yourssu.behind.exception.post.AlreadyThumbsUpException
import com.yourssu.behind.exception.post.PostNotExistException
import com.yourssu.behind.exception.post.WriterThumbsUpException
import com.yourssu.behind.exception.user.UserNotExistsException
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.user.User
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.user.UserRepository

class ThumbsUpFunction(private val userRepository: UserRepository, private val postRepository: PostRepository) {

    fun thumbsUp(schoolId: String, postId: Long) {
        val post: Post = postRepository.findById(postId).orElseThrow { PostNotExistException() }
        val user: User = userRepository.findBySchoolId(schoolId).orElseThrow { UserNotExistsException() }

        when {
            post.user == user -> throw  WriterThumbsUpException()
            user.likePost.contains(post) -> throw AlreadyThumbsUpException()
            else -> {
                user.likePost.add(post)
                userRepository.save(user)
            }
        }
    }
}