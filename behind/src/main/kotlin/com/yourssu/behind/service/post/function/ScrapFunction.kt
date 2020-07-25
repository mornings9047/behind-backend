package com.yourssu.behind.service.post.function

import com.yourssu.behind.exception.post.PostNotExistException
import com.yourssu.behind.exception.post.WritterScrapException
import com.yourssu.behind.exception.user.UserNotExistsException
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.user.User
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.user.UserRepository

class ScrapFunction(private val userRepository: UserRepository, private val postRepository: PostRepository) {
    fun createScrapPost(schoolId: String, postId: Long) {

        val post: Post = postRepository.findById(postId).orElseThrow { PostNotExistException() }
        val user: User = userRepository.findBySchoolId(schoolId).orElseThrow { UserNotExistsException() }

        when {
            post.user == user -> throw WritterScrapException()
            user.scrapPost.contains(post) -> deleteScrapPost(user, post)
            else -> user.scrapPost.add(post)
        }
        userRepository.save(user)
    }

    private fun deleteScrapPost(user: User, post: Post): Boolean {
        return user.scrapPost.remove(post)
    }
}