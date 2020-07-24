package com.yourssu.behind.service.post.function

import com.yourssu.behind.exception.post.PostNotExistException
import com.yourssu.behind.exception.post.WriterThumbsUpException
import com.yourssu.behind.exception.post.WritterScrapException
import com.yourssu.behind.exception.user.UserNotExistsException
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.user.User
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.user.UserRepository

class ScrapFunction(private val userRepository: UserRepository, private val postRepository: PostRepository) {
    fun createScrapPost(schoolId: String, postId: Long) {

        var scrapPost: Post = postRepository.findById(postId).orElseThrow { PostNotExistException() }
        var scrapUser: User = userRepository.findBySchoolId(schoolId).orElseThrow { UserNotExistsException() }

        if (scrapPost.user == scrapUser)
            throw WritterScrapException()
        else if (scrapUser.scrapPost.contains(scrapPost))
            deleteScrapPost(scrapUser, scrapPost)
        else {
            scrapUser.scrapPost.add(scrapPost)
        }
        userRepository.save(scrapUser)
    }

    private fun deleteScrapPost(user: User, post: Post): Boolean {
        return user.scrapPost.remove(post)
    }
}